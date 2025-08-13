package steps;

import io.cucumber.java.pt.Dado;
import io.restassured.RestAssured;

public class CommonSetupSteps {

    @Dado("que configuro a base da Dog API")
    public void configurarBase() {
        RestAssured.baseURI = "https://dog.ceo/api";
    }
}

