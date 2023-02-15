package com.TestCase;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import com.Base.base;

import io.restassured.response.Response;


public class Purchase_APITestCases extends base{
	
	
	String cartId;
	
	@BeforeMethod
	public void setup() {
		
		baseURI = prop.getProperty("Production_URI");
		
		cartId = given()
		.when()
		.get("/v1/carts/createGuestCart")
		.then()
		.extract()
		.path("cartId");
	}
	
	@SuppressWarnings("unchecked")
	@Test(groups = "Non-GI")
	public void AddSingleProduct() {
		JSONObject jsonBody = new JSONObject();
		JSONObject cartItems = new JSONObject();
		
		cartItems.put("quote_id", cartId);
		cartItems.put("sku", prop.getProperty("sku_id"));
		cartItems.put("qty", 1);
		cartItems.put("product_options", "[]");
		cartItems.put("product_type", "simple");
		
		jsonBody.put("cartId", cartId);
		jsonBody.put("cartItem", cartItems);
		System.out.println(cartId);
		System.out.println(jsonBody);
		
		Response response = (Response) given()
		.when()
		.contentType("application/json")
		.body(jsonBody.toJSONString())
		.post("/v1/carts/guestAdd/")
		.then()
		.statusCode(200)
		.extract()
		.response();
		
		System.out.println(response.asString());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void totalAPIAfterAddingSingleProduct() {
		
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("", "");
		
		given()
		.when()
		.contentType("application/json")
		.body("")
		.post("/v1/carts/user/"+cartId+"/totals")
		.then();
		
	}

}
