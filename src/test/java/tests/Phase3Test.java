package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Phase3Test {
    //positive test
    @Test
    public void validUser() {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/5")
                .then()
                .statusCode(200)
                .body("id", equalTo(5));
    }
    //negative test
    @Test
    public void invalidUser() {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/999")
                .then()
                .statusCode(404);
    }
    //negative test
    @Test
    public void invalidUserWithResponse() {

        Response response =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/users/999");

        System.out.println(response.getBody().asString());

        response.then().statusCode(404);
    }
    @Test
    public void loginTest() {

        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "username": "emilys",
                                "password": "emilyspass"
                            }
                            """)
                        .when()
                        .post("https://dummyjson.com/auth/login");

        System.out.println(response.getBody().asString());

        response.then().statusCode(200);
    }

    //The response contains the token, but we need to capture it into a Java variable.
    //
    //REST Assured makes this easy with jsonPath().

    @Test
    public void loginAndExtractToken() {

        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "username": "emilys",
                                "password": "emilyspass"
                            }
                            """)
                        .when()
                        .post("https://dummyjson.com/auth/login");

        response.then().statusCode(200);

        String accessToken =
                response.jsonPath().getString("accessToken");

        System.out.println("Access Token: " + accessToken);

    }


    // NOW USE THIS TOKEN AGAIN CREATE THE SAME AS ABOVE JUST MINOR ADDITION
    @Test
    public void loginAndAccessProtectedAPI() {

        // 1. Login
        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "username": "emilys",
                                "password": "emilyspass"
                            }
                            """)
                        .when()
                        .post("https://dummyjson.com/auth/login");

        response.then().statusCode(200);

        // 2. Extract token
        String accessToken =
                response.jsonPath().getString("accessToken");

        // 3. Use token
        given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("https://dummyjson.com/auth/me")
                .then()
                .statusCode(200);
    }



    //Testing Negative Authentication
    @Test
    public void loginAndAccessProtectedAPINegativr() {

        // 1. Login
        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "username": "emilys",
                                "password": "emilyspass"
                            }
                            """)
                        .when()
                        .post("https://dummyjson.com/auth/login");

        response.then().statusCode(200);

        // 2. Extract token
        String accessToken =
                response.jsonPath().getString("accessToken");

        // 3. Use no token
        given()
//                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("https://dummyjson.com/auth/me")
                .then()
                .statusCode(401);
    }

    //Testing Negative Authentication
    @Test
    public void loginAndAccessProtectedAPINegative() {

        // 1. Login
        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "username": "emilys",
                                "password": "emilyspass"
                            }
                            """)
                        .when()
                        .post("https://dummyjson.com/auth/login");

        response.then().statusCode(200);

        // 2. Extract token
        String accessToken =
                response.jsonPath().getString("accessToken");

        // 3. Use wrong token
        given()
                .header("Authorization", "Bearer invalid-token")
                .when()
                .get("https://dummyjson.com/auth/me")
                .then()
                .statusCode(401);
    }



    @Test
    public void jsonPathExercise1() {
        Response response = given().
                when().get("https://jsonplaceholder.typicode.com/users/5");


        int id = response.jsonPath().getInt("id");
        String username =
                response.jsonPath().getString("username");
        String name =
                response.jsonPath().getString("name");
        String email =
                response.jsonPath().getString("email");
        String address =
                response.jsonPath().getString("address.city");


        System.out.println("ID: " + id);
        System.out.println("Name: "+ name);
        System.out.println("Username: " + username);
        System.out.println("Email: "+ email);
        System.out.println("City: " + address);


    }


    @Test
    public void jsonPathExercise() {
        Response response = given().
                when().get("https://jsonplaceholder.typicode.com/users");


        int fid = response.jsonPath().getInt("[0].id");
        String thirdName =
                response.jsonPath().getString("[1].name");
        List<Integer> ids =
                response.jsonPath().getList("id");
        String name =
                response.jsonPath()
                        .getString("find { it.username == 'Kamren' }.name");



        System.out.println(fid);
        System.out.println(thirdName);
        System.out.println(ids);
        System.out.println(name);


    }



    @Test
    public void requestChaining() {

        // 1. Create user
        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                    "name": "Shivam",
                                    "username": "shivam123",
                                    "email": "shivam@example.com"
                                }
                                """)
                        .when()
                        .post("https://jsonplaceholder.typicode.com/users");

        // 2. Validate POST response
        response.then()
                .statusCode(201);

        // 3. Extract ID from response
        int userId =
                response.jsonPath().getInt("id");

        System.out.println("Created User ID: " + userId);

        // 4. Use extracted ID in next request
        given()
                .pathParam("id", userId)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{id}")
                .then()
                .statusCode(200);
    }

    //this is one for request specification we would define everything at top
    // so no repeating and we can test then 50 APis

    public class RequestSpecificationTest {

        RequestSpecification requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri("https://jsonplaceholder.typicode.com")
                        .setContentType("application/json")
                        .build();

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
}
