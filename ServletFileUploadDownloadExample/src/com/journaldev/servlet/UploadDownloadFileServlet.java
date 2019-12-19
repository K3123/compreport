package com.journaldev.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



/**
 * Servlet implementation class UploadDownloadFileServlet
 */
@WebServlet("/UploadDownloadFileServlet")
public class UploadDownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;      
 
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName = request.getParameter("fileName");
		if ( fileName == null || fileName.equals("")) {
			throw new ServletException("File Name can't be null or empty");
		}
		File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName);
		if ( !file.exists() ) {
			throw new ServletException("File doesn't exists on server.");
		}
		System.out.println("File location on server::" + file.getAbsolutePath());
		ServletContext ctx = getServletContext();
		InputStream fis = new FileInputStream(file);
		String mimeType = ctx.getMimeType(file.getAbsolutePath());
		response.setContentType(mimeType != null? mimeType:"application/octet-stream"); 
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		ServletOutputStream os = response.getOutputStream();
		byte[] bufferData = new byte[1024];
		int read1 = 0; 
		while (( read1 = fis.read(bufferData)) != -1 ) {
			os.write(bufferData, 0 , read1);
		}
		os.flush();
		os.close();
		fis.close();
		System.out.println("File downloaded at client successfully.");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if ( !ServletFileUpload.isMultipartContent(request)) {
			throw new ServletException("Content type is not multipart/form-data");
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
				List<FileItem> fileItemsList = uploader.parseRequest(request);
				Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
				while ( fileItemsIterator.hasNext() ) {
					FileItem fileItem = fileItemsIterator.next();
					System.out.println("FieldName=" + fileItem.getFieldName());
					System.out.println("FileNamePath=" + fileItem.getName());
					System.out.println("FileNamePath=" + fileItem.getName());
					System.out.println("ContentType=" + fileItem.getContentType());
					System.out.println("Size in bytes=" + fileItem.getSize());
					String[] fildetails = fileItem.getName().split(":");
					
					File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fildetails[1].replace("\\", ""));
					System.out.println("Absolute Path at server " + file.getAbsolutePath());
					if ( ! file.exists()) {
						fileItem.write(file);
						out.write("File " + fileItem.getName() + " uploaded successfully. ");
					}else out.write("File " + fileItem.getName() + " already uploaded successfully. ");
					
					out.write("<br>");
					out.write("<a href=\"UploadDownloadFileServlet?fileName=" + fileItem.getName().replace("C:\\", "").replace("\\","/") + "\">Download " + fileItem.getName() + "</a>");
				}
		} catch ( FileUploadException e ) {
			out.write("Exception in uploading file. ");
		} catch ( Exception e ) {
			out.write("Exception in uploading file. ");
		}
		out.write("</body></html>");
	}

}
