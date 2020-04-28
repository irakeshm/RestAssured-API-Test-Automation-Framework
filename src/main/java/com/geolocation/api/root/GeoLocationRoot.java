package com.geolocation.api.root;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.geolocation.api.utilities.TestUtility;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GeoLocationRoot {
	
	public static Response apiResponse;
	public static Properties prop;
	public static RequestSpecification requestSpec;
	public static RestAssured apiRequest;
	public static String baseUrl;
	public static String apiKey;
	static Logger log = Logger.getLogger(GeoLocationRoot.class);
	
	public GeoLocationRoot()
	{		
		try 
		{
			prop = new Properties();
			FileInputStream configFile = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/geolocation/api/configuration/config.properties");
			prop.load(configFile);
		}
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
			log.error("Exception Occured "+ex.getMessage());
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			log.error("Exception Occured "+ex.getMessage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			log.error("Exception Occured "+ex.getMessage());
		}
		
	}
	
	public static void initialization()
	{		
		baseUrl = prop.getProperty("apibaseurl");
		apiKey= prop.getProperty("apikey");
	}
	
	public static Response getResponse(String address)
	{
		address.replace(" ", "+");
		apiResponse=RestAssured.request(Method.GET, baseUrl+"address="+address+"&key="+apiKey);		
		return apiResponse;
	}
	
	public static String[] GetActualResultfromResponse(Response response)
	{
		String[] latandlngValues=new String[2];
		String jsonResponse=apiResponse.getBody().asString();
		JSONObject jsonObject = new JSONObject(jsonResponse);	
		JSONArray tsmresponse = (JSONArray) jsonObject.get("results");		
	    for(int i=0; i<tsmresponse.length();)
	    {
	    	latandlngValues[0]=tsmresponse.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
	    	latandlngValues[1]=tsmresponse.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
	    }
	    return latandlngValues;
	}
		
	public static int getResponseStatusCode(Response response)
	{
		return response.getStatusCode();
	}
	
	
	@SuppressWarnings("null")
	public static String[] getExpectedResult(String address)
	{
		int index=0;
		Object[][] data= TestUtility.getExpectedResultCSVData();
		String[] returnData=new String[3];
		
		for(index=0;index<data.length;index++)
		{
			if(data[index][0].toString().contains(address))
			{
				returnData[0]=data[index][1].toString();
				returnData[1]=data[index][2].toString();
				returnData[2]=data[index][3].toString();
				break;
			}
		}
		return returnData;
	}

}
