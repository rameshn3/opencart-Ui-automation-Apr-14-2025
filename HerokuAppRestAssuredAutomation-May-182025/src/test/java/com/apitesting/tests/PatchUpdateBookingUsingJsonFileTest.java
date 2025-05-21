package com.apitesting.tests;
import com.apitesting.utils.JsonPathValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

public class PatchUpdateBookingUsingJsonFileTest{
    private int bookingId;
    String patchApiReqBody;
    String tokenApiReqBody;

    /*@BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }
*/
    @Test
    public void PatchUpdateBookingTest() throws JsonProcessingException {

        try{
            String postApiReqBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/singlebooking.json"), "UTF-8");
            tokenApiReqBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/tokendata.json"), "UTF-8");
            patchApiReqBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/resources/patchTestData.json"), "UTF-8");
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
            //3.Update the request by Id using PATCH call

            Response putresponse = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(patchApiReqBody)
                    .header("cookie","token="+token)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                    .patch("/{bookingId}",bookingId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("firstname",Matchers.equalTo("James"))
                    .body("lastname",Matchers.equalTo("Brown"))
                    .extract()
                    .response();

            System.out.println("patch Booking Response:");
            putresponse.prettyPrint();
        }catch(IOException fnex){
            fnex.printStackTrace();
        }
    }

}
