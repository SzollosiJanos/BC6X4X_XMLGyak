package domBC6X4X1115;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;



public class DomModifyBC6X4X {

	public static void main(String[] args) throws Exception,IOException, SAXException, ParserConfigurationException, TransformerException {
        File inputFile = new File("orarendBC6X4X.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = documentBuilder.parse(inputFile);

        Node orarend = doc.getFirstChild();
        NodeList orak = doc.getElementsByTagName("ora");

        
        for (int i=0;i<orak.getLength();i++) {
        	NamedNodeMap attr = orak.item(i).getAttributes();
            Node nodeAttr = attr.getNamedItem("tipus");
            nodeAttr.setTextContent("eloadas");
            NodeList oraelement = orak.item(i).getChildNodes();
            boolean hasoraado=false;
            for(int k=0;k<orak.item(i).getChildNodes().getLength();k++){
            	if(orak.item(i).getChildNodes().item(k).getNodeName()=="oraado"){
            		hasoraado=true;
            	}
            }
            if (!hasoraado) {
            	Node newNode = orak.item(i).appendChild(doc.createElement("oraado"));
                newNode.setTextContent("Bednarik László");
			}
		}
        
        


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);

        System.out.println("Módosított XML fájl");
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
        writeXMLToFile(doc,"orarendModifyBC6X4X.xml");
    }
	
	 private static void writeXMLToFile(Document doc, String filename) throws Exception {
	        javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
	        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

	        javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
	        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(new java.io.File(filename));
	        transformer.transform(source, result);
	    }

}
