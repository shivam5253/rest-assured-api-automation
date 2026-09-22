package tests;

import base.UpdatedBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ApiUtils;

import static io.restassured.RestAssured.given;

public class UtilityTest extends UpdatedBaseTest {

    @Test
    public void getUserDetails() {

        Response response =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/users/5");

        String username =
                ApiUtils.getValue(response, "username");

        System.out.println("Username: " + username);
    }
}
