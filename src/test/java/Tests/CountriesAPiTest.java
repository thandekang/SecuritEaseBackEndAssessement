package Tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;

import static Common.CommonTestData.Success_Status_Code;
import static Common.RequestBuilder.getListOfAllCountriesResponse;
import static org.hamcrest.Matchers.*;

@Test
@Feature("Countries API")
@Story("Get list of allcountries")
public class CountriesAPiTest {

    @Description("As an api user i want to get the list of all breeds")
    @Severity(SeverityLevel.CRITICAL)
    public void getListOfAllCountriesTest(){
        getListOfAllCountriesResponse().
                then().
                assertThat().
                statusCode(Success_Status_Code);
//                body(containsString("body")).

    }



}
