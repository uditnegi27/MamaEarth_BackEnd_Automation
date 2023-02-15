package com.TestCase;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.List;

import com.Base.base;

import io.restassured.response.Response;

public class UserAddress_APITestCases extends base{
	
	
	
	public String cartId;
	public String token;
	public String userId;
	public Response userDetailsResponse;
	public List<Object> addressList;
	
	
	
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public void setup() {
		
		baseURI = prop.getProperty("Production_URI");
		
		cartId = given()
				.when()
				.get("v1/carts/createGuestCart")
				.then()
				.extract()
				.path("cartId");
		
		JSONObject request = new JSONObject();
		
		request.put("contact", "9599882655");
		request.put("otp", 1234);
		request.put("guestCartId", cartId);
		request.put("isGupshup", true);
		
		userDetailsResponse = (Response) given()
				.contentType("application/json")
				.body(request.toJSONString())
				.when()
				.post("v1/auth/otp")
				.then()
				.extract()
				.body();
		
		token = userDetailsResponse.jsonPath().getJsonObject("token.accessToken");
		userId = userDetailsResponse.jsonPath().getString("user.id");
		System.out.println(token);
		System.out.println(userId);
	}
	
	@Test(priority = 1)
	public void getUserAddress() {
	
		Response userAddresses = (Response)given()
		.when()
		.headers("Authorization", "Bearer "+token)
		.get("/v2/users/address/" + userId);
		
		addressList = userAddresses.jsonPath().getList("addresses.addressId");
		
	}
	
 	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void Test_UserAddress_WhenFirstNameIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", "");
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid first name", "Error message is invalid");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 3)
	public void Test_UserAddress_WhenLastNameIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", "");
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid last name", "Error message is invalid");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 4)
	public void Test_UserAddress_WhenEmailIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", "");
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid email", "Error message is invalid");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 5)
	public void Test_UserAddress_WhenNumberIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", "");
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Not a valid phone number", "Error message is invalid");
		
	}
	

	@SuppressWarnings("unchecked")
	@Test(priority = 6)
	public void Test_UserAddress_WhenCityrIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", "");
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Only alphabets allowed", "Error message is invalid");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 7)
	public void Test_UserAddress_WhenAddressIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", "");
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid address", "Error message is invalid");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 8)
	public void Test_UserAddress_WhenStateIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", "");
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid state", "Error message is invalid");
		
	}
	

	@SuppressWarnings("unchecked")
	@Test(priority = 9)
	public void Test_UserAddress_WhenPincodeIsEmpty() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", "");
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		Assert.assertEquals(res.statusCode(), 400);
		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid 6 digit pincode", "Error message is invalid");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 10)
	public void Test_UserAddress_WhenAddressDetailsAlreadyExist() {
		
		JSONObject object = new JSONObject();
		object.put("firstName", prop.getProperty("firstName"));
		object.put("lastName", prop.getProperty("lastName"));
		object.put("email", prop.getProperty("email"));
		object.put("city", prop.getProperty("city"));
		object.put("address", prop.getProperty("address"));
		object.put("addressType", prop.getProperty("addressType"));
		object.put("state", prop.getProperty("state"));
		object.put("postCode", prop.getProperty("postCode"));
		object.put("mobile", prop.getProperty("mobile"));
		
		Response res = (Response)given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
		.when().post("v2/users/address/"+userId);
		
		System.out.println(res.asString());
//		Assert.assertEquals(res.statusCode(), 400);
//		Assert.assertEquals(res.jsonPath().getString("message"), "Validation Error", "Error message is invalid");
//		Assert.assertEquals(res.jsonPath().getString("errors[0].messages[0]"), "Please enter a valid 6 digit pincode", "Error message is invalid");
		
	}

	@SuppressWarnings("unchecked")
	@Test(priority = 10)
	public void Test_UserAddress_DeleteExistingAddress() {
		
		JSONObject object = new JSONObject();
		for(Object obj: addressList) {
			System.out.println(obj);
		}
		
		for(Object obj: addressList) {
			object.put("addressId", obj);
			given().header("Authorization", "Bearer "+token).contentType("application/json").body(object.toJSONString())
			.when().delete("v2/users/address/"+userId);
			object.remove("addressId");
		}
		
	}

	

}
