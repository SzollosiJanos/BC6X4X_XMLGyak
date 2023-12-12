package BC6X4X;

import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class JSONWrite {

		 public static void main(String[] args)
		    {
			 	JSONObject jsonRoot = new JSONObject();
		        JSONArray orak = new JSONArray();
		        orak.add(addora(1,1,"Adatkezelés XML-ben", "Kedd", "12:00", "14:00", "XXXII A1 magasfsz", "Dr. Bednarik László","Programtervező Informatikus"));
		        orak.add(addora(1,2,"Adatkezelés XML-ben", "Szerda", "10:00", "12:00", "In/101 In I. e. 101", "Dr. Bednarik László","Programtervező Informatikus"));
		        
		        jsonRoot.put("ora", orak);
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("BC6X4X_orarend", jsonRoot);
		        
		        System.out.println(formatJSONStr(jsonObject.toJSONString(),2));
		        try
		        {
		        	FileWriter writer = new FileWriter("orarendBC6X4X.json");
		            writer.write(formatJSONStr(jsonObject.toJSONString(),2));
		            writer.close();	
		        }
		        catch(Exception e)
		        {
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
		 
		 
		    private static JSONObject addora(int id, int type,String tantargy, String nap, String tol, String ig, String helyszin, String oktato,String szak)
		    {
		        JSONObject ora = new JSONObject();
		        JSONObject ido = new JSONObject();
		        ido.put("nap", nap);
		        ido.put("tol", tol);
		        ido.put("ig", ig);
		        ora.put("@id", id);
		        ora.put("@tipus", type);
		        ora.put("targy", tantargy);
		        ora.put("idopont", ido);
		        ora.put("helyszin", helyszin);
		        ora.put("oktato", oktato);
		        ora.put("szak", szak);
		        
		        return ora;
		    }

}
