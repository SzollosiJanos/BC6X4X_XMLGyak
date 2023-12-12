package BC6X4X;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONRead {

	public static void main(String[] args) throws ParseException {
		 try {
	            FileReader reader = new FileReader("orarendBC6X4X.json");
	            JSONParser jsonParser = new JSONParser();
	            JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);

	            JSONObject jsonRoot = (JSONObject) jsonObject.get("BC6X4X_orarend");
	            JSONArray orak = (JSONArray) jsonRoot.get("ora");

	            System.out.println("Órarend:");
	            for(int i=0; i<orak.size(); i++) {
	                System.out.println("Tárgy: "+((JSONObject)orak.get(i)).get("targy"));
	                System.out.print("Időpont: "+((JSONObject)((JSONObject)orak.get(i)).get("idopont")).get("nap")+" ");
	                System.out.print(((JSONObject)((JSONObject)orak.get(i)).get("idopont")).get("tol")+"-");
	                System.out.println(((JSONObject)((JSONObject)orak.get(i)).get("idopont")).get("ig"));
	                System.out.println("Helyszín: "+((JSONObject)orak.get(i)).get("helyszin"));
	                System.out.println("Oktató: "+((JSONObject)orak.get(i)).get("oktato"));
	                System.out.println("Szak: "+((JSONObject)orak.get(i)).get("szak")+"\n");
	            }
	            
	            System.out.println(formatJSONStr(jsonObject.toJSONString(),2));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 
		 
	}
	
	
	
	 public static String formatJSONStr(final String json_str, final int indent_width) {
		    final char[] chars = json_str.toCharArray();
		    String ret = "";
		    int indent=0;
		    for (int i = 0; i < chars.length; i++) {
		            switch (chars[i]) {
		            case '{':
		            case '[':
		            	indent+=indent_width;
		                ret += chars[i] + "\n" + getIndent(indent,indent_width);
		                continue;
		            case '}':
		            case ']':
		            	indent-=indent_width;
		                ret += "\n" + getIndent(indent,indent_width) + chars[i];
		                continue;
		            case ':':
		                ret += chars[i] + " ";
		                continue;
		            case ',':
		                ret += chars[i] + "\n" + getIndent(indent,indent_width);
		                continue;
		            case '\"':
		            	ret += chars[i];
			            continue;
		            default:
		                if (Character.isWhitespace(chars[i])) continue;
		            }

		        ret += chars[i] + (chars[i] == '\\' ? "" + chars[++i] : "");
		    }

		    return ret;
		}
	 public static String getIndent(int indent,int indent_width) {
		 String ret="";
		 for (int i = 0; i < indent; i++) {
				 ret+=" ";
		 }
		 return ret;
	 }

}
