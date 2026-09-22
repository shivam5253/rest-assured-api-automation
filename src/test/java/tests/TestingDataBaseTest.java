package tests;

import base.UpdatedBaseTest;
import data.TestData;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestingDataBaseTest extends UpdatedBaseTest {
    @Test
    public void createUser() {

        given()
                .spec(requestSpec)
                .body(TestData.createUserPayload())
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }
}
