package tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import client.DogApiClient;
import config.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExtentReportManager;

import java.util.List;
import java.util.Map;

public class BreedListTest extends BaseTest {

    DogApiClient api = new DogApiClient();

    @Test
    public void testAllBreedsEndpointValidations() {
        var test = ExtentReportManager.createTest("Valida endpoint de todas as raças");

        Response res = api.getAllBreeds();

        test.info("Status da resposta: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 200, "Status HTTP deve ser 200");
        Assert.assertEquals(res.jsonPath().getString("status"), "success", "Status deve ser 'success'");

        Map<String, Object> breeds = res.jsonPath().getMap("message");

        Assert.assertNotNull(breeds, "'message' não pode ser null");
        Assert.assertFalse(breeds.isEmpty(), "'message' não pode estar vazio");

        Assert.assertTrue(breeds.containsKey("bulldog"), "Deveria conter a raça 'bulldog'");
        Assert.assertTrue(breeds.containsKey("poodle"), "Deveria conter a raça 'poodle'");
        Assert.assertTrue(breeds.containsKey("retriever"), "Deveria conter a raça 'retriever'");

        boolean temSubraca = breeds.values().stream()
                .anyMatch(value -> value instanceof List && !((List<?>) value).isEmpty());

        Assert.assertTrue(temSubraca, "Pelo menos uma raça deveria conter sub-raças");
    }

    @Test
    public void testAllBreedsPostMethodShouldReturn405() {
        var test = ExtentReportManager.createTest("POST em /breeds/list/all deve retornar 405");

        Response res = api.postToAllBreeds();

        test.info("Status da resposta: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        Assert.assertEquals(res.statusCode(), 405, "POST deveria retornar 405 Method Not Allowed");

        String body = res.getBody().asString().toLowerCase();
        Assert.assertTrue(body.contains("not allowed") || body.contains("error"),
                "A resposta deveria indicar que o método não é permitido");
    }

    @Test
    public void testSchemaValidationForBreedList() {
        var test = ExtentReportManager.createTest("Validação de schema JSON para /breeds/list/all");

        Response res = api.getAllBreeds();

        test.info("Status da resposta: " + res.statusCode());
        test.info("Corpo da resposta:\n" + res.asPrettyString());

        res.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/breeds-schema.json"));
    }
}
