package tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest {

    @Test
    public void getUser() {
        Response response =
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/5");

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());
    }
    @Test
    public void validateUserId() {

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/5")
                .then()
                .statusCode(200)
                .body("id", equalTo(5))
                .body("username",equalTo("Kamren"))
                .body("name",equalTo("Chelsey Dietrich"))
                .body("address.city", equalTo("Roscoeview"));
    }
    @Test
    public void getUserUsingQueryParam() {
        given().queryParam("id",5).
                when().get("https://jsonplaceholder.typicode.com/users").
                then().statusCode(200);
    }
    @Test
    public void getUserUsingPathParam() {
        given().pathParams("id",5)
                .when().get("https://jsonplaceholder.typicode.com/users/{id}")
                .then().statusCode(200);
    }
    @Test
    public void getUserWithHeader() {
        given().
                header("Accept","application/json").
                when().
                get("https://jsonplaceholder.typicode.com/users/5").
                then().statusCode(200);
    }

    //post
    @Test
    public void createUser() {

        given()
                //we have to create content type here before body
                // We're telling the server that the request body is JSON.
                .contentType("application/json")
                .body("""

                   {
                       "name": "Rahul",
                       "username": "rahul123",
                       "email": "rahul@example.com"
                   }
                    """)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201).
                body("name",equalTo("Rahul")).
                        body("username",equalTo("rahul123"))
                                .body("email",equalTo("rahul@example.com"));

        //down side body is for validation

    }

    @Test
    public void createUser1() {

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
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Shivam"))
                .body("username", equalTo("shivam123"))
                .body("email", equalTo("shivam@example.com"));
    }

    // if we want id for extractionResponse extraction means retrieving a specific value from
    // an API response and storing it in a Java variable so it can be used later.
    @Test
    public void createUserAndExtractId() {

        Response response =
                given()
                        .contentType("application/json")
                        .body("""
                            {
                                "name": "Rahul",
                                "username": "rahul123",
                                "email": "rahul@example.com"
                            }
                            """)
                        .when()
                        .post("https://jsonplaceholder.typicode.com/users");

        response.then().statusCode(201);

        int userId = response.jsonPath().getInt("id");

        System.out.println("Created User ID: " + userId);
    }


    //PUT Request
    //PUT is generally used to replace/update an existing resource.
    @Test
    public void updateUserUsingPut() {

        given()
                .contentType("application/json")
                .body("""
                    {
                        "name": "Shivam Updated",
                        "username": "shivam_updated",
                        "email": "updated@example.com"
                    }
                    """)
                .when()
                .put("https://jsonplaceholder.typicode.com/users/5")
                .then()
                .statusCode(200)
                .body("name", equalTo("Shivam Updated"))
                .body("username", equalTo("shivam_updated"))
                .body("email", equalTo("updated@example.com"));
    }



    //Patch
    //PATCH is generally used for a partial update.
    @Test
    public void updateUserUsingPatch() {

        given()
                .contentType("application/json")
                .body("""
                    {
                        "email": "patch@example.com"
                    }
                    """)
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/5")
                .then()
                .statusCode(200)
                .body("email", equalTo("patch@example.com"));
    }
    //delete
    @Test
    public void userDelete() {

        given().when()
                .delete("https://jsonplaceholder.typicode.com/users/5")
                .then()
                .statusCode(200);
    }

    //more validating
    public void getValidate(){
        given().when().get("https://jsonplaceholder.typicode.com/users/5").
                then().statusCode(200).
                header("Content-Type", containsString("application/json"));
    }
    public void getValidate2(){
        given().when().get("https://jsonplaceholder.typicode.com/users/5").
                then().statusCode(200).
                time(lessThan(3000L), TimeUnit.MILLISECONDS);
    }

}
