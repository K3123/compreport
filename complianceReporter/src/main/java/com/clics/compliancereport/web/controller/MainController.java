package com.clics.compliancereport.web.controller;

import com.clics.compliancereport.bean.DataBean;
import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.bean.LabelValueBean;
import com.clics.compliancereport.common.Constants;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;
import com.clics.compliancereport.domain.UserCredential;
import com.clics.compliancereport.exception.ServiceException;
import com.clics.compliancereport.service.CompReportService;
import com.clics.compliancereport.util.MiscUtil;
import com.clics.compliancereport.validator.FormBeanValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("formBean")
public class MainController {
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    public static final String FORM_VIEW = "requestform";
    public static final String MODEL_KEY = "formBean";
    @Autowired
    private CompReportService compReportService;
    @Autowired
    private FormBeanValidator formBeanValidator;


    @RequestMapping({"/", "/requestform"})
    public String createForm(Model model) {
        FormBean formBean = new FormBean();
        formBean.setReportTypes(getReportTypeList());
        model.addAttribute(MODEL_KEY, formBean);
        return FORM_VIEW;
    }


    @RequestMapping(value = "/saverequest", method = RequestMethod.POST)
    public String saveRequestForm(@ModelAttribute FormBean formBean, BindingResult result,
                                HttpServletRequest request, RedirectAttributes redirectAttrs) {
        LOG.debug("<<<<< Entering 'saveRequestForm()' method...");

        String email = null;
        String fullName = null;

        //get the credentials before validation kicks in
        UserCredential userCredential = (UserCredential)request.getAttribute(Constants.CREDENTIAL_KEY);
        if(userCredential != null && formBean != null){
              email = userCredential.getEmail();
              fullName = userCredential.getName();
              formBean.setSsoId(userCredential.getSsoid());
              formBean.setEmail(email);
        }

        //now we are ready to validate
        formBeanValidator.validate(formBean, result);

        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute(Constants.ERROR_KEY, Constants.SUBMISSION_ERROR);
            redirectAttrs.addFlashAttribute(MODEL_KEY, formBean);
            return FORM_VIEW;
        }

        //populate request to be sent
        CompRequest compRequest = MiscUtil.populateCompRequest(formBean);

        try {
            //process the request
            Boolean bSuccess = compReportService.processRequest(compRequest);
            if(bSuccess) {
               LOG.info("<<<<< Request succeeded.......");
            }

            redirectAttrs.addFlashAttribute(Constants.SUCCESS_KEY, formatSuccessMessage(email, fullName));
        } catch (ServiceException ex) {
            LOG.error(ex.getMessage());
            redirectAttrs.addFlashAttribute(Constants.ERROR_KEY, Constants.SUBMISSION_ERROR);
        }
        return "redirect:" + FORM_VIEW;
    }


    /**
     * This method will return same set of databases all the time.
     *
     * @return Map<String, List<DBInfo>> represents a JSON name value pair
     */
    @RequestMapping(value = "/databaseSearch", method = RequestMethod.GET)
    @ResponseBody
    public DataBean getDatabases(@RequestParam(required = true) String term) {
        LOG.debug("<<<<< Entering method 'getDbInfos(\"{}\")...", term);
        List<DBInfo> dbInfos = compReportService.findByNameLike(term);
        return new DataBean(dbInfos);
    }


    private String formatSuccessMessage(final String email, final String fullName) {
        StringBuilder buffer = new StringBuilder();
        buffer.append((StringUtils.isNotEmpty(fullName))? fullName: "").append(" your request was submitted successfully. ")
                .append("An email with the link to your report will be  sent to your ")
                .append("email account ").append((StringUtils.isNotEmpty(email))? email : "").append(" as soon as the report is generated.");
        return  buffer.toString();
    }


    private List<LabelValueBean> getReportTypeList() {
        LabelValueBean bean = new LabelValueBean("Database-User-Scan-Report", "Database User Scan Reports");
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(bean);
        bean = new LabelValueBean("SPII-Database-Compliance-Review", "SPII Database Compliance Review");
        list.add(bean);
        return list;
    }


    public void setCompReportService(CompReportService compReportService) {
        this.compReportService = compReportService;
    }

    public void setFormBeanValidator(FormBeanValidator formBeanValidator) {
        this.formBeanValidator = formBeanValidator;
    }

}
