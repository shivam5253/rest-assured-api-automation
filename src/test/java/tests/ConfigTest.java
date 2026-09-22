package tests;

import base.UpdatedBaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ConfigTest extends UpdatedBaseTest {

    @Test
    public void getUser() {

        given()
                .spec(requestSpec)
                .when()
                .get("/users/5")
                .then()
                .statusCode(200);
    }
}
