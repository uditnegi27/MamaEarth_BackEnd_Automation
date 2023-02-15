package com.TestCase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.Base.base;
import com.Utils.Utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class FiestTestCases extends base{
		
	public int averge_time_response;
	public String cartId;
	public String token;
	public String userId;
	public Response userDetailsResponse;
	public Response categories;

	
	public Utils ut;
	
	@BeforeMethod
	@BeforeClass
	public void setUp() {
		
		ut = new Utils();
		baseURI = prop.getProperty("Production_URI");
		
		cartId = given()
		.when()
		.get("/carts/createGuestCart")
		.then()
		.extract()
		.path("cartId");
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void generate_OTP_APITEST() {
		JSONObject request = new JSONObject();
		
		request.put("contact", "9599882655");
		request.put("isGupshup", true);
		
		given()
		.contentType("application/json")
		.body(request.toJSONString())
		.when()
		.post("/auth/otp/generate")
		.then()
		.statusCode(200)
		.body("sessionId", equalTo(true));
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void verify_OTP_with_valid_credentials_APITEST() {
		JSONObject request = new JSONObject();
		
		request.put("contact", "9599882655");
		request.put("otp", 1234);
		request.put("guestCartId", cartId);
		request.put("isGupshup", true);
		
		userDetailsResponse = (Response) given()
				.contentType("application/json")
				.body(request.toJSONString())
				.when()
				.post("/auth/otp")
				.then().log().all()
				.statusCode(200)
				.assertThat()
				.body(JsonSchemaValidator.
						matchesJsonSchema(new File("/Users/honasa/Desktop/SeleniumSeesion/APIAutomation/src/main/java/com/Parameterize/LoginUserSchema.json")))
				.extract()
				.body();
		
		token = userDetailsResponse.jsonPath().getJsonObject("token.accessToken");
		userId = userDetailsResponse.jsonPath().getString("user.id");
		Assert.assertNotNull(token);
		System.out.println(userDetailsResponse);
	}
	
	@Test(priority = 2)
	public void verify_cateogories() {
		
		categories = (Response) given()
				.when()
				.get("/categories")
				.then()
				.extract()
				.response();
		
		int categoriesCount = categories.jsonPath().getInt("product_count");
		
		for(int i = 0; i < categoriesCount; i++) {
			if(categories.jsonPath().getInt("children_data["+i+"].id") == 32) {
				System.out.println(categories.jsonPath().getString("children_data["+i+"].name"));
				
			}
		}
		
	}
	
	
//	@SuppressWarnings("unchecked")
//	@Test(priority = 3)
//	public void addProduct_to_cart() {
//		JSONObject object = new JSONObject();
//		object.put("firstName", prop.getProperty("firstName"));
//		object.put("lastName", prop.getProperty("lastName"));
//		object.put("email", prop.getProperty("email"));
//		object.put("city", prop.getProperty("city"));
//		object.put("address", prop.getProperty("address"));
//		object.put("addressType", prop.getProperty("addressType"));
//		object.put("state", prop.getProperty("state"));
//		object.put("postCode", prop.getProperty("postCode"));
//		object.put("mobile", prop.getProperty("mobile"));
//		
//		System.out.println(userId);
//		
//		int ss = given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
//		.when().post("/users/address/"+userId)
//		.then().log().all()
//		.extract()
//		.statusCode();
//		System.out.println(ss);
//		
//	}
	
	@Test(priority = 4)
	public void firstTest() {
		
		given()
		.when()
		.get("/content/contentfulBanners")
		.then()
		.statusCode(200);
	}
	
	
	
	@Test
 	public void verify_the_categories_APITEST() {
		given()
		.when()
		.get("/categories")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void dataBase() throws SQLException {
		ResultSet result = ut.get_data_from_dataBase("honasa-mamaearth-magento-dev", "SELECT * FROM `honasa-mamaearth-magento-dev`.customer_address_entity WHERE entity_id = 1103;");
		while(result.next()) {
			String first_name = result.getString("firstname");
			String last_name = result.getString("lastname");
			System.out.println("result");
			System.out.println(first_name);
			System.out.println(last_name);
			System.out.println("result");
		}
	}
	
	@Test
	public void Check_entityId_of_the_user() throws SQLException {
		ResultSet result = ut.get_data_from_dataBase("honasa-mamaearth-magento-dev", "SELECT * FROM `honasa-mamaearth-magento-dev`.customer_address_entity WHERE entity_id = 60;");
		while(result.next()) {
			String first_name = result.getString("firstname");
			String last_name = result.getString("lastname");
			System.out.println("result");
			System.out.println(first_name);
			System.out.println(last_name);
			System.out.println("result");
		}
	}
	
}
