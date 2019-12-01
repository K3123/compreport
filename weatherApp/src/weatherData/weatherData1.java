package weatherData;


import static weatherData.weatherClass.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class weatherData1 extends DefaultHandler implements Runnable  {
	
	private static List<weatherClass> weather2 = new ArrayList<weatherClass>();
    private static weatherClass wthData = null;
    private static weatherClass wthData2 = null;
    private static String text = null;
    private JFrame mainFrame;
    private JPanel hiPanel;
    private JPanel lowPanel;
    private JPanel imageTempPanel;
    private JPanel tempPanel;
    private JLabel hiLabel;
    private JLabel lowLabel;
    private TextField hiText;
    private TextField lowText;
    private TextField iconText;
    private TextField description;
    private JPanel controlPanel;
    private JLabel picPanel;
    private BufferedImage myPicture;
	
    @Override
    // A start tag is encountered.
    public void startElement(String uri, String localName, String qName, Attributes attributes)
         throws SAXException {

         switch (qName) {
              // Create a new Employee.
              case "CITY": {
            	  wthData = new weatherClass();
            	  wthData.setCityName(attributes.getValue("NAME"));
                  break;
              }
              case "ICON" :{
            	  wthData.setCityIcon(attributes.getValue("VALUE"));
                  break;
              }
         }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
         switch (qName) {
              case "CITY": { 
            	   weather2.add(wthData);
                   break;
              }
              case "LOW": {
            	  wthData.setCityLow(text);
                   break;
              }
              case "HI": {
            	  wthData.setCityHi(text);
                   break;
              }
              case "DESCRIPTION": {
            	  wthData.setCityDescription(text);
                   break;
              }
 
         }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
         text = String.copyValueOf(ch, start, length).trim();
    }

    private void prepareGUI(){
        mainFrame = new JFrame();
        mainFrame.setSize(400,350);
        mainFrame.setLayout(new GridLayout(4, 1));       
        hiLabel = new JLabel("HI:  ",JLabel.LEFT );
        hiLabel.setSize(5, 5);
        lowLabel = new JLabel("Low:",JLabel.LEFT );
        lowLabel.setSize(5,5);
        hiPanel = new JPanel();
        hiPanel.setLayout(new FlowLayout());
        hiText = new TextField();
        lowText = new TextField();
        iconText = new TextField();
        hiText.setEditable(false);
        hiText.setSize(1, 1);
        lowText.setEditable(false);
        hiPanel.add(hiLabel);
        hiPanel.add(hiText);
        lowPanel = new JPanel();
        lowPanel.setLayout(new FlowLayout());
        lowPanel.add(lowLabel);
        lowPanel.add(lowText);
        
        tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(2,1));
        
        tempPanel.add(hiPanel,JPanel.LEFT_ALIGNMENT);
        tempPanel.add(lowPanel,JPanel.LEFT_ALIGNMENT);
        
        description = new TextField();
        description.setEditable(false);
        description.setSize(100,100);
        
        JComboBox<String> weatherBox = new JComboBox<String>();
        for (weatherClass wthData1 : weather2){
        	weatherBox.addItem(wthData1.getCityName());
        }
        mainFrame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });    

        controlPanel = new JPanel();
        controlPanel.setSize(2, 5);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        controlPanel.add(weatherBox);

      
        imageTempPanel = new JPanel();
        imageTempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        imageTempPanel.add(tempPanel);
  		
        try {
    		myPicture = ImageIO.read(new File("./bin/rain.gif"));
    		picPanel = new JLabel(new ImageIcon(myPicture));
    		picPanel.setVisible(false);
    		imageTempPanel.add(picPanel);
        } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
    	}
     	  
        
        weatherBox.addActionListener(new ActionListener () {
        	@Override
        	public void actionPerformed(ActionEvent event ) {
        		String selectedCity = (String) weatherBox.getSelectedItem();
        		for (weatherClass wthData1 : weather2){
        			if (wthData1.getCityName().equalsIgnoreCase(selectedCity)) {
        				wthData2 = wthData1;        			
        			    run();
        			}
            		
        		}
        	}
        	
        	
        });
        
        
        
        mainFrame.add(controlPanel);
        mainFrame.add(imageTempPanel);
        mainFrame.add(description);
        mainFrame.setVisible(true);  
     }

    public weatherData1(){
     }
    
    public static void main(String[] args) throws ParserConfigurationException,
    SAXException, IOException {
		
		SAXParserFactory parserFactor = SAXParserFactory.newInstance();
		SAXParser parser = parserFactor.newSAXParser();
		weatherData1 handler = new weatherData1();
		parser.parse(new File("./bin/Weather.xml"), handler);
	    
		handler.prepareGUI();
		
 
	} 
    
 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		hiText.setText(wthData2.getCityHi());
		lowText.setText(wthData2.getCityLow());
		iconText.setText("./bin/" + wthData2.getCityIcon().toLowerCase() + ".gif");
		description.setText(wthData2.getCityDescription());

        try {
        		myPicture = ImageIO.read(new File(iconText.getText().toString().toLowerCase()));
        		picPanel.setIcon(new ImageIcon(myPicture));
        		picPanel.setVisible(true);
 		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}

