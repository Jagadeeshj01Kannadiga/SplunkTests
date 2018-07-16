package com.splunk.commons;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;

import com.splunk.pojos.RequestBean;
import com.splunk.sampleproject.dataread.ExcelUtil;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ReusableFunctions extends BaseSetup{
	

	/** The gson. */
	public static Gson gson = new Gson();

	/** The json object. */
	static JSONObject jsonObject = null;

	/** The response. */
	public static Response response = null;

	/** The json as string. */
	public static String jsonAsString;

	/** The formatter. */
	public DataFormatter formatter;

	/** The excel util. */
	public ExcelUtil excelUtil;


	RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");
	
	
	/**
	 * 
	 * @param endPoint
	 * @param statusCode
	 * @return
	 */
	public Response checkSuccessFullResposne(String endPoint, int statusCode) {
		try {	
			response = given().accept("application/json").when().get(endPoint).then().statusCode(statusCode)
			.extract().response();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured while getting resposne Hence Failing " + response.getStatusCode());
		}
		return response;

	}
	/**
	 * 
	 * @param endPoint
	 * @param statusCode
	 * @param sf
	 * @return
	 */
	public SoftAssert checkResponse(String endPoint, int statusCode,SoftAssert sf) {
		
		if(!endPoint.matches("^(http|https)://.*$")) {
			sf.assertFalse(true, "Invalid End Point :"+endPoint);
		}else {
		response = given().accept("application/json").when().get(endPoint);
		sf.assertEquals(response.getStatusCode(), 200," Did not get Successfull responase for Endpoint:"+endPoint);
		Reporter.log("Got Successful result for Endpoint:"+endPoint);
		}
		return sf;
	}
	
	
	
	/**
	 * 
	 * @param endPoint
	 * @param statusCode
	 * @return
	 */
	public Response checkSuccessFullResposneQueryPram(String endPoint, int statusCode) {
		try {	
			response = given().accept("application/json").queryParam("q", "batman").when().get(endPoint).then().statusCode(statusCode)
			.extract().response();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured while getting resposne Hence Failing" + response.getStatusCode());
		}
		return response;

	}
	
	/**
	 * Post a Movie , Here Ideally we read field values from SCV and With fields as Constant class 
	 */
	@Test(groups = { "postMovie", "Movies" }, description = "Post a new Moivie")
	public Response postMovie(String name , String description) {
		try {
		RequestBean body = new RequestBean();
		body.setName(name);
		body.setDescription(description);
		response = request.body(body).post();
		Assert.assertTrue(response.getStatusCode() == 201, "Movie Posting failed with resposne code : "+response.getStatusCode());
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured while getting resposne Hence Failing" + response.getStatusCode());
		}
		return response;
	}
	/**
	 * 
	 * @param endPoint
	 * @param statusCode
	 * @param queryKey
	 * @param queryValue
	 * @return
	 */
	public Response checkSuccessFullResposneQueryPram(String endPoint, int statusCode, String queryKey , String queryValue) {
		try {	
		
			response = given().accept("application/json").queryParam(queryKey, queryValue).when().get(endPoint).then().statusCode(statusCode)
			.extract().response();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured while getting resposne Hence Failing" + response.getStatusCode());
		}
		return response;

	}
	/**
	 * 
	 * @param qparam
	 * @param endPoint
	 * @param statusCode
	 * @return
	 */
	public Response checkSuccessFullResposneQueryPrams(Map qparam , String endPoint, int statusCode) {
		try {	

			response = given().accept("application/json").queryParams(qparam).when().get(endPoint).then().statusCode(statusCode)
					.extract().response();
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Exception occured while getting resposne Hence Failing" + response.getStatusCode());
		}
		return response;

	}

}
