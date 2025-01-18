package Tests;

import Common.RequestBuilder;
import Utils.ReadProperties;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class CountriesAPiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = ReadProperties.getBaseUrl();
    }


    @Test
    public void GetAllCountriesTests() {
        given()
                .when()
                .get(ReadProperties.getEndpoint())
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }


    @Test(priority = 1)
    public void SchemaValidationTests() {
        // Get the response from the API
        Response response = RequestBuilder.getAllCountries();

        // Verify response status code is 200
        Assert.assertEquals(200, response.getStatusCode());

        // Validate the response against our schema
        RequestBuilder.validateSchema(response, "src/test/resources/schemas/countries_schema.json");
    }


    @Test(priority = 2)
    public void VerifyCountryDetailsTests() {
        Response response = given()
                .when()
                .get(ReadProperties.getEndpoint())
                .then()
                .statusCode(200)
                .extract().response();

        // Verify that each country has required fields
        response.then()
                .body("name.common", everyItem(notNullValue()))
                .body("capital", everyItem(anyOf(nullValue(), isA(java.util.List.class))))
                .body("region", everyItem(notNullValue()));
    }

    @Test(priority = 3)
    public void ResponseTimeTests() {
        given()
                .when()
                .get(ReadProperties.getEndpoint())
                .then()
                .statusCode(200)
                .time(lessThan(5000L)); // Response time should be less than 5 seconds
    }

    @Test(priority = 4, description = "Verify total number of countries is 195")
    public void verifyTotalCountriesCountTests() {
        int expectedCountries = 195;
        int actualCountries = RequestBuilder.getTotalCountriesCount();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualCountries, expectedCountries,
                "Expected " + expectedCountries + " countries but found " + actualCountries);

        // Print test report
        System.out.println("\n=== Countries Count Test Report ===");
        System.out.println("Expected number of countries: " + expectedCountries);
        System.out.println("Actual number of countries: " + actualCountries);
        System.out.println("Test Status: " + (actualCountries == expectedCountries ? "PASSED" : "FAILED"));

        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Verify South African Sign Language (SASL) is included in official languages")
    public void verifySouthAfricanLanguagesTests() {
        Object languages = RequestBuilder.getSouthAfricanLanguages();
        String languagesStr = languages.toString().toLowerCase();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(languagesStr.contains("sasl") || languagesStr.contains("sign language"),
                "South African Sign Language (SASL) is not listed in official languages");

        // Print test report
        System.out.println("\n=== South African Languages Test Report ===");
        System.out.println("Retrieved languages: " + languages);
        System.out.println("SASL Status: " + (languagesStr.contains("sasl") ? "INCLUDED" : "NOT INCLUDED"));
        System.out.println("Test Status: " + (languagesStr.contains("sasl") ? "PASSED" : "FAILED"));

        softAssert.assertAll();
    }

}













