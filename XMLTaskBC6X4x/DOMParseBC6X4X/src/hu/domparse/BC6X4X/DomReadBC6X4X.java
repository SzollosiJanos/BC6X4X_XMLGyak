package hu.domparse.BC6X4X;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
public class DomReadBC6X4X {

	 public static void main(String[] args) {
	        File xmlFile = new File("XMLBC6X4X.xml");
	        Document doc = introduceFile(xmlFile);

	        if (doc == null) {
	        	 System.out.println("The document is null");
		         System.exit(-1);
	            
	        } else {
	        	doc.getDocumentElement().normalize();
	            System.out.println("Root: " + doc.getDocumentElement().getNodeName());
	        }

	        NodeList nodeList = doc.getDocumentElement().getChildNodes();
	        String indent = "";
	        listData(nodeList, indent);
	    }

	    public static Document introduceFile(File xmlFile){
	        Document doc = null;

	        try{
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
	            doc = dbBuilder.parse(xmlFile);
	        } catch (ParserConfigurationException | SAXException | IOException e) {
	            e.printStackTrace();
	        }
	        return doc;
	    }

	    public static void listData(NodeList nodeList, String indent){
	        indent += "\t";

	        if(nodeList != null) {
	            for (int i = 0; i < nodeList.getLength(); i++) {
	                Node node = nodeList.item(i);
	                if (node.getNodeType() == Node.ELEMENT_NODE && !node.getTextContent().trim().isEmpty()) {
	                    System.out.println(indent + "{" + node.getNodeName() + "}:");
	                    NodeList nodeList_new = node.getChildNodes();
	                    listData(nodeList_new, indent);
	                } else if (node instanceof Text){
	                    String value = node.getNodeValue().trim();
	                    if (value.isEmpty()){
	                        continue;
	                    }
	                    System.out.println(indent + node.getTextContent());
	                }
	            }
	        }
	    }
}