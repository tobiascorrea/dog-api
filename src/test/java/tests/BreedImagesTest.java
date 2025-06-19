package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.BreedImageAvailabilityDataProvider;
import dataprovider.ImageCountDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;

public class BreedImagesTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test
    public void testImagesHaveValidUrl() {
        ExtentTest log = ExtentReportManager.getTest();
        Response res = api.getImagesByBreed("pug");
        List<String> images = res.jsonPath().getList("message");

        log.info("Validando imagens da raça 'pug'");
        Assert.assertFalse(images.isEmpty(), "A lista de imagens não deve estar vazia");

        images.forEach(url -> validateUrl(url, "pug", log));
    }

    @Test(dataProvider = "validBreedAndCountCombinations", dataProviderClass = ImageCountDataProvider.class)
    public void testMultipleImagesByBreed(String breed, int count) {
        ExtentTest log = ExtentReportManager.getTest();
        log.info("Buscando " + count + " imagens da raça " + breed);

        Response res = api.getMultipleImagesByBreed(breed, count);

        Assert.assertEquals(res.statusCode(), 200, "Status HTTP deve ser 200");
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        List<String> images = res.jsonPath().getList("message");
        log.info("Total retornado: " + images.size());
        Assert.assertTrue(images.size() <= count);
        Assert.assertFalse(images.isEmpty());

        images.forEach(url -> validateUrl(url, breed, log));
    }

    @Test(dataProvider = "breedAndLargeCount", dataProviderClass = BreedImageAvailabilityDataProvider.class)
    public void testImageCountNeverExceedsAvailableImages(String breed, int requestedCount) {
        ExtentTest log = ExtentReportManager.getTest();
        log.info("Solicitando " + requestedCount + " imagens da raça " + breed);

        Response res = api.getMultipleImagesByBreed(breed, requestedCount);

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        List<String> images = res.jsonPath().getList("message");
        log.info("Recebidas " + images.size() + " imagens");

        Assert.assertTrue(images.size() <= requestedCount);
        images.forEach(url -> validateUrl(url, breed, log));
    }

    @Test
    public void testInvalidBreedShouldReturnError() {
        ExtentTest log = ExtentReportManager.getTest();
        String invalidBreed = "dragon";

        log.info("Testando raça inválida: " + invalidBreed);
        Response res = api.getImagesByBreed(invalidBreed);

        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertTrue(
                res.statusCode() == 404 || res.asString().toLowerCase().contains("error"),
                "Deve retornar erro ou 404 para raça inválida"
        );
        log.info("Resposta: " + res.asPrettyString());
    }

    @Test
    public void testPostMethodShouldFailOnBreedImagesEndpoint() {
        ExtentTest log = ExtentReportManager.getTest();
        String breed = "pug";
        log.info("Executando POST inválido para a raça " + breed);

        Response res = api.postToMultipleImagesByBreed(breed, 3);

        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertTrue(
                res.statusCode() == 405 || res.asString().toLowerCase().contains("not allowed"),
                "POST deve ser rejeitado adequadamente"
        );
        log.info("Resposta: " + res.asPrettyString());
    }

    @Test(dataProvider = "breedAndLargeCount", dataProviderClass = BreedImageAvailabilityDataProvider.class)
    public void testInvalidImageCountsForValidBreed(String breed, int requestedCount) {
        ExtentTest log = ExtentReportManager.getTest();
        log.info("Testando grande volume de imagens para raça válida: " + breed);

        Response res = api.getMultipleImagesByBreed(breed, requestedCount);

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        List<String> images = res.jsonPath().getList("message");
        Assert.assertTrue(images.size() <= requestedCount);
        images.forEach(url -> validateUrl(url, breed, log));
    }

    private void validateUrl(String url, String breed, ExtentTest log) {
        log.info("Validando URL: " + url);
        Assert.assertTrue(url.startsWith("https://"), "URL deve começar com https://");
        Assert.assertTrue(url.endsWith(".jpg"), "URL deve terminar com .jpg");
        Assert.assertTrue(url.toLowerCase().contains(breed.toLowerCase()),
                "URL deve conter o nome da raça: " + breed);
    }
}
