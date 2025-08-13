package steps;

import io.cucumber.java.pt.Entao;
import org.testng.Assert;
import support.ScenarioContext;

import java.util.List;

public class ImageListValidationSteps {

    private final ScenarioContext context;

    public ImageListValidationSteps(ScenarioContext context) { this.context = context; }

    @Entao("a lista de imagens não deve estar vazia")
    public void listaNaoVazia() {
        List<String> imgs = getImages();
        Assert.assertNotNull(imgs, "Lista de imagens ausente");
        Assert.assertFalse(imgs.isEmpty(), "Lista de imagens não deve estar vazia");
    }

    @Entao("a quantidade de imagens deve ser exatamente {int}")
    public void quantidadeExata(int expected) {
        List<String> imgs = getImages();
        Assert.assertEquals(imgs.size(), expected, "Quantidade divergente");
    }

    @Entao("deve retornar no máximo {int} imagens")
    public void noMaximo(int max) {
        List<String> imgs = getImages();
        Assert.assertTrue(imgs.size() <= max, "Retornou mais que o máximo: " + imgs.size());
    }

    @Entao("a lista de imagens deve estar vazia ou ter até {int} imagens")
    public void vaziaOuLimite(int limite) {
        List<String> imgs = getImages();
        if (imgs != null) {
            Assert.assertTrue(imgs.isEmpty() || imgs.size() <= limite,
                    "Tamanho inesperado: " + imgs.size());
        }
    }

    @Entao("todas as URLs devem ser válidas")
    public void urlsValidas() {
        for (String url : getImages()) {
            Assert.assertTrue(url.startsWith("https://"), "URL deve começar com https://");
            Assert.assertTrue(url.endsWith(".jpg"), "URL deve terminar com .jpg");
        }
    }

    @Entao("todas as URLs devem conter a raça atual")
    public void urlsContemRaca() {
        String breed = context.getBreed();
        Assert.assertNotNull(breed, "Raça não definida");
        for (String url : getImages()) {
            Assert.assertTrue(url.toLowerCase().contains(breed.toLowerCase()), "URL deve conter a raça: " + breed);
        }
    }

    @Entao("todas as URLs devem conter a sub-raça atual")
    public void urlsContemSubraca() {
        String sub = context.getSubBreed();
        Assert.assertNotNull(sub, "Sub-raça não definida");
        for (String url : getImages()) {
            Assert.assertTrue(url.toLowerCase().contains(sub.toLowerCase()), "URL deve conter a sub-raça: " + sub);
        }
    }

    private List<String> getImages() {
        if (context.getImages() == null) {
            Object msg = context.getResponse().jsonPath().get("message");
            if (msg instanceof List<?>) {
                context.setImages(context.getResponse().jsonPath().getList("message"));
            }
        }
        return context.getImages();
    }
}
