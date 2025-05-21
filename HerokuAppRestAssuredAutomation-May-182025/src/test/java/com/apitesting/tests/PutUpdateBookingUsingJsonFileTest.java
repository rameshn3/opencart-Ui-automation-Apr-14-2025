package com.apitesting.tests;
import com.apitesting.pojos.Booking;
import com.apitesting.pojos.BookingDates;
import com.apitesting.utils.JsonPathValidator;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class PutUpdateBookingUsingJsonFileTest{
    private int bookingId;
    String putApiReqBody;
    String tokenApiReqBody;

    /*@BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }
*/
    @Test
    public void createBookingTest() throws JsonProcessingException {

        try{
            String postApiReqBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/singlebooking.json"), "UTF-8");
            tokenApiReqBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/tokendata.json"), "UTF-8");
            putApiReqBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/updatebookingdata.json"), "UTF-8");
            //1. Send POST request
            // Send POST request
            Response response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(postApiReqBody)
                    .baseUri("https://restful-booker.herokuapp.com")
                    .when()
                    .post("/booking")
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .response();

            System.out.println("Create Booking Response:");
            response.prettyPrint();
            // Extract booking ID safely
            bookingId = response.jsonPath().getInt("bookingid");
            System.out.printf("Booking ID: %d%n", bookingId);



            //step2 : Token Generation
            Response tokenResp = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(tokenApiReqBody)
                    .baseUri("https://restful-booker.herokuapp.com/auth")
                    .when()
                    .post()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .response();
            String token =JsonPathValidator.read(tokenResp,"token");
            //3.Update the request by Id using PUT call

            Response putresponse = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(putApiReqBody)
                    .header("cookie","token="+token)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                    .put("/{bookingId}",bookingId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .response();

            System.out.println("put Booking Response:");
            putresponse.prettyPrint();
        }catch(IOException fnex){
            fnex.printStackTrace();
        }
    }
}
