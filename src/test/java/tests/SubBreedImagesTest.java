package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.SubBreedImageDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.util.List;

public class SubBreedImagesTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test(dataProvider = "validSubBreedCombinations", dataProviderClass = SubBreedImageDataProvider.class)
    public void testImagesForValidSubBreeds(String breed, String subBreed) {
        var test = ExtentReportManager.createTest("Imagens por sub-raça válida: " + breed + "/" + subBreed);

        Response res = api.getImagesBySubBreed(breed, subBreed);
        test.info("Status: " + res.statusCode());
        test.info("Corpo:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        List<String> images = res.jsonPath().getList("message");
        Assert.assertFalse(images.isEmpty(), "Lista de imagens não pode estar vazia");

        for (String url : images) {
            Assert.assertTrue(url.startsWith("https://"), "URL deve começar com https://");
            Assert.assertTrue(url.endsWith(".jpg"), "URL deve terminar com .jpg");
            Assert.assertTrue(url.toLowerCase().contains(subBreed.toLowerCase()),
                    "URL deve conter a sub-raça: " + subBreed);
        }
    }

    @Test(dataProvider = "invalidSubBreedCombinations", dataProviderClass = SubBreedImageDataProvider.class)
    public void testInvalidSubBreedRequests(String breed, String subBreed) {
        var test = ExtentReportManager.createTest("Sub-raça inválida: " + breed + "/" + subBreed);

        Response res = api.getImagesBySubBreed(breed, subBreed);
        test.info("Status retornado: " + res.statusCode());
        test.info("Resposta: " + res.asString());

        Assert.assertNotEquals(res.statusCode(), 200);
    }

    @Test
    public void testInvalidMethodOnSubBreedImagesEndpoint() {
        var test = ExtentReportManager.createTest("POST não permitido para imagens de sub-raça");

        Response res = api.postToSubBreedImages("hound", "afghan");
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertTrue(res.statusCode() == 405 || res.asString().toLowerCase().contains("not allowed"));
    }

    @Test
    public void testSubBreedImageResponseTime() {
        var test = ExtentReportManager.createTest("Tempo de resposta: sub-raça hound/afghan");

        Response res = api.getImagesBySubBreed("hound", "afghan");
        long time = res.time();

        test.info("Status: " + res.statusCode());
        test.info("Tempo de resposta: " + time + "ms");

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(time < 3000, "Tempo de resposta deve ser inferior a 3 segundos");
    }

    @Test
    public void testSubBreedImageWithUppercaseShouldFail() {
        var test = ExtentReportManager.createTest("Sub-raça com letras maiúsculas");

        String breed = "HOUND";
        String subBreed = "AFGHAN";
        Response res = api.getImagesBySubBreed(breed, subBreed);

        test.info("Status: " + res.statusCode());
        test.info("Resposta:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 404, "Deveria retornar 404 para letras maiúsculas");
    }

    @Test
    public void testSubBreedImageWithSpecialCharacters() {
        var test = ExtentReportManager.createTest("Sub-raça com caracteres especiais");

        Response res = api.getImagesBySubBreed("hound", "afgh@an");

        test.info("Status: " + res.statusCode());
        test.info("Resposta:\n" + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200,
                "Sub-raça com caractere especial não deve ser aceita, mas retornou: " + res.statusCode());
    }
}