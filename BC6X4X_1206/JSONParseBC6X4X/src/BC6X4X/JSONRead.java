package BC6X4X;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONRead {

	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		
		try{
			Object obj=parser.parse(new FileReader("orarendBC6X4X.json"));
			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject.toJSONString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
