package Common;

import groovy.xml.StreamingDOMBuilder;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Common.BasePaths.*;
import static Common.CommonTestData.Bad_Request_Status_Code;
import static Common.ContentTypes.json_contentType;
import static Common.PayloadBuilder.*;
import static io.restassured.RestAssured.given;

public class RequestBuilder {
    public static String stationID;
    public static String UserID;

    public static Response getListOfAllCountriesResponse() {
        return given().
                when().
//                contentType(json_contentType).
                log().all().
                get(BaseURL + "/v3.1/all/").
                then().
                log().all().
                extract().response();
    }

}