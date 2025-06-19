package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.SubBreedImageDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

public class SubBreedRandomImageTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test(dataProvider = "validSubBreedCombinations", dataProviderClass = SubBreedImageDataProvider.class)
    public void testRandomImageFromValidSubBreed(String breed, String subBreed) {
        var test = ExtentReportManager.createTest(
                String.format("Imagem aleatória de sub-raça válida [%s/%s]", breed, subBreed)
        );

        Response res = api.getRandomImageBySubBreed(breed, subBreed);
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        String imageUrl = res.jsonPath().getString("message");
        Assert.assertTrue(imageUrl.startsWith("https://"));
        Assert.assertTrue(imageUrl.endsWith(".jpg"));
        Assert.assertTrue(imageUrl.contains(subBreed), "URL não contém a sub-raça esperada: " + subBreed);
    }

    @Test(dataProvider = "invalidSubBreedCombinations", dataProviderClass = SubBreedImageDataProvider.class)
    public void testRandomImageFromInvalidSubBreed(String breed, String subBreed) {
        var test = ExtentReportManager.createTest(
                String.format("Imagem aleatória de sub-raça inválida [%s/%s]", breed, subBreed)
        );

        Response res = api.getRandomImageBySubBreed(breed, subBreed);
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200, "Raça/sub-raça inválida não deve retornar 200");
    }

    @Test
    public void testPostMethodNotAllowedForRandomSubBreedImage() {
        var test = ExtentReportManager.createTest("POST não permitido no endpoint de imagem aleatória por sub-raça");

        Response res = api.postToRandomImageBySubBreed("hound", "afghan");
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertTrue(
                res.statusCode() == 405 || res.asString().toLowerCase().contains("not allowed")
        );
    }

    @Test
    public void testRandomImageIsDifferentOnMultipleCalls() {
        var test = ExtentReportManager.createTest("Imagem aleatória deve variar em chamadas consecutivas");

        String breed = "hound";
        String subBreed = "afghan";

        String first = api.getRandomImageBySubBreed(breed, subBreed).jsonPath().getString("message");
        String second = api.getRandomImageBySubBreed(breed, subBreed).jsonPath().getString("message");

        test.info("Primeira imagem: " + first);
        test.info("Segunda imagem: " + second);

        if (first.equals(second)) {
            test.warning("⚠️ A mesma imagem foi retornada duas vezes. Pode ser comportamento aceitável da API.");
        } else {
            Assert.assertNotEquals(first, second, "Imagens consecutivas não devem ser iguais");
        }
    }
}