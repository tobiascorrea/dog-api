package steps;

import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import support.ScenarioContext;

import static io.restassured.RestAssured.given;

public class HttpRequestSteps {

    private final ScenarioContext context;

    public HttpRequestSteps(ScenarioContext context) { this.context = context; }

    @Quando("eu envio GET para {string}")
    public void envioGetPara(String endpoint) {
        Response res = given().when().get(resolve(endpoint));
        context.setResponse(res);
        parseImages(res);
    }

    @Quando("eu envio POST para {string}")
    public void envioPostPara(String endpoint) {
        Response res = given().when().post(resolve(endpoint));
        context.setResponse(res);
        parseImages(res);
    }

    private String resolve(String raw) {
        String r = raw;
        if (context.getBreed() != null) r = r.replace("{breed}", context.getBreed());
        if (context.getSubBreed() != null) r = r.replace("{subBreed}", context.getSubBreed());
        if (context.getRequestedCount() != null) r = r.replace("{count}", String.valueOf(context.getRequestedCount()));
        return r;
    }

    private void parseImages(Response res) {
        // Se a resposta não for 200, não tentar parsear como JSON (pode ser HTML/erro)
        if (res == null || res.getStatusCode() != 200) return;

        String body = res.getBody().asString();
        String contentType = res.getHeader("Content-Type");

        boolean looksLikeJson = false;
        if (contentType != null && contentType.toLowerCase().contains("application/json")) {
            looksLikeJson = true;
        } else if (body != null) {
            String t = body.trim();
            looksLikeJson = t.startsWith("{") || t.startsWith("[");
        }

        if (!looksLikeJson) {
            // Não tentar parsear JSON quando a resposta aparenta ser HTML/erro
            return;
        }

        try {
            Object msg = res.jsonPath().get("message");
            if (msg instanceof java.util.List<?>) {
                context.setImages(res.jsonPath().getList("message"));
            } else if (msg instanceof String) {
                context.setImageUrl((String) msg);
            }
        } catch (Exception e) {
            // Protege contra JsonPathException se o corpo não for JSON válido
            // Não setar imagens/imagem para que os asserts posteriores falhem de forma clara
        }
    }
}
