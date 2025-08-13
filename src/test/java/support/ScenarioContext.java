package support;

import io.restassured.response.Response;
import java.util.List;

public class ScenarioContext {
    private Response response;
    private List<String> images;
    private String imageUrl;
    private long responseTime;
    private String breed;
    private String subBreed;
    private Integer requestedCount;

    public Response getResponse() { return response; }
    public void setResponse(Response response) { this.response = response; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public long getResponseTime() { return responseTime; }
    public void setResponseTime(long responseTime) { this.responseTime = responseTime; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getSubBreed() { return subBreed; }
    public void setSubBreed(String subBreed) { this.subBreed = subBreed; }

    public Integer getRequestedCount() { return requestedCount; }
    public void setRequestedCount(Integer requestedCount) { this.requestedCount = requestedCount; }
}
