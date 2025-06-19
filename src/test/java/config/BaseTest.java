package config;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(listener.ExtentReportListener.class)
public class BaseTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://dog.ceo/api";
    }
}
