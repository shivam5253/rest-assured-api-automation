package tests;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
public class Phase5BTests {
    @Test
    public void testNGAssertions() {

        Response response =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/users/5");

        Assert.assertEquals(response.getStatusCode(), 200);

        int userId = response.jsonPath().getInt("id");

        Assert.assertEquals(userId, 5);

        String username = response.jsonPath().getString("username");

        Assert.assertEquals(username, "Kamren");
    }
}



