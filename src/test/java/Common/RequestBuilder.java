package Common;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;


public class RequestBuilder {
    private static final String BASE_URL = "https://restcountries.com/v3.1";
    
    private static File cachedSchema = null;
    private static String cachedSchemaPath = null;

    public static Response getAllCountries() {
        RequestSpecification request = RestAssured.given();
        return request.get(BASE_URL + "/all");
    }
    
    // Helper method to validate schema with caching
    public static void validateSchema(Response response, String schemaPath) {
        // Only create new File if schema path changed or cache is empty
        if (cachedSchema == null || !schemaPath.equals(cachedSchemaPath)) {
            cachedSchema = new File(schemaPath);
            cachedSchemaPath = schemaPath;
        }
        
        response
            .then()
            .assertThat()
            .body(JsonSchemaValidator.matchesJsonSchema(cachedSchema));
    }
    
    /**
     * Gets information about South Africa specifically
     * @return Response containing South Africa's data
     */
    public static Response getSouthAfricaInfo() {
        RequestSpecification request = RestAssured.given();
        return request.get(BASE_URL + "/name/South Africa");
    }
    
    /**
     * Gets the total number of countries from the API
     * @return int representing the total number of countries
     */
    public static int getTotalCountriesCount() {
        Response response = getAllCountries();
        return response.jsonPath().getList("$").size();
    }
    
    /**
     * Gets the official languages of South Africa
     * @return Response containing language information
     */
    public static Object getSouthAfricanLanguages() {
        Response response = getSouthAfricaInfo();
        return response.jsonPath().get("[0].languages");
    }
}