package com.splunk.test;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.gson.Gson;
import com.jayway.restassured.response.Header;

import com.splunk.commons.BaseSetup;
import com.splunk.commons.ReusableFunctions;

import io.restassured.response.Response;

public class GetMovies extends BaseSetup {

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

	/** The response bean. */
	

	ReusableFunctions rf;

	Logger log = Logger.getLogger("devpinoyLogger");

	/** The getMovies. */
	public String getMovies = baseUrl;
	
	

	public GetMovies() {

		rf = new ReusableFunctions();
	}

	/**
	 * Verify response code.
	 */
	@Test(groups = { "verifyResponseCode", "Movies" }, description = "Verify reponse code is as Expected")
	public void verifyResponseCode() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
	}

	@Test(groups = { "checkDuplicate_poster_path", "Movies" }, description = "Test to Verify Duplicate poster path")
	public void checkDuplicate_poster_path() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
		ArrayList<String> poasterPaths = response.path("results.poster_path");
		Set<String> uniquePath = new HashSet<>();
		for (String poasterPath : poasterPaths) {		
			 if(!uniquePath.add(poasterPath)) {
			 Assert.fail("Found Duplicate poasterPath =>:"+poasterPath+" <= Hence Failing"
			 ); }
			 
		}
	}

	@Test(groups = { "checkForValid_poster_path", "Movies" }, description = "Test to Verify all poster_path are valid")
	public void checkForValid_poster_path() {
		int noOfNullPosterPath = 0;
		SoftAssert softAssert = new SoftAssert();
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
		ArrayList<String> poasterPaths = response.path("results.poster_path");

		for (String poasterPath : poasterPaths) {
			if (poasterPath != null) {
				softAssert = rf.checkResponse(poasterPath, 200, softAssert);
			} else {
				noOfNullPosterPath++;
			}
		}
		Reporter.log("Found :" + noOfNullPosterPath + "null poster paths.");
		softAssert.assertAll();

	}

	@Test(groups = { "checkForValid_poster_path", "Movies" }, description = "Test to Verify Movies with genre_ids == null should be first in response,if multiple movies have genre_ids == null, then sort by id (ascending). ")
	public void numOfNullGenreIds() {
		try {
			int i;
			response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
			List<List> genre_ids = new ArrayList<List>();
			ArrayList<Integer> id = response.path("results.id");
			Map<Integer, Integer> nullgen = new LinkedHashMap<>();

			int countOfnullGenre = 0;
			genre_ids = response.path("results.genre_ids");

			for (i = 0; i <= genre_ids.size() - 1; i++) {

				if (genre_ids.get(i).size() == 0) {
					countOfnullGenre++;
					nullgen.put(i, id.get(i));
				}
			}

			if (countOfnullGenre != 0 && countOfnullGenre > 1) {
				Collection<Integer> pp = nullgen.values();
				List tmp = new ArrayList(pp);
				Collections.sort(tmp);
				boolean sorted = tmp.equals(pp);
				Assert.assertTrue(tmp.equals(pp),
						"We have more than one empty genre_ids ,But they are not in ascending order of Id");
				Reporter.log("We have more than one empty genre_ids ,And they are not in ascending order of Id");
				System.out.println(sorted);
			} else {
				Assert.assertTrue(nullgen.keySet().contains(0),
						"We have one empty genre_ids ,But its not at first in response , Hence failing ");
				Reporter.log("We have one empty genre_ids , Its at first in response");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("We got Exception while Execution , Hence failing :" + ex.getStackTrace()[0]);
			Reporter.log("We got Exception while Execution , Hence failing :" + ex.getStackTrace()[0]);
		}

	}

	@Test(groups = { "checkNonNullGenreIdsAreSorted", "Movies" }, description = "Test to Verify movies that have non-null genre_ids, results should be sorted by id (ascending)")
	public void checkNonNullGenreIdsAreSorted() {
		int i;
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
		List<List> genre_ids = new ArrayList<List>();
		ArrayList<Integer> id = response.path("results.id");
		List<Integer> nonNullId = new LinkedList<>();
		genre_ids = response.path("results.genre_ids");

		for (i = 0; i <= genre_ids.size() - 1; i++) {
			if (genre_ids.get(i).size() != 0) {
				nonNullId.add(id.get(i));
			}
		}
		List tmp = new ArrayList(nonNullId);
		Collections.sort(tmp);
		boolean sorted = tmp.equals(nonNullId);
		Assert.assertTrue(tmp.equals(nonNullId), "Non Null Genre  Are not Sorted by Ids, Ids are:" + nonNullId);
		Reporter.log("Non Null Genre  Are  Sorted by Ids");
		log.info("Non Null Genre  Are  Sorted by Ids");
	}

	@Test(groups = { "checkNoOfMoviesGenereIdGrterThanFourHundred",
			"Movies" }, description = "Test to Verify number of movies whose sum of \"genre_ids\" > 400 should be no more than 7")
	public void checkNoOfMoviesGenereIdGrterThanFourHundred() {
		List<List> genre_ids = new ArrayList<List>();
		int i;
		int sum = 0;
		int count = 0;
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
		genre_ids = response.path("results.genre_ids");
		for (i = 0; i <= genre_ids.size() - 1; i++) {
			if (genre_ids.get(i).size() != 0) {
				for (int j = 0; j <= genre_ids.get(i).size() - 1; j++) {
					sum = sum + (Integer) genre_ids.get(i).get(j);

				}
				if (sum > 400) {
					count++;
				}

			}
		}

		Assert.assertTrue(count <= 7, "We have more than 7 GenereId sum greater than 400");
		Reporter.log("We have less than 7 GenereId sum greater than 400");
	}

	@Test(groups = { "checkforAtlestOnePalendromeTitle",
			"Movies" }, description = "Verify for Atlest any one Title contains  Palendromic Word")
	public void checkforAtlestOnePalendromeTitle() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
		ArrayList<String> title = response.path("results.title");
		boolean foundPalen = false;
		for (String str : title) {

			String[] titleWords = str.split(" ");
			for (String ss : titleWords) {
				System.out.println(ss);
				if (ss.toLowerCase().trim()
						.equals(new StringBuilder(ss.toLowerCase().trim()).reverse().toString()) == true) {
					Reporter.log("Found Title containing  Palendromic Word is : " + ss);
					foundPalen = true;
					break;

				}

			}
			if (foundPalen == true)
				break;
		}

		Assert.assertTrue(foundPalen, "Couldn't able to find Atlest any one Title containing  Palendromic Word");

	}

	@Test(groups = { "checkforTitleIsSubStrOfOtherTitle",
			"Movies" }, description = "Verify for Atlest any two Title which are substring on other Title")
	public void checkforTitleIsSubStrOfOtherTitle() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 200);
		ArrayList<String> title = response.path("results.title");
		int count = 0;
		for (int i = 0; i <= title.size() - 1; i++) {
			for (int j = i + 1; j <= title.size() - 1; j++) {
				String temp = title.get(i);
				if (!temp.equals(title.get(j))) {
					if (title.get(j).contains(temp) || temp.contains(title.get(j))) {
						Reporter.log(title.get(j) + " present in : " + temp);
						count++;
					}

					if (count == 2) {
						break;
					}
				}
			}
			if (count == 2) {
				break;
			}
		}
		Assert.assertTrue(count >= 2, "Couldn't find Atlest any two Title which are substring on other Title");
	}


	@Test(groups = { "checkWithOutQueryparam",
			"Movies" }, description = "Verify check With Out Query param and test should fail ")
	public void checkWithOutQueryparam() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 404);

	}
	
	@Test(groups = { "checkWithOutQueryparam",
			"Movies" }, description = "Verify check With wrong quey Key  and test should fail ")
	public void checkWithWrongQueryKey() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 404, "abc", "batman");

	}
	
	

	@Test(groups = { "checkWithOutQueryparam",
			"Movies" }, description = "Verify check With wrong quey Value  and test should fail ")
	public void checkWithWrongQueryValue() {
		response = rf.checkSuccessFulResposneQueryPram(getMovies, 404, "q", "abrikadabra");

	}
	

	@Test(groups = { "checkForLimitNoOFRecord",
			"Movies" }, description = "Verify check limit no of Record by 1")
	public void checkForLimitNoOFRecord() {
		Map<String,String> qparam = new HashMap<>();
		qparam.put("q", "batman");
		qparam.put("Count", "1");
		response = rf.checkSuccessFulResposneQueryPrams(qparam, getMovies, 200);
		ArrayList<String> title = response.path("results.title");
		Assert.assertTrue(title.size()==1, "Response count is more than 1");
	}


}
