package hu.domparse.BC6X4X;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
public class DomModifyBC6X4X {
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

        //CĂ©g alapĂ­tĂˇs Ă©vĂ©nek csĂ¶kkentĂ©se 10 Ă©vvel
        NodeList evek = doc.getDocumentElement().getElementsByTagName("ev");
        for (int i = 0; i < evek.getLength(); i++) {
            evek.item(i).setTextContent(Integer.toString(Integer.parseInt(evek.item(i).getTextContent())-10));
        }

        evek = doc.getDocumentElement().getElementsByTagName("ceg");
        for (int i = 0; i < evek.getLength(); i++) {
            listData(evek.item(i).getChildNodes(), "");
        }
        

        System.out.println("-------------------------------------------------------------");

        //DolgozĂłk fizetĂ©sĂ©nek nĂ¶velĂ©se a duplĂˇjĂˇra
        NodeList dolgozok = doc.getDocumentElement().getElementsByTagName("fizetes");
        for (int i = 0; i < dolgozok.getLength(); i++) {
            dolgozok.item(i).setTextContent(Integer.toString(Integer.parseInt(dolgozok.item(i).getTextContent())*2));
        }

        dolgozok = doc.getDocumentElement().getElementsByTagName("dolgozo");
        for (int i = 0; i < dolgozok.getLength(); i++) {
            listData(dolgozok.item(i).getChildNodes(), "");
        }

        System.out.println("-------------------------------------------------------------");

        //TermĂ©kek eladĂˇsi ĂˇrĂˇnak nĂ¶velĂ©se 200-al
        NodeList termekek = doc.getDocumentElement().getElementsByTagName("eladasiAr");
        for (int i = 0; i < termekek.getLength(); i++) {
            termekek.item(i).setTextContent(Integer.toString(Integer.parseInt(termekek.item(i).getTextContent())+200));
        }

        termekek = doc.getDocumentElement().getElementsByTagName("termek");
        for (int i = 0; i < termekek.getLength(); i++) {
            listData(termekek.item(i).getChildNodes(), "");
        }

        System.out.println("-------------------------------------------------------------");

        //DolgozĂłk munkakĂ¶rĂ©nek mĂłdosĂ­tĂˇsa Ă¶sszeszerelĹ‘re
        NodeList munkakorok = doc.getDocumentElement().getElementsByTagName("munkakor");
        for (int i = 0; i < munkakorok.getLength(); i++) {
        	munkakorok.item(i).setTextContent("Ă–sszeszerelĹ‘");
        }

        munkakorok = doc.getDocumentElement().getElementsByTagName("dolgozo");
        for (int i = 0; i < munkakorok.getLength(); i++) {
             listData(munkakorok.item(i).getChildNodes(), "");
        }

        System.out.println("-------------------------------------------------------------");

        //Minden cĂ©g tulajdonosĂˇhoz hozzĂˇadom a sajĂˇt nevemet
        NodeList tulajdonosok = doc.getDocumentElement().getElementsByTagName("tulajdonosok");
        for (int i = 0; i < tulajdonosok.getLength(); i++) {
        	Node newNode = tulajdonosok.item(i).appendChild(doc.createElement("tulajdonos"));
        	newNode.setTextContent("SzĂ¶llĹ‘si JĂˇnos");
        }

        tulajdonosok = doc.getDocumentElement().getElementsByTagName("ceg");
        for (int i = 0; i < tulajdonosok.getLength(); i++) {
            listData(tulajdonosok.item(i).getChildNodes(), "");
        }
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            transformer.transform(new DOMSource(doc), new StreamResult("XMLBC6X4XModified.xml"));

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
