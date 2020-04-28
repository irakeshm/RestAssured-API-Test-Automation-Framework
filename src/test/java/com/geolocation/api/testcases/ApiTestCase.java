package com.geolocation.api.testcases;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.geolocation.api.root.GeoLocationRoot;
import com.geolocation.api.utilities.TestUtility;

import io.restassured.response.Response;

public class ApiTestCase extends GeoLocationRoot {
	Logger log = Logger.getLogger(ApiTestCase.class);

	public ApiTestCase() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
	}

	@DataProvider(name = "getInputTestData")
	public Object[][] getInputTestData() {
		Object data[][] = TestUtility.getInputCSVData();
		return data;
	}

	@Test(dataProvider="getInputTestData")
	public void verifyApiResponse(String address) {
		
		log.info("verifyApiResponse Test Started!");
		try
		{
			String[] expectedResults=new String[3];
			String[] actualResults=new String[2];
			Response response=GeoLocationRoot.getResponse(address);
			log.info("Get Response for the API"+response.getBody().asString());
			expectedResults=GeoLocationRoot.getExpectedResult(address);
			log.info("Verify the Status Code of the Response");
			Assert.assertEquals(Integer.toString(GeoLocationRoot.getResponseStatusCode(response)), expectedResults[2]);
			actualResults= GeoLocationRoot.GetActualResultfromResponse(response);
			log.info("Verify the Latitude and Lognitude for the Address");
			Assert.assertTrue(actualResults[0].equals(expectedResults[0]));
			Assert.assertTrue(actualResults[1].equals(expectedResults[1]));
		}
		catch(Exception ex)
		{
			log.error("Exception Occured in verifyApiResponse Test "+ex.getMessage());
			Assert.fail();
		}
		
	}

	@AfterMethod
	public void tearDown() {
		
		log.info("verifyApiResponse Test Ended!");
	}

}
