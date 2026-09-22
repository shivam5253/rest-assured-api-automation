package ecommerce;

import base.UpdatedBaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthUtils;

import static io.restassured.RestAssured.given;

public class AuthTest extends UpdatedBaseTest {

    // Positive Scenario: Login → Token → Protected API
    @Test
    public void loginAndGetProfileUtil() {

        // Step 1: Get access token using reusable AuthUtils
        String accessToken = AuthUtils.getAccessToken();

        System.out.println("Token received successfully");

        // Step 2: Use token to access protected API
        Response profileResponse =
                given()
                        .header("Authorization", "Bearer " + accessToken)
                        .when()
                        .get("https://dummyjson.com/auth/me");

        // Step 3: Validate status code
        profileResponse.then()
                .statusCode(200);

        // Step 4: Extract username
        String username =
                profileResponse.jsonPath().getString("username");

        System.out.println("Username: " + username);

        // Step 5: Validate username
        Assert.assertEquals(username, "emilys");
    }


    // Negative Scenario: Invalid Token → 401
    @Test
    public void invalidToken() {

        Response response =
                given()
                        .header(
                                "Authorization",
                                "Bearer invalid_token_123"
                        )
                        .when()
                        .get("https://dummyjson.com/auth/me");

        // Validate unauthorized response
        response.then()
                .statusCode(401);

        System.out.println(
                "Status Code: " + response.getStatusCode()
        );
    }
}
