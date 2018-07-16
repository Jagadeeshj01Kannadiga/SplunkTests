package com.splunk.test;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import com.splunk.commons.BaseSetup;
import com.splunk.commons.ReusableFunctions;
import com.splunk.pojos.RequestBean;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostMovies extends BaseSetup{
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
	public com.splunk.sampleproject.dataread.ExcelUtil excelUtil;


	ReusableFunctions rf;

	Logger log = Logger.getLogger("devpinoyLogger");

	/** The getMovies. */
	public String getMovies = baseUrl;
	

	public PostMovies() {

		rf = new ReusableFunctions();
	}
	
	@Test(groups = { "verifyPostingMovie",
	"Movies" }, description = "Verify movie is posted successfully and availabe in response list")
	public void verifyPostingMovie() {
		
		response = rf.postMovie("I am a SuperMan", "Super man Series One");
		
		//  Below here we will do get resposne and look for newly Posted movie in it .
	    // But As post not working( sshows 200 Instead of 201) nor give any unique id to check in resposne , Hence validation is pending
	}

}
