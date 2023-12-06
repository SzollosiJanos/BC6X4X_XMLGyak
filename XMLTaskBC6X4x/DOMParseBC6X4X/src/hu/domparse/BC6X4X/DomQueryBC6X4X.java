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
public class DomQueryBC6X4X {
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
        
       //KiĂ­rja azokat a cĂ©geket, amelyeket 2017 elĹ‘tt alapĂ­tottak
        NodeList alapitas = doc.getDocumentElement().getElementsByTagName("ceg");
        for (int i = 0; i < alapitas.getLength(); i++) {
            NodeList query = alapitas.item(i).getChildNodes();
            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName().equals("alapitasIdeje")){
                	NodeList query2 = query.item(j).getChildNodes();
                	 for (int k = 0; k < query2.getLength(); k++) {
                		 if (query2.item(k).getNodeName().equals("ev")&& Integer.parseInt(query2.item(k).getTextContent()) <2017) {
                			 
                			 listData(alapitas.item(i).getChildNodes(), "");
                		 }
                	 }
                	}
                }
            }
        

        System.out.println("-------------------------------------------------------------");

        
        //TermĂ©kek kiĂ­rsa, ahol az eladĂˇsi Ăˇr tĂ¶bb, mint 1000$
        NodeList termek = doc.getDocumentElement().getElementsByTagName("termek");
        for (int i = 0; i < termek.getLength(); i++) {
            NodeList query = termek.item(i).getChildNodes();
            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName().equals("eladasiAr") && Integer.parseInt(query.item(j).getTextContent()) > 1000){
                    listData(termek.item(i).getChildNodes(), "");
                }
            }
        }

        System.out.println("-------------------------------------------------------------");

        
        //ProgramozĂł dolgozĂłk adatainak kiĂ­rĂˇsa
        NodeList programozok = doc.getDocumentElement().getElementsByTagName("dolgozo");
        for (int i = 0; i < programozok.getLength(); i++) {
            NodeList query = programozok.item(i).getChildNodes();
            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName().equals("munkakor") && query.item(j).getTextContent().equals("ProgramozĂł")){
                    listData(programozok.item(i).getChildNodes(), "");
                }
            }
        }
        
        System.out.println("-------------------------------------------------------------");
        //KiĂ­rja azoknak a termĂ©keknek az adatait, amik pontosan 3 alkatrĂ©sszel rendelkeznek
        NodeList termekek = doc.getDocumentElement().getElementsByTagName("termek");
        for (int i = 0; i < termekek.getLength(); i++) {
            NodeList query = termekek.item(i).getChildNodes();
            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName().equals("alkatreszek")){
                	NodeList query2 = query.item(j).getChildNodes();
                	 if((query2.getLength()-1)/2==3) {
            			 listData(termekek.item(i).getChildNodes(), "");
            		 }
                	}
                }
            }
        
        System.out.println("-------------------------------------------------------------");
        //KiĂ­rja azokat a dolgozĂłkat, akiknek a fizetĹ‘se oszthatĂł 3-al
        NodeList dolgozok = doc.getDocumentElement().getElementsByTagName("dolgozo");
        for (int i = 0; i < dolgozok.getLength(); i++) {
            NodeList query = dolgozok.item(i).getChildNodes();
            for (int j = 0; j < query.getLength(); j++) {
                if (query.item(j).getNodeName().equals("fizetes") && Integer.parseInt(query.item(j).getTextContent())%3==0){
                    listData(dolgozok.item(i).getChildNodes(), "");
                }
            }
        }
        
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            transformer.transform(new DOMSource(doc), new StreamResult("XMLBC6X4XQUARY.xml"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Document introduceFile(File xmlFile){
        Document doc = null;

        try {
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

        if (nodeList != null) {
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
