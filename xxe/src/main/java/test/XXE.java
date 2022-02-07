package main.java.test;

import java.io.*;  
import javax.servlet.ServletException;  
import javax.servlet.http.*; 	
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.xml.XMLConstants;

public class XXE extends HttpServlet {
	
	private static final long serialVersionUID = 102831973239L;

    private List<User> processFiles(String filePath){
        File xmlFile = new File(filePath);
        List<User> userList = new ArrayList<User>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {            
            dBuilder = dbFactory.newDocumentBuilder();                      
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();            
            NodeList nodeList = doc.getElementsByTagName("user");                       
            for (int i = 0; i < nodeList.getLength(); i++) {
                userList.add(getUser(nodeList.item(i)));
            }                        
        } catch (Exception e1) {
            e1.printStackTrace();            
        }   
        return userList;     
    }

    private static User getUser(Node node) {        
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            user.setFname(getTagValue("fname", element));
            user.setLname(getTagValue("lname", element));
            user.setEmail(getTagValue("email", element));            
        }
        return user;
    }


    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
        
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}	
    public void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {		       	
        String filePath = "";       
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        File uploadedFile = new File(name);
                        filePath = uploadedFile.getAbsolutePath();
                        item.write(uploadedFile);
                    }
                }
                if (filePath != ""){
                    List<User> users = processFiles(filePath);        
                    request.setAttribute("users", users);
                }
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception e1) {
                e1.printStackTrace();            
            }   
        }
    }
}
