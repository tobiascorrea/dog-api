package tests;

import client.DogApiClient;
import config.BaseTest;
import dataprovider.SubBreedDataProvider;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.util.List;

public class SubBreedListTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test(dataProvider = "breedsForSubBreedValidation", dataProviderClass = SubBreedDataProvider.class)
    public void testListSubBreedsForVariousBreeds(String breed) {
        var test = ExtentReportManager.createTest("Listar sub-raças para: " + breed);

        Response res = api.getSubBreeds(breed);
        test.info("Status: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200, "Status HTTP deve ser 200");
        Assert.assertEquals(res.jsonPath().getString("status"), "success", "Status deve ser 'success'");

        List<String> subBreeds = res.jsonPath().getList("message");

        if (breed.equals("pug") || breed.equals("beagle") || breed.equals("labrador")) {
            Assert.assertTrue(subBreeds.isEmpty(), "Sub-raças devem estar vazias para: " + breed);
        } else {
            Assert.assertFalse(subBreeds.isEmpty(), "Sub-raças devem existir para: " + breed);
        }
    }

    @Test
    public void testListSubBreedsForInvalidBreed() {
        var test = ExtentReportManager.createTest("Sub-raças para raça inválida");

        String breed = "dragondog";
        Response res = api.getSubBreeds(breed);

        test.info("Status retornado: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200, "Não deve retornar 200 para raça inválida");
        Assert.assertTrue(
                res.statusCode() == 404 || res.asString().toLowerCase().contains("error"),
                "Deveria retornar erro ou 404 para breed inválida"
        );
    }

    @Test
    public void testInvalidMethodPostOnSubBreedList() {
        var test = ExtentReportManager.createTest("POST na listagem de sub-raças deve falhar");

        String breed = "hound";
        Response res = api.postToSubBreedList(breed);

        test.info("Status retornado: " + res.statusCode());
        test.info("Resposta: " + res.asPrettyString());

        Assert.assertNotEquals(res.statusCode(), 200, "POST não deve ser aceito");
        Assert.assertTrue(
                res.statusCode() == 405 || res.asString().toLowerCase().contains("not allowed"),
                "POST deve ser rejeitado com erro apropriado"
        );
    }
}