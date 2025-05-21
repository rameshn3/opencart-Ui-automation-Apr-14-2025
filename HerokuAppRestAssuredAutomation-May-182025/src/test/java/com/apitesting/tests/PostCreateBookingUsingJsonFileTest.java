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
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PostCreateBookingUsingJsonFileTest {
    private int bookingId;

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test(dataProvider = "getTestData")
    public void createBookingTest(Map<String, Object> bookingData) throws JsonProcessingException {

        if (bookingData == null || bookingData.isEmpty()) {
            throw new RuntimeException("Test data is null or empty");
        }

        // Extract the fields safely
        String firstname = (String) bookingData.getOrDefault("firstname", "");
        String lastname = (String) bookingData.getOrDefault("lastname", "");
        String additionalneeds = (String) bookingData.getOrDefault("additionalneeds", "");
        int totalprice = ((Number) bookingData.getOrDefault("totalprice", 0)).intValue();
        boolean depositpaid = (boolean) bookingData.getOrDefault("depositpaid", false);

        // Extract booking dates safely
        @SuppressWarnings("unchecked")
        Map<String, String> bookingDatesMap = (Map<String, String>) bookingData.get("bookingdates");

        String checkin = bookingDatesMap != null ? bookingDatesMap.getOrDefault("checkin", "") : "";
        String checkout = bookingDatesMap != null ? bookingDatesMap.getOrDefault("checkout", "") : "";

        // Validate required fields
        if (firstname.isEmpty() || lastname.isEmpty() || checkin.isEmpty() || checkout.isEmpty()) {
            throw new RuntimeException("Missing required fields in test data");
        }

        // Log extracted data
        System.out.printf("Creating booking - First Name: %s, Last Name: %s, Total Price: %d, Deposit Paid: %b%n", firstname, lastname, totalprice, depositpaid);
        System.out.printf("Check-in Date: %s, Check-out Date: %s%n", checkin, checkout);

        // Prepare the request body
        BookingDates bookingdates = new BookingDates(checkin, checkout);
        Booking bookingRequest = new Booking(firstname, lastname, additionalneeds, totalprice, depositpaid, bookingdates);

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
                .body("booking.firstname", Matchers.equalTo(firstname))
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

    @DataProvider
    public Object[] getTestData() {
        try {
            //reading the json file from resources folder
            String jsonData = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/data.json"), "UTF-8");
            JSONArray jsonArray = JsonPath.read(jsonData, "$");

            if (jsonArray.isEmpty()) {
                throw new RuntimeException("Test data JSON is empty.");
            }

            return jsonArray.toArray(new Object[0]);
        } catch (IOException e) {
            throw new RuntimeException("Error reading test data from JSON file", e);
        }
    }
}