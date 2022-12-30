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
import org.testng.annotations.Test;

import com.Base.base;
import com.Utils.Utils;

import io.restassured.module.jsv.JsonSchemaValidator;

public class FiestTestCases extends base{
		
	public int averge_time_response;
	public String cartId;
	public String token;
	
	public Utils ut;
	
	@BeforeMethod
	@BeforeClass
	public void setUp() {
		
		ut = new Utils();
		baseURI = prop.getProperty("uri");
		
		cartId = given()
		.when()
		.get("/carts/createGuestCart")
		.then()
		.extract()
		.path("cartId");
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
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
	@Test
	public void verify_OTP_with_valid_credentials_APITEST() {
		JSONObject request = new JSONObject();
		
		request.put("contact", "9599882655");
		request.put("otp", 1234);
		request.put("guestCartId", cartId);
		request.put("isGupshup", true);
		
		token = given()
				.contentType("application/json")
				.body(request.toJSONString())
				.when()
				.post("/auth/otp")
				.then()
				.statusCode(200)
				.assertThat()
				.body(JsonSchemaValidator.
						matchesJsonSchema(new File("/Users/honasa/Desktop/SeleniumSeesion/APIAutomation/src/main/java/com/Parameterize/LoginUserSchema.json")))
				.extract()
				.path("token.accessToken");
		
		
		Assert.assertNotNull(token);
	}
	
	@Test
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
