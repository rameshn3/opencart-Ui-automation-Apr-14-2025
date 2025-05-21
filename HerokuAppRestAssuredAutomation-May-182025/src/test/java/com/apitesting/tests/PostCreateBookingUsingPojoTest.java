package com.apitesting.tests;
import com.apitesting.pojos.Booking;
import com.apitesting.pojos.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
public class PostCreateBookingUsingPojoTest{
    private int bookingId;

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void createBookingTest() throws JsonProcessingException {

        // Prepare the request body
        BookingDates bookingdates = new BookingDates("2025-05-15", "2025-05-30");
        Booking bookingRequest = new Booking("harika", "golla", "dinner", 15000, true, bookingdates);

        // Serialize the Java object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(bookingRequest);

        System.out.println("Request Body:\n" + requestBody);

        // Send POST request
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .assertThat()
                .statusCode(200)
                .body("booking.firstname", Matchers.equalTo("harika"))
                .extract()
                .response();

        System.out.println("Create Booking Response:");
        response.prettyPrint();

        // Extract booking ID safely
        try {
            bookingId = response.jsonPath().getInt("bookingid");
            System.out.printf("Booking ID: %d%n", bookingId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract booking ID from response", e);
        }
    }

    @Test(dependsOnMethods = "createBookingTest")
    public void getBookingByIdTest() {
        if (bookingId == 0) {
            throw new RuntimeException("Booking ID is not set. Ensure createBookingTest ran successfully.");
        }

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("bookingId", bookingId)
                .when()
                .get("/booking/{bookingId}")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("Get Booking Response:");
        response.prettyPrint();
    }

}