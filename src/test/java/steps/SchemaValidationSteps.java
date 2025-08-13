package steps;

import io.cucumber.java.pt.Entao;
import io.restassured.module.jsv.JsonSchemaValidator;
import support.ScenarioContext;

public class SchemaValidationSteps {

    private final ScenarioContext context;

    public SchemaValidationSteps(ScenarioContext context) { this.context = context; }

    @Entao("o schema deve ser valido para {string}")
    public void validarSchema(String schemaPath) {
        context.getResponse()
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
}

