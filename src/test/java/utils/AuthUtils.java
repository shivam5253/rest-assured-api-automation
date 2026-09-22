package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthUtils {

    //use this in access token test
    public static String getAccessToken() {

        String payload = """
            {
                "username": "emilys",
                "password": "emilyspass"
            }
            """;

        Response response =
                given()
                        .contentType("application/json")
                        .body(payload)
                        .when()
                        .post("https://dummyjson.com/auth/login");

        response.then()
                .statusCode(200);

        return response.jsonPath().getString("accessToken");
    }
}
