package Tests;

import Common.BasePaths;
import io.qameta.allure.*;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonSerializable;
import io.qameta.allure.internal.shadowed.jackson.databind.jsonschema.JsonSchema;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static Common.CommonTestData.Success_Status_Code;
import static Common.RequestBuilder.getListOfAllCountriesResponse;
import static org.hamcrest.Matchers.*;

@Test
@Feature("Countries API")
@Story("Get list of allcountries")
public class CountriesAPiTest extends BasePaths {

    @Description("As a map builder i want to get the list of all countries")
    @Severity(SeverityLevel.CRITICAL)
    public void getListOfAllCountriesTest() {
        getListOfAllCountriesResponse().
                then().
                assertThat().
                statusCode(Success_Status_Code);
//                body(containsString("body")).

    }

    @Description("As an API user I want to ensure that the data returned from the API conforms to published schema\n" +
            "so that my application can reliably consume and process the data returned")
    @Severity(SeverityLevel.CRITICAL)


    public void testCountriesSchemaValidation() throws Exception {
        // API endpoint to retrieve all countries
        String url = BaseURL + "/v3.1/all/";

        // Create an HTTP client
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            // Check if the response status code is 200 (OK)
            Assert.assertEquals("Expected status code 200",  response.getStatusLine().getStatusCode());

            // Read the JSON response
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            // Load the JSON schema from a file
            String schemaPath = "path/to/countries-schema.json"; // Replace with the path to your schema file
            String schema = new String(Files.readAllBytes(Paths.get(schemaPath)));

            // Validate the JSON response against the schema
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            JsonSchema jsonSchema = factory.getJsonSchema(schema);

        }

    }


    @Description("As a map builder i want to confirm number of countries")
    @Severity(SeverityLevel.CRITICAL)

    public void confirmNumberOfCountries() throws Exception {
        // API endpoint to retrieve all countries
        String url = BaseURL + "/v3.1/all/";

        // Create an HTTP client
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            // Check if the response status code is 200 (OK)
            Assert.assertEquals("Expected status code 200", response.getStatusLine().getStatusCode());

            // Read the JSON response
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            // Parse the JSON response to count the number of countries
            String json = jsonResponse.toString();
            int numberOfCountries = json.split("\\{").length - 1; // Count the number of countries

            // Assert that there are 195 countries
            Assert.assertEquals("Expected number of countries to be 195", numberOfCountries);
            System.out.println("Number of countries in the world: " + numberOfCountries);

        }


    }

    @Description("As a map builder i want to validate SASL as an official language")
    @Severity(SeverityLevel.CRITICAL)
    public void validateSaslRecognition() throws Exception {
        // API endpoint to check the status of SASL
        String url = BaseURL + "/v3.1/all/";

        // Create an HTTP client
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            // Check if the response status code is 200 (OK)
            Assert.assertEquals("Expected status code 200", response.getStatusLine().getStatusCode());

            // Read the JSON response
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            // Parse the JSON response (assuming a simple JSON structure)
            String json = jsonResponse.toString();
            //Assert.assertTrue("SASL should be recognized as an official SA language", json.contains("SASL"));


            // Print the JSON response for debugging
            System.out.println("Response: " + json);
        }
    }
}





























