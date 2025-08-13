package steps;

import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

import java.util.List;

public class SubBreedListSteps {

    private final ScenarioContext context;

    public SubBreedListSteps(ScenarioContext context) { this.context = context; }

    @Entao("a lista de sub-raças deve conter itens")
    public void listaSubracasComItens() {
        Object msg = context.getResponse().jsonPath().get("message");
        Assert.assertTrue(msg instanceof List<?>, "Campo 'message' não é lista");
        Assert.assertFalse(((List<?>) msg).isEmpty(), "Esperado ao menos uma sub-raça");
    }

    @Entao("a lista de sub-raças deve estar vazia")
    public void listaSubracasVazia() {
        Object msg = context.getResponse().jsonPath().get("message");
        Assert.assertTrue(msg instanceof List<?>, "Campo 'message' não é lista");
        Assert.assertTrue(((List<?>) msg).isEmpty(), "Esperado lista vazia");
    }
}

