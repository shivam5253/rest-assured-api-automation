package tests;

import base.UpdatedBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DataDrivenTest extends UpdatedBaseTest {

    @DataProvider(name = "userData")
    public Object[][] userData() {

        return new Object[][] {
                {"Rahul", "rahul123", "rahul@example.com"},
                {"Shivam", "shivam123", "shivam@example.com"},
                {"Aman", "aman123", "aman@example.com"}
        };
    }

    @Test(dataProvider = "userData")
    public void createUser(String name, String username, String email) {

        String payload = """
                {
                    "name": "%s",
                    "username": "%s",
                    "email": "%s"
                }
                """.formatted(name, username, email);

        given()
                .spec(requestSpec)
                .body(payload)
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }
}