package steps;

import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

public class PerformanceSteps {

    private final ScenarioContext context;

    public PerformanceSteps(ScenarioContext context) { this.context = context; }

    @Entao("o tempo de resposta deve ser menor que {int} ms")
    public void tempoRespostaMenor(int limiteMs) {
        long time = context.getResponse().time();
        Assert.assertTrue(time < limiteMs, "Tempo de resposta excedido: " + time + "ms (limite=" + limiteMs + ")");
    }
}

