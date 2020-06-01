package com.kede.database;

//singleton design pattern
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Gestion de la base de donnée XML
 * Singleton
 * @author Christophe Kede
 *
 */
public class Database {
	
	private String dbPath;
	private File file;
	
	private static Database INSTANCE = null;
	/**
	 * Initialiser la Base de donnée, qui va être créée la racine du répetoire contenant l'application
	 * @param name nom de la base de donnée
	 */
	public Database(String filePath){
		
		this.dbPath = filePath;
		System.out.println(this.dbPath);
	}
	
	/**
	 * Méthode qui vérifie si un utilisateur existe
	 * @param login
	 * @return Boolean 
	 */
	public boolean isUser(String login) {
		try {
			
			File fXmlFile = new File(dbPath);
			DocumentBuilderFactory dbFct = DocumentBuilderFactory.newInstance();
	        dbFct.setIgnoringElementContentWhitespace(true);

		    DocumentBuilder builder;
			builder = dbFct.newDocumentBuilder();
			Document doc = builder.parse(fXmlFile);
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
		/**
		 * Methode ajout d'un nouvelle utilisateur
		 * @param login
		 * @param password
		 */
	   public void addUser(String login, String password){
	        
	        try {
	            DocumentBuilderFactory dFct = DocumentBuilderFactory.newInstance();
		        dFct.setIgnoringElementContentWhitespace(true);
	            DocumentBuilder docBuilder = dFct.newDocumentBuilder();
	            
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
	   
	   
	   
	   /**
	    * Methode d'ajout d'un nouveau Groupe d'utilisateur
	    * @param name String
	    * @param createddBy String
	    * @param groupUser String[]
	    */
	   public void addGroup(String name, String createddBy, String[] groupUser){
	        
	        try {
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse(this.dbPath);
	 
	            Node data = doc.getFirstChild();
	            
	            Element group = doc.createElement("group");
	            
	            Element createdBy = doc.createElement("createdBy");
	            createdBy.setTextContent(createddBy);
	            
	            Element groupName = doc.createElement("name"); 
	            groupName.setTextContent(name);
	            
	            group.appendChild(createdBy);
	            group.appendChild(groupName); 
	           
	            for(int i= 0; i<groupUser.length; i++) {
	            	Element user = doc.createElement("userLogin");
	            	user.setTextContent(""+groupUser[i]);
		            group.appendChild(user);

	            }
	            System.out.println(group);
	            
	            data.appendChild(group);
	            
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
	   
	   
	   
	   
	   
	
	
	/**
	 * Methode qui retourne la valeur d'une balise du Fichier XML
	 * @param nom de la balise
	 * @param el Element
	 * @return String Valeur de la basile
	 */
	 public  String getValue(String tag, Element el) {
			NodeList nList = el.getElementsByTagName(tag).item(0).getChildNodes();
		    Node nValue = (Node) nList.item(0);
			return formatXml(nValue.getNodeValue());
		  }
	 
	 
	 /**
	  * Methode qui Verifie si les identifiants entrés sont correctes 
	  * @param login
	  * @param password
	  * @return Bolean
	  */
   public boolean chckLogin(String login, String password){
        
        if(!isUser(login)){ return false; }
        
        try{
        	File fXmlFile = new File(dbPath);
            DocumentBuilderFactory dbFct = DocumentBuilderFactory.newInstance();
	        dbFct.setIgnoringElementContentWhitespace(true);

            DocumentBuilder docBuilder = dbFct.newDocumentBuilder();
            Document doc = docBuilder.parse(fXmlFile);
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
   
  
    /**
     * Mehtode Retourne les groupes auxquels est inscit l'utilisateur
     * @param login
     * @return String de groupes
     */
   public String getUserGroups(String login){
	   try {
			System.out.println("group login "+login);
		   List<String> group =  new ArrayList<>();

			DocumentBuilderFactory dbFct = DocumentBuilderFactory.newInstance();
	        dbFct.setIgnoringElementContentWhitespace(true);

		    DocumentBuilder builder;
			builder = dbFct.newDocumentBuilder();
			Document doc = builder.parse(this.dbPath);
		    doc.getDocumentElement().normalize();
		         
		    NodeList node = doc.getElementsByTagName("group");
		    
		    
		    for(int i =0; i< node.getLength(); i++) {
		    	
		    	Node item = node.item(i);
		    	
		    	if(item.getNodeType() == Node.ELEMENT_NODE) {
		    		Element el = (Element) item;
		    		//System.out.println(getValue("userLogin", el));
		    		if(userInGroup("userLogin", el, login)){
	                        group.add(getValue("name", el));
	                        
	                   }
		    	}
		    }
		    return this.arrToString(group);
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
			return null;
		}
   }
   
   /**
    * Retourne les utilisateurs appertenants à  un groupe
    * @param name nom du Groupe
    * @return List d'utilisateur
    */
   public  List<String> getUsersByGroup(String name){
	   try {
		   
			System.out.println("group name "+name);
		   List <String> user =  new ArrayList<>();
		   
			DocumentBuilderFactory dbFct = DocumentBuilderFactory.newInstance();
	        dbFct.setIgnoringElementContentWhitespace(true);

		    DocumentBuilder builder;
			builder = dbFct.newDocumentBuilder();
			Document doc = builder.parse(this.dbPath);
		    doc.getDocumentElement().normalize();
		         
		    NodeList node = doc.getElementsByTagName("group");
		    
		    
		    
		    for(int i =0; i< node.getLength(); i++) {
		    	
		    	Node item = node.item(i);
		    	
		    	if(item.getNodeType() == Node.ELEMENT_NODE) {
		    		Element el = (Element) item;
		    		
		    		//System.out.println(getValue("userLogin", el));
		    		if(isMatchGroup("name", el, name)){
		    			System.out.println("c'est ok");
	                        user = this.findUsersInGroup("userLogin", el, name);
	                        
	                   }
		    	}
		    }
		    return user;
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			//e.printStackTrace();
			return null;
		}
   }
   
   
   public  boolean isMatchGroup(String tag, Element el,String login) {
	   boolean isIn = false;
	   NodeList t = el.getElementsByTagName(tag);
	 	
	 	for(int i=0; i<t.getLength(); i++) {
	 		if(formatXml(t.item(i).getTextContent()).equals(login)) {
	 			
	 			isIn = true;
	 			break;
	 		};
	 	}
		return  isIn;
	  }
   

   
   private  List<String> findUsersInGroup(String tag, Element el,String login) {
	                  
	   NodeList t = el.getElementsByTagName(tag);
	   List <String> res = new ArrayList();
	 	for(int i=0; i<t.getLength(); i++) {
	 		res.add(formatXml(t.item(i).getTextContent()));
	 			
	 		
	 	}
		return  res;
	  }
	 
   public  boolean userInGroup(String tag, Element el,String login) {
	   boolean isIn = false;
	   NodeList t = el.getElementsByTagName(tag);
	 	
	 	for(int i=0; i<t.getLength(); i++) {
	 		if(formatXml(t.item(i).getTextContent()).equals(login)) {
	 			
	 			isIn = true;
	 			break;
	 		};
	 	}
		return  isIn;
	  }
	 
   	
   public String formatXml(String s) {
	   return s.replace("\n", "").replace("\r", "").trim();
   }
	 
	public String arrToString(List<String> l) {
		String res ="";
		for(int i =0; i<l.size(); i++) {
			res+=l.get(i)+";";
		}
		return res.substring(0, res.length() -1);
	}
	
	/**
	 * Retourne l'instance Database
	 * @return
	 */
	 public static Database getInstance(String filePath)
	    {           
	        if (INSTANCE == null)
	        {   INSTANCE = new Database(filePath); 
	        }
	        return INSTANCE;
	    }
	
	 
	

}
