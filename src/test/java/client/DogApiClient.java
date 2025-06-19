package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class DogApiClient {

    public Response getAllBreeds() {
        return get("/breeds/list/all");
    }

    public Response postToAllBreeds() {
        return post("/breeds/list/all");
    }

    public Response getImagesByBreed(String breed) {
        return given()
                .pathParam("breed", breed)
                .get("/breed/{breed}/images");
    }

    public Response getRandomImage() {
        return get("/breeds/image/random");
    }

    public Response getMultipleRandomImages(int count) {
        return given()
                .pathParam("count", count)
                .get("/breeds/image/random/{count}");
    }

    public Response getRandomImageByBreed(String breed) {
        return given()
                .pathParam("breed", breed)
                .get("/breed/{breed}/images/random");
    }

    public Response postToRandomImageByBreed(String breed) {
        return given()
                .pathParam("breed", breed)
                .post("/breed/{breed}/images/random");
    }

    public Response getMultipleImagesByBreed(String breed, int count) {
        return given()
                .pathParam("breed", breed)
                .pathParam("count", count)
                .get("/breed/{breed}/images/random/{count}");
    }

    public Response postToMultipleImagesByBreed(String breed, int count) {
        return given()
                .pathParam("breed", breed)
                .pathParam("count", count)
                .post("/breed/{breed}/images/random/{count}");
    }

    public Response getSubBreeds(String breed) {
        return given()
                .pathParam("breed", breed)
                .get("/breed/{breed}/list");
    }

    public Response postToSubBreedList(String breed) {
        return given()
                .pathParam("breed", breed)
                .post("/breed/{breed}/list");
    }

    public Response getImagesBySubBreed(String breed, String subBreed) {
        return given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .get("/breed/{breed}/{subBreed}/images");
    }

    public Response postToSubBreedImages(String breed, String subBreed) {
        return given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .post("/breed/{breed}/{subBreed}/images");
    }

    public Response getRandomImageBySubBreed(String breed, String subBreed) {
        return given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .get("/breed/{breed}/{subBreed}/images/random");
    }

    public Response postToRandomImageBySubBreed(String breed, String subBreed) {
        return given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .post("/breed/{breed}/{subBreed}/images/random");
    }

    public Response getMultipleImagesBySubBreed(String breed, String subBreed, int count) {
        return given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .pathParam("count", count)
                .get("/breed/{breed}/{subBreed}/images/random/{count}");
    }

    public Response postToMultipleImagesBySubBreed(String breed, String subBreed, int count) {
        return given()
                .pathParam("breed", breed)
                .pathParam("subBreed", subBreed)
                .pathParam("count", count)
                .post("/breed/{breed}/{subBreed}/images/random/{count}");
    }
}
