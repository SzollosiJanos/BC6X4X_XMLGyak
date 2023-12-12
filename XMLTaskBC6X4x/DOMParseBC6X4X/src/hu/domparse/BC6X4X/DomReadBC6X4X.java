package hu.domparse.BC6X4X;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
	        	System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	            System.out.println("<" + doc.getDocumentElement().getNodeName()+" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaBC6X4X.xsd\">");
	        }

	        NodeList nodeList = doc.getDocumentElement().getChildNodes();
	        String indent = "";
	        listData(nodeList, indent);
	        System.out.println("</" + doc.getDocumentElement().getNodeName()+">");
	        try {
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "no");
	            
	            transformer.transform(new DOMSource(doc), new StreamResult("XMLBC6X4Xreadoutput.xml"));

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
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
	                    System.out.print(indent + "<" + node.getNodeName());
	                    if (node.hasAttributes()) {
	                        for (int k = 0; k < node.getAttributes().getLength(); k++) {
	                            Node attribute = node.getAttributes().item(k);
	                            System.out.print(" "+attribute.getNodeName()+"=\""+attribute.getNodeValue()+"\"");
	                        }
	                        System.out.print(">");
	                    }else {
	                    	System.out.print(">");
	                    }
	                    	
	                    NodeList nodeList_new = node.getChildNodes();
	                    if(node.getChildNodes().getLength()==1 && nodeList_new.item(0).getNodeType()!=Node.ELEMENT_NODE) {
	                    	String value = nodeList_new.item(0).getNodeValue().trim();
		                    if (value.isEmpty()){
		                        continue;
		                    }
		                    System.out.print(nodeList_new.item(0).getTextContent());
		                    System.out.println("</" + node.getNodeName() + ">");
	                    }else {
	                    	System.out.print("\n");
	                    	listData(nodeList_new, indent);
	                    	 System.out.println(indent+"</" + node.getNodeName() + ">");
	                    }
	                   
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
