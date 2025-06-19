package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.BreedDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

public class BreedRandomImageTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test(dataProvider = "breeds", dataProviderClass = BreedDataProvider.class)
    public void testRandomImageByBreedIsAccessible(String breed) {
        var test = ExtentReportManager.createTest("Imagem aleatória acessível para a raça: " + breed);

        Response res = api.getRandomImageByBreed(breed);
        test.info("Status da resposta: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200, "Status code deve ser 200");
        Assert.assertEquals(res.jsonPath().getString("status"), "success", "Status no corpo deve ser 'success'");

        String imageUrl = res.jsonPath().getString("message");

        Assert.assertNotNull(imageUrl, "URL da imagem não deve ser nula");
        Assert.assertTrue(imageUrl.startsWith("https://"), "URL deve começar com https://");
        Assert.assertTrue(imageUrl.endsWith(".jpg"), "URL deve terminar com .jpg");
        Assert.assertTrue(imageUrl.toLowerCase().contains(breed.toLowerCase()),
                "URL deve conter o nome da raça: " + breed);
    }

    @Test
    public void testRandomImageByBreedWithInvalidMethod() {
        var test = ExtentReportManager.createTest("POST em /breed/{breed}/images/random deve falhar");

        String breed = "hound";
        Response res = api.postToRandomImageByBreed(breed);

        test.info("Status da resposta: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200, "POST não deveria retornar 200");
        Assert.assertTrue(
                res.statusCode() == 405 || res.asString().toLowerCase().contains("not allowed"),
                "POST deve retornar 405 ou conter 'not allowed' no corpo"
        );
    }
}