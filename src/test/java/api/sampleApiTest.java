package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class sampleApiTest {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void verifyAllUsers() {

        Response response =
                given()
                        .when()
                        .get("/users/1")
                        .then()
                        .extract()
                        .response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.jsonPath().getInt("id"), 1);
        Assert.assertEquals(response.jsonPath().getString("username"), "Bret");
        Assert.assertEquals(response.jsonPath().getString("email"), "Sincere@april.biz");
    }

    @Test
    public void createUserTest() {

        String requestBody = "{ \"name\": \"Deven\", \"username\": \"deven123\", \"email\": \"deven@test.com\" }";

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .extract()
                        .response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), "Deven");
        Assert.assertEquals(response.jsonPath().getString("username"), "deven123");
    }

    @Test
    public void updateUser() {
        String requestBody = "{ \"name\": \"Deven\", \"username\": \"deven123\", \"email\": \"deven@updated.com\" }";

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .put("/users/1")
                        .then()
                        .extract()
                        .response();

        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("email"), "deven@updated.com");
    }

}
