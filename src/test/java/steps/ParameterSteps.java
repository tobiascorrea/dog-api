package steps;

import io.cucumber.java.pt.Dado;
import support.ScenarioContext;

public class ParameterSteps {

    private final ScenarioContext context;

    public ParameterSteps(ScenarioContext context) { this.context = context; }

    @Dado("a raça {string}")
    public void definirRaca(String breed) { context.setBreed(breed); }

    @Dado("a sub-raça {string}")
    public void definirSubRaca(String sub) { context.setSubBreed(sub); }

    @Dado("o count {int}")
    public void definirCount(Integer count) { context.setRequestedCount(count); }
}

