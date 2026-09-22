package tests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import models.User1;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Phase4Test {

    //serialization  java object to json
    @Test
    public void createUserUsingPOJO(){
        User user = new User("Rahul" , "rahul123","rahul@example.com");
        given().contentType("application/json").body(user).when()
                .post("https://jsonplaceholder.typicode.com/users").
                then().statusCode(201);
    }

    //deserialization json to java
    @Test
    public void deserializeUser() {

        Response response =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/users/5");

        User1 user = response.as(User1.class);

        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
    }
}
