package domBC6X4X1108;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadBC6X4X  {
    public static void main(String arg[]) throws ParserConfigurationException, SAXException, IOException {
        try {
            File xmlFile = new File("orarendBC6X4X.xml");
        
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
   
            Element rootelem = doc.getDocumentElement();
            System.out.println("root: " + rootelem.getNodeName());
            
            listelement(rootelem, "");
                        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listelement(Node node, String indent) {
	    if (node.getNodeType() == Node.ELEMENT_NODE) {
	        System.out.println(indent + "Element: " + node.getNodeName());

	        if (node.hasAttributes()) {
	            NamedNodeMap attrib = node.getAttributes();
	            for (int i = 0; i < attrib.getLength(); i++) {
	                Node attribute = attrib.item(i);
	                System.out.println(indent + "  Attribute: " + attribute.getNodeName() + " = " + attribute.getNodeValue());
	            }
            }
	        if (node.hasChildNodes()) {
	            NodeList childnote = node.getChildNodes();
	            for (int i = 0; i < childnote.getLength(); i++) {
	                Node child = childnote.item(i);
	                listelement(child, indent + "  ");
	                }
	        }
	        } else if (node.getNodeType() == Node.TEXT_NODE) {
	            String datas = node.getNodeValue().trim();
	            if (!datas.isEmpty()) {
	                System.out.println(indent + "Data: " + datas);
	            }
	        }
	}

}
