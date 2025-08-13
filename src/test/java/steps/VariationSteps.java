package steps;

import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import support.ScenarioContext;

import static io.restassured.RestAssured.given;

public class VariationSteps {

    private final ScenarioContext context;

    public VariationSteps(ScenarioContext context) { this.context = context; }

    @Quando("eu realizo duas chamadas GET para {string} e comparo os resultados")
    public void duasChamadasGet(String endpoint) {
        String path = resolve(endpoint);
        Response first = given().when().get(path);
        Response second = given().when().get(path);
        context.setResponse(second); // mantém última para demais validações
        String f = first.jsonPath().getString("message");
        String s = second.jsonPath().getString("message");
        if (f != null && s != null && f.equals(s)) {
            System.out.println("[INFO] Mesma imagem retornada em chamadas consecutivas: " + f);
        }
    }

    private String resolve(String raw) {
        String r = raw;
        if (context.getBreed() != null) r = r.replace("{breed}", context.getBreed());
        if (context.getSubBreed() != null) r = r.replace("{subBreed}", context.getSubBreed());
        if (context.getRequestedCount() != null) r = r.replace("{count}", String.valueOf(context.getRequestedCount()));
        return r;
    }
}

