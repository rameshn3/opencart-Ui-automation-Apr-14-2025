package com.apitesting.tests;

import com.apitesting.utils.JsonPathValidator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Map;
import java.util.HashMap;
public class PostCreateBookingTest{
    int bookingId;
    @BeforeMethod
    public void setup(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
    }


    @Test
    public void createBookingTest(){
        //prepare the request body
        Map<String, Object> bookingData = new HashMap<>();
        Map<String, String> bookingDates = new HashMap<>();
        bookingData.put("firstname","Ramesh");
        bookingData.put("lastname","Ch");
        bookingData.put("totalprice",1500);
        bookingData.put("depositpaid",true);
        bookingData.put("additionalneeds","Breakfast");
        bookingData.put("bookingdates",bookingDates);
        bookingDates.put("checkin","2025-05-20");
        bookingDates.put("checkout","2025-05-25");

        Response response= RestAssured.given()
                .contentType(ContentType.JSON)
                .body(bookingData)
                .when()
                .post("/booking")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .body("booking.firstname",Matchers.equalTo("Ramesh"))
                .extract()
                .response();
        System.out.println("Create Response is:");
        response.prettyPrint();
        //fetch the booking id
        bookingId =JsonPathValidator.read(response,"bookingid");
        System.out.println("create response booking id:"+bookingId);
    }

    @Test
    public void getBookingByIdTest(){
        Response response= RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("bookingId",bookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .extract()
                .response();
        System.out.println("Response body dataa");
        response.prettyPrint();
    }

}
