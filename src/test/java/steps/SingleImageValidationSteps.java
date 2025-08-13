package steps;

import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

public class SingleImageValidationSteps {

    private final ScenarioContext context;

    public SingleImageValidationSteps(ScenarioContext context) { this.context = context; }

    @Entao("a URL da imagem deve ser valida")
    public void urlValida() {
        String url = context.getImageUrl();
        Assert.assertNotNull(url, "URL não presente");
        Assert.assertTrue(url.startsWith("https://"), "URL deve começar com https://");
        Assert.assertTrue(url.endsWith(".jpg"), "URL deve terminar com .jpg");
    }

    @Entao("a URL da imagem deve conter a raça atual")
    public void urlContemRaca() {
        String breed = context.getBreed();
        Assert.assertNotNull(breed, "Raça não definida");
        Assert.assertTrue(context.getImageUrl().toLowerCase().contains(breed.toLowerCase()), "URL não contém raça");
    }

    @Entao("a URL da imagem deve conter a sub-raça atual")
    public void urlContemSubraca() {
        String sub = context.getSubBreed();
        Assert.assertNotNull(sub, "Sub-raça não definida");
        Assert.assertTrue(context.getImageUrl().toLowerCase().contains(sub.toLowerCase()), "URL não contém sub-raça");
    }
}

