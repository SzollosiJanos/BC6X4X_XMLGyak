package domBC6X4X1115;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryBC6X4X {

	public static void main(String[] args) throws Exception,IOException, SAXException, ParserConfigurationException, TransformerException {
        File inputFile = new File("orarendBC6X4X.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = documentBuilder.parse(inputFile);

        Node orarend = doc.getFirstChild();
        NodeList orak = doc.getElementsByTagName("ora");

        ArrayList<String> targynevek = new ArrayList<String>();
        ArrayList<String> oktatok = new ArrayList<String>();
        for (int i=0;i<orak.getLength();i++) {
            NodeList oraelement = orak.item(i).getChildNodes();
            for(int k=0;k<orak.item(i).getChildNodes().getLength();k++){
            	if(orak.item(i).getChildNodes().item(k).getNodeName()=="targy"){
            		targynevek.add(orak.item(i).getChildNodes().item(k).getTextContent());
            	}else if(orak.item(i).getChildNodes().item(k).getNodeName()=="oktato"){
            		
            		oktatok.add(orak.item(i).getChildNodes().item(k).getTextContent());
            	}
            }
		}
        System.out.println("Tárgyak nevei:");
        for (int i = 0; i < targynevek.size(); i++) {
        	
        	System.out.println(targynevek.get(i));
		}
        System.out.println("Oktatok nevei:");
        for (int i = 0; i < oktatok.size(); i++) {
        	
        	System.out.println(oktatok.get(i));
		}

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);

        System.out.println("Módosított XML fájl");
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);
    }

}
