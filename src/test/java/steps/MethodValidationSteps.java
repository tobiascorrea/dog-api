package steps;

import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

public class MethodValidationSteps {

    private final ScenarioContext context;

    public MethodValidationSteps(ScenarioContext context) { this.context = context; }

    @Entao("a resposta deve indicar metodo nao permitido")
    public void metodoNaoPermitido() {
        int code = context.getResponse().statusCode();
        String body = context.getResponse().asString().toLowerCase();
        Assert.assertTrue(code == 405 || body.contains("not allowed") || body.contains("error"),
                "Esperado 405 ou mensagem de método não permitido. Status: " + code);
    }
}

