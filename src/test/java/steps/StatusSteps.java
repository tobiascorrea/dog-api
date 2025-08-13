package steps;

import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

public class StatusSteps {
    private final ScenarioContext context;
    public StatusSteps(ScenarioContext context){ this.context = context; }

    @Entao("o status code deve ser {int}")
    public void validarStatusCode(int status){
        Assert.assertEquals(context.getResponse().statusCode(), status);
    }

    @Entao("a resposta não deve ser 200")
    public void respostaNao200(){
        Assert.assertNotEquals(context.getResponse().statusCode(), 200);
    }

    @Entao("o campo status deve ser {string}")
    public void validarCampoStatus(String esperado){
        Assert.assertEquals(context.getResponse().jsonPath().getString("status"), esperado);
    }
}

