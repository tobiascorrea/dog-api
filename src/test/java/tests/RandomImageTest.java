package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.ImageCountDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RandomImageTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test
    public void testRandomDogImageReturnsValidUrl() {
        var test = ExtentReportManager.createTest("Imagem aleatória de qualquer raça");

        Response res = api.getRandomImage();

        test.info("Status HTTP: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200, "Status code deve ser 200");
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        String imageUrl = res.jsonPath().getString("message");
        test.info("URL retornada: " + imageUrl);

        Assert.assertTrue(imageUrl.startsWith("https://"), "URL da imagem deve começar com https://");
    }

    @Test
    public void testRandomDogImagePostMethodShouldFail() {
        var test = ExtentReportManager.createTest("POST não permitido para imagem aleatória");

        Response res = given().when().post("/breeds/image/random");

        test.info("Status HTTP: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200, "POST não deveria retornar 200");

        String body = res.getBody().asString().toLowerCase();
        Assert.assertTrue(
                body.contains("not allowed") || body.contains("error") || res.statusCode() == 405,
                "Resposta deveria indicar erro ou método não permitido"
        );
    }

    @Test
    public void testMultipleRandomImagesWithValidCounts() {
        var test = ExtentReportManager.createTest("Vários cães aleatórios com counts válidos");

        int[] validCounts = {1, 2, 10, 50};

        for (int count : validCounts) {
            Response res = api.getMultipleRandomImages(count);
            test.info("Count solicitado: " + count);
            test.info("Status HTTP: " + res.statusCode());

            Assert.assertEquals(res.statusCode(), 200);
            Assert.assertEquals(res.jsonPath().getString("status"), "success");

            List<String> images = res.jsonPath().getList("message");
            test.info("Total de imagens retornadas: " + images.size());

            Assert.assertEquals(images.size(), count, "Deveria retornar exatamente " + count + " imagens");

            for (String url : images) {
                Assert.assertTrue(url.startsWith("https://"), "URL deve começar com https://");
            }
        }
    }

    @Test
    public void testMultipleRandomImagesAboveLimit() {
        var test = ExtentReportManager.createTest("Quantidade acima do limite suportado");

        int invalidCount = 100;
        Response res = api.getMultipleRandomImages(invalidCount);
        List<String> images = res.jsonPath().getList("message");

        test.info("Count solicitado: " + invalidCount);
        test.info("Imagens retornadas: " + images.size());

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(images.size() <= 50, "A API não deve retornar mais de 50 imagens");
    }

    @Test(dataProvider = "invalidCounts", dataProviderClass = ImageCountDataProvider.class)
    public void testMultipleRandomImagesWithInvalidCounts(int count) {
        var test = ExtentReportManager.createTest("Count inválido para imagens aleatórias - " + count);

        Response res = api.getMultipleRandomImages(count);
        List<String> images = res.jsonPath().getList("message");

        test.info("Count enviado: " + count);
        test.info("Imagens retornadas: " + images.size());

        Assert.assertTrue(images.size() <= 50, "Mesmo com count inválido, não deve retornar mais de 50 imagens");
    }
}