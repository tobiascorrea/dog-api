package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.SubBreedImageCountDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.util.List;

public class SubBreedMultipleImagesTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test(dataProvider = "validSubBreedWithCounts", dataProviderClass = SubBreedImageCountDataProvider.class)
    public void testMultipleImagesValidSubBreed(String breed, String subBreed, int count) {
        var test = ExtentReportManager.createTest(
                String.format("Múltiplas imagens - Sub-raça válida [%s/%s] com count=%d", breed, subBreed, count)
        );

        Response res = api.getMultipleImagesBySubBreed(breed, subBreed, count);
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("status"), "success");

        List<String> images = res.jsonPath().getList("message");
        Assert.assertEquals(images.size(), count);

        for (String url : images) {
            Assert.assertTrue(url.startsWith("https://"));
            Assert.assertTrue(url.endsWith(".jpg"));
            Assert.assertTrue(url.contains(subBreed));
        }
    }

    @Test(dataProvider = "invalidSubBreedWithCounts", dataProviderClass = SubBreedImageCountDataProvider.class)
    public void testInvalidSubBreedCombinations(String breed, String subBreed, int count) {
        var test = ExtentReportManager.createTest(
                String.format("Sub-raça inválida [%s/%s] com count=%d", breed, subBreed, count)
        );

        Response res = api.getMultipleImagesBySubBreed(breed, subBreed, count);
        test.info("Status retornado: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200);
    }

    @Test(dataProvider = "extremeCounts", dataProviderClass = SubBreedImageCountDataProvider.class)
    public void testExtremeCountsBehavior(String breed, String subBreed, int count) {
        var test = ExtentReportManager.createTest(
                String.format("Extremos de count para [%s/%s]: count=%d", breed, subBreed, count)
        );

        Response res = api.getMultipleImagesBySubBreed(breed, subBreed, count);
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        List<String> images = res.jsonPath().getList("message");

        if (count == 0) {
            Assert.assertTrue(images.isEmpty() || images.size() == 1);
        } else if (count > 50) {
            int status = res.statusCode();
            List<Integer> expected = List.of(200, 400, 429);
            Assert.assertTrue(expected.contains(status), "Status inesperado para count alto: " + status);
        } else {
            Assert.assertEquals(images.size(), count);
        }
    }

    @Test
    public void testInvalidHttpMethodOnSubBreedMultipleImages() {
        var test = ExtentReportManager.createTest("POST não permitido para múltiplas imagens de sub-raça");

        Response res = api.postToMultipleImagesBySubBreed("hound", "afghan", 3);
        test.info("Status retornado: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200);
        Assert.assertTrue(
                res.statusCode() == 405 || res.asString().toLowerCase().contains("not allowed")
        );
    }
}