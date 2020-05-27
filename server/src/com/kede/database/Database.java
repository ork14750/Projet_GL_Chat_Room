package com.kede.database;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {
	
	private String dbPath;
	
	public Database(String dbPath){
		this.dbPath = dbPath;
	}
	
	public boolean isUser(String login) {
		try {
			File file= new File(dbPath);
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
			e.printStackTrace();
			return false;
		}
       
	}
	
	
	 public static String getValue(String tag, Element el) {
			NodeList nList = el.getElementsByTagName(tag).item(0).getChildNodes();
		    Node nValue = (Node) nList.item(0);
			return nValue.getNodeValue();
		  }
	
	
	

}
