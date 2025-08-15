package steps;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.restassured.RestAssured;

public class CommonSetupSteps {

    @Before
    public void beforeScenario() {
        String base = System.getProperty("baseUrl", "https://dog.ceo/api");
        RestAssured.baseURI = base;
    }

    @Dado("que configuro a base da Dog API")
    public void configurarBase() {
        RestAssured.baseURI = "https://dog.ceo/api";
    }
}
