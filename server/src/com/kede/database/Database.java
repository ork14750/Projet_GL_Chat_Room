package com.kede.database;

//singleton design pattern
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {
	
	private String dbPath;
	private File file;
	
	private static Database INSTANCE = null;
	
	public Database(String name){
		
		Path currentRelativePath = Paths.get("");
	
		String s = currentRelativePath.toAbsolutePath().getParent().toString();
		this.dbPath = s+File.separator+name+".xml";
		 file = new File(this.dbPath);
		try {
			if (file.createNewFile())
			{
			    System.out.println("File is created!");
			} else {
			    System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(this.dbPath);
	}
	
	public boolean isUser(String login) {
		try {
			
			DocumentBuilderFactory dbFct = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder;
			builder = dbFct.newDocumentBuilder();
			Document doc = builder.parse(file);
		    doc.getDocumentElement().normalize();
		         
		    NodeList node = doc.getElementsByTagName("user");
		    
		    
		    for(int i =0; i< node.getLength(); i++) {
		    	Node item = node.item(i);
		    	if(item.getNodeType() == Node.ELEMENT_NODE) {
		    		Element el = (Element) item;
		    		if(getValue("login", el).equals(login)){
	                        return true;
	                   }
		    	}
		    }
		    return false;
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
       
	}
	
	   public void addUser(String login, String password){
	        
	        try {
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse(this.dbPath);
	 
	            Node data = doc.getFirstChild();
	            
	            Element user = doc.createElement("user");
	            Element newlogin = doc.createElement("login"); newlogin.setTextContent(login);
	            Element newpass = doc.createElement("password"); newpass.setTextContent(password);
	            
	            user.appendChild(newlogin); user.appendChild(newpass); data.appendChild(user);
	            
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(new File(this.dbPath));
	            transformer.transform(source, result);
	 
		   } 
	           catch(Exception ex){
			System.out.println(ex);
		   }
		}
	
	
	
	 public  String getValue(String tag, Element el) {
			NodeList nList = el.getElementsByTagName(tag).item(0).getChildNodes();
		    Node nValue = (Node) nList.item(0);
			return nValue.getNodeValue();
		  }
	 
	 
   public boolean chkLogin(String login, String password){
        
        if(!isUser(login)){ return false; }
        
        try{
            
            DocumentBuilderFactory dbFct = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFct.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();
            
            NodeList nodeList = doc.getElementsByTagName("user");
            
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    if(getValue("login", el).equals(login) && getValue("password", el).equals(password)){
                        return true;
                    }
                }
            }
            return false;
        }
        catch(Exception ex){
            return false;
        }
    }
    
	 
	 
	 
	
	 public static Database getInstance()
	    {           
	        if (INSTANCE == null)
	        {   INSTANCE = new Database("Database"); 
	        }
	        return INSTANCE;
	    }
	
	

}
