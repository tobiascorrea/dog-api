package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

import java.util.List;
import java.util.Map;

// Classe reimplementada para conter validações da lista de raças.

public class BreedListSteps {

    private final ScenarioContext context;

    public BreedListSteps(ScenarioContext context) {
        this.context = context;
    }

    @Entao("a resposta deve conter as raças:")
    public void respostaDeveConterRacas(DataTable tabela) {
        Map<String, Object> breeds = context.getResponse().jsonPath().getMap("message");
        for (String r : tabela.asList()) {
            Assert.assertTrue(breeds.containsKey(r.trim()), "Não encontrou raça: " + r);
        }
    }

    @Entao("pelo menos uma raça deve possuir sub-raças")
    public void algumaRacaComSubraca() {
        Map<String, Object> breeds = context.getResponse().jsonPath().getMap("message");
        boolean tem = breeds.values().stream().anyMatch(v -> v instanceof List<?> l && !l.isEmpty());
        Assert.assertTrue(tem, "Nenhuma raça com sub-raças");
    }
}
