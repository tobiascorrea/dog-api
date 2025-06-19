package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.BreedDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.net.HttpURLConnection;
import java.net.URL;

public class RandomImageByBreedTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test(dataProvider = "breeds", dataProviderClass = BreedDataProvider.class)
    public void testRandomImageByBreedReturnsValidUrl(String breed) {
        var test = ExtentReportManager.createTest("Imagem aleatória por raça - " + breed);

        Response res = api.getRandomImageByBreed(breed);
        test.info("Status da resposta: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200, "Status code deve ser 200 para breed: " + breed);
        Assert.assertEquals(res.jsonPath().getString("status"), "success", "Status deve ser 'success'");

        String imageUrl = res.jsonPath().getString("message");

        test.info("URL da imagem retornada: " + imageUrl);

        Assert.assertTrue(imageUrl.startsWith("https://"), "URL deve começar com https://");
        Assert.assertTrue(imageUrl.matches("^https://.*\\.jpg$"), "A URL deve apontar para uma imagem .jpg");

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(imageUrl).openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.connect();
            int responseCode = conn.getResponseCode();

            test.info("Status HTTP da imagem: " + responseCode);

            Assert.assertTrue(
                    responseCode == 200 || responseCode == 302,
                    "Imagem deve estar acessível (200 ou 302)"
            );
        } catch (Exception e) {
            test.warning("Exceção ao validar URL: " + e.getMessage());
            Assert.fail("Erro ao acessar imagem da raça " + breed + ": " + e.getMessage());
        }
    }
}