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
        Object msg = res.jsonPath().get("message");
        if (msg instanceof java.util.List<?>) {
            context.setImages(res.jsonPath().getList("message"));
        } else if (msg instanceof String) {
            context.setImageUrl((String) msg);
        }
    }
}
