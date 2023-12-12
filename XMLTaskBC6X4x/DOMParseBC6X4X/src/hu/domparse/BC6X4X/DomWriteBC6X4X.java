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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import org.w3c.dom.*;
public class DomWriteBC6X4X {

	public static void main(String[] args) {
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("gyartocegek");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaBC6X4X.xsd");
            document.appendChild(rootElement);
            cegek(document,rootElement,"1","ABC Zrt","Zrt","2010","02","15",Arrays.asList("Kovács István","Nagy Mária","Szabó János"));
            cegek(document,rootElement,"2","DEF Kft","Kft","2015","06","10",Arrays.asList("Kiss Andrea","Nagy Gábor","Kovács Anikó"));
            cegek(document,rootElement,"3","GHI Bt","Bt","2018","11","25",Arrays.asList("Nagy Péter","Kiss Judit","Kovács Bence"));
            
            telepules(document,rootElement,"1","1098","Pécs","Király utca","25");
            telepules(document,rootElement,"2","1024","Budapest","Alkotás utca","8");
            telepules(document,rootElement,"3" ,"3300","Eger","Széchenyi utca","12");
            
            ceg_telepules(document,rootElement,"1" ,"1","5000" );
            ceg_telepules (document,rootElement,"2" ,"2" ,"4000");
            ceg_telepules (document,rootElement,"3" ,"3","3000");
            	        
            termek(document,rootElement,"1","Laptop XYZ","1500",Arrays.asList("Memória","Processzor","Tárhely"),Arrays.asList("Andrásné","Kiss Géza","Nagy Balázs"));
            termek(document,rootElement,"2","Smartphone Plus","800",Arrays.asList("Kijelző","Akku","Kamera"),Arrays.asList("Nagy Katalin","Kiss József","Szabó Anna"));
            termek(document,rootElement,"3","Asztali Számítógép Pro","2500",Arrays.asList("Processzor","RAM","GPU"),Arrays.asList("Kiss Péter","Nagy Zoltán","Szabó Eszter"));
            
            ceg_termek(document,rootElement,"1","1");
            ceg_termek(document,rootElement,"2","2");
            ceg_termek(document,rootElement,"3","3");
            
            gyartasi(document,rootElement,"1","1","800","24","3%","25");
            gyartasi(document,rootElement,"2","2","400","25","2%","15");
            gyartasi(document,rootElement,"3","3","1200","26","1%","30");
            
            dolgozo(document,rootElement,"1","1","Kis Anikó","2021-08-05","Rendszergazda","3500");
            dolgozo(document,rootElement,"2","2","Nagy Gábor","2022-01-15","Programozó","4200");
            dolgozo(document,rootElement,"3","3","Szabó Mónika","2023-05-20","HR Menedzser","3800");
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            printDocument(document);   
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	private static void cegek(Document document, Element rootElement, String cegID, String nev, String tipus, String ev, String honap, String nap, List<String> tulajdonosok) {
		Element ceg = document.createElement("ceg");
		ceg.setAttribute("cegID", cegID);
		
		Element nevE = createElement(document, "nev", nev);
		ceg.appendChild(nevE);
		Element tipusE = createElement(document, "tipus", tipus);
		ceg.appendChild(tipusE);
		Element alapitasIdejeE = document.createElement("alapitasIdeje");
		Element eve = createElement(document, "ev", ev);
		Element honape = createElement(document, "honap", honap);
		Element nape = createElement(document, "nap", nap);
		alapitasIdejeE.appendChild(eve);
		alapitasIdejeE.appendChild(honape);
		alapitasIdejeE.appendChild(nape);
		ceg.appendChild(alapitasIdejeE);
		Element tulajdonosokE = document.createElement("tulajdonosok");
		for (String s : tulajdonosok) {
            Element temp = createElement(document, "tulajdonos", s);
            tulajdonosokE.appendChild(temp);
        }
		ceg.appendChild(tulajdonosokE);
		rootElement.appendChild(ceg);
	}

	private static void telepules(Document document, Element rootElement, String telepulesid, String iranyitoszam, String nev, String utca, String szam) {
		Element telepules = document.createElement("telepules");
		telepules.setAttribute("telepulesID", telepulesid);
		
		Element irE = createElement(document, "iranyitoszam", iranyitoszam);
		telepules.appendChild(irE);
		Element nevE = createElement(document, "telepulesNeve", nev);
		telepules.appendChild(nevE);
		Element utcaE = createElement(document, "utca", utca);
		telepules.appendChild(utcaE);
		Element szamE = createElement(document, "hazszam", szam);
		telepules.appendChild(szamE);
		
		rootElement.appendChild(telepules);
	}
	
	private static void ceg_telepules(Document document, Element rootElement, String cegref, String telepulesref, String alkalmazottak) {
		Element ceg_telepules = document.createElement("ceg_telepules");
		ceg_telepules.setAttribute("cegREF", cegref);
		ceg_telepules.setAttribute("telepulesREF", telepulesref);
		Element alkalmazottakE = createElement(document, "alkalmazottak", alkalmazottak);
		ceg_telepules.appendChild(alkalmazottakE);
		
		rootElement.appendChild(ceg_telepules);
	}
	
	private static void termek(Document document, Element rootElement, String termekid, String nev, String ar, List<String> alkatreszek, List<String> vevok) {
		Element termek = document.createElement("termek");
		termek.setAttribute("termekID", termekid);
		
		Element nevE = createElement(document, "termekNeve", nev);
		termek.appendChild(nevE);
		Element arE = createElement(document, "eladasiAr", ar);
		termek.appendChild(arE);
		Element alkE = document.createElement("alkatreszek");
		for (String s : alkatreszek) {
            Element temp = createElement(document, "alkatresz", s);
            alkE.appendChild(temp);
        }
		termek.appendChild(alkE);
		
		Element vevokE = document.createElement("vevok");
		for (String s : vevok) {
            Element temp = createElement(document, "vevo", s);
            vevokE.appendChild(temp);
        }
		termek.appendChild(vevokE);
		rootElement.appendChild(termek);
	}
	
		
	private static void ceg_termek(Document document, Element rootElement, String cegref, String termekref) {
		Element ceg_termek = document.createElement("ceg_termek");
		ceg_termek.setAttribute("cegREF", cegref);
		ceg_termek.setAttribute("termekREF", termekref);
		
		rootElement.appendChild(ceg_termek);
	}
	
	
	private static void gyartasi(Document document, Element rootElement, String id, String termekREF, String koltseg, String ido, String selejtszam,String dolgozoszam) {
		Element gyartasi = document.createElement("gyartasiInformacio");
		gyartasi.setAttribute("gyartasiInformacioID", id);
		gyartasi.setAttribute("termekREF", termekREF);
		
		Element koltsegE = createElement(document, "gyartasiKoltseg", koltseg);
		gyartasi.appendChild(koltsegE);
		Element gyartasiIdoE = createElement(document, "gyartasiIdo", ido);
		gyartasi.appendChild(gyartasiIdoE);
		Element selejtekSzamaE = createElement(document, "selejtekSzama", selejtszam);
		gyartasi.appendChild(selejtekSzamaE);
		Element dolgozokSzamaE = createElement(document, "dolgozokSzama", dolgozoszam);
		gyartasi.appendChild(dolgozokSzamaE);
		
		rootElement.appendChild(gyartasi);
	}
	
	private static void dolgozo(Document document, Element rootElement, String id, String cegref, String nev, String datum, String munkakor,String fizetes) {
		Element dolgozo = document.createElement("dolgozo");
		dolgozo.setAttribute("dolgozoID", id);
		dolgozo.setAttribute("cegREF", cegref);
		
		Element nevE = createElement(document, "dolgozoNeve", nev);
		dolgozo.appendChild(nevE);
		Element belepesiDatumE = createElement(document, "belepesiDatum", datum);
		dolgozo.appendChild(belepesiDatumE);
		Element munkakorE = createElement(document, "munkakor", munkakor);
		dolgozo.appendChild(munkakorE);
		Element fizetesE = createElement(document, "fizetes", fizetes);
		dolgozo.appendChild(fizetesE);
		
		rootElement.appendChild(dolgozo);
	}
	
	
	 private static Element createElement(Document document, String name, String value) {
	     Element element = document.createElement(name);
	     element.appendChild(document.createTextNode(value));
	     return element;
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
	 
	 private static void printDocument(Document document) {
	        try {

	            System.out.print("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n");

	            
	            System.out.print("<gyartocegek xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaBC6X4X.xsd\">\n");

	            
	            NodeList nodeList = document.getDocumentElement().getChildNodes();
	            listData(nodeList, "");
	            
	            try {
		            TransformerFactory transformerFactory = TransformerFactory.newInstance();
		            Transformer transformer = transformerFactory.newTransformer();
		            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		            
		            transformer.transform(new DOMSource(document), new StreamResult("XMLBC6X4X2.xml"));

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
	            
	            System.out.print("</gyartocegek>");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
