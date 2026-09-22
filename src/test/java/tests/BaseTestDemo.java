package tests;

import base.BaseTest;
import base.UpdatedBaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BaseTestDemo extends UpdatedBaseTest {

    @Test
    public void getUser() {

        given()
                .spec(requestSpec)
                .when()
                .get("/users/5")
                .then()
                .statusCode(200);
    }

    @Test
    public void getAnotherUser() {

        given()
                .spec(requestSpec)
                .when()
                .get("/users/10")
                .then()
                .statusCode(200);
    }
}
