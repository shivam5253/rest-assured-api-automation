package ecommerce;

import base.UpdatedBaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductTest extends UpdatedBaseTest {

    @Test
    public void getAllProducts() {

        Response response =
                given()
                        .when()
                        .get("https://dummyjson.com/products");

        response.then()
                .statusCode(200);

        int totalProducts =
                response.jsonPath().getInt("total");

        System.out.println("Total products: " + totalProducts);

        Assert.assertTrue(totalProducts > 0);
    }

    @Test
    public void getSingleProduct() {

        Response response =
                given()
                        .pathParam("id", 1)
                        .when()
                        .get("https://dummyjson.com/products/{id}");

        response.then()
                .statusCode(200);

        int productId =
                response.jsonPath().getInt("id");

        String title =
                response.jsonPath().getString("title");

        System.out.println("Product ID: " + productId);
        System.out.println("Product Title: " + title);

        Assert.assertEquals(productId, 1);
        Assert.assertNotNull(title);
    }
    @Test
    public void searchProducts() {

        Response response =
                given()
                        .queryParam("q", "phone")
                        .when()
                        .get("https://dummyjson.com/products/search");

        response.then()
                .statusCode(200);

        int total =
                response.jsonPath().getInt("total");

        System.out.println("Search result count: " + total);

        Assert.assertTrue(total > 0);
    }



    @Test
    public void createProduct() {

        String payload = """
        {
            "title": "Test Laptop",
            "price": 999
        }
        """;

        Response response =
                given()
                        .contentType("application/json")
                        .body(payload)
                        .when()
                        .post("https://dummyjson.com/products/add");

        response.then()
                .statusCode(201);

        int productId =
                response.jsonPath().getInt("id");

        String title =
                response.jsonPath().getString("title");

        System.out.println("Created Product ID: " + productId);
        System.out.println("Created Product Title: " + title);

        Assert.assertTrue(productId > 0);
        Assert.assertEquals(title, "Test Laptop");
    }
    @Test
    public void updateProduct() {

        String payload = """
        {
            "title": "Updated Laptop",
            "price": 1200
        }
        """;

        Response response =
                given()
                        .contentType("application/json")
                        .pathParam("id", 1)
                        .body(payload)
                        .when()
                        .put("https://dummyjson.com/products/{id}");

        response.then()
                .statusCode(200);

        String title =
                response.jsonPath().getString("title");

        int price =
                response.jsonPath().getInt("price");

        System.out.println("Updated Title: " + title);
        System.out.println("Updated Price: " + price);

        Assert.assertEquals(title, "Updated Laptop");
        Assert.assertEquals(price, 1200);
    }

    @Test
    public void patchProduct() {

        String payload = """
        {
            "price": 1500
        }
        """;

        Response response =
                given()
                        .contentType("application/json")
                        .pathParam("id", 1)
                        .body(payload)
                        .when()
                        .patch("https://dummyjson.com/products/{id}");

        response.then()
                .statusCode(200);

        int price =
                response.jsonPath().getInt("price");

        System.out.println("Patched Price: " + price);

        Assert.assertEquals(price, 1500);
    }

    @Test
    public void deleteProduct() {

        Response response =
                given()
                        .pathParam("id", 1)
                        .when()
                        .delete("https://dummyjson.com/products/{id}");

        response.then()
                .statusCode(200);

        int productId =
                response.jsonPath().getInt("id");

        System.out.println("Deleted Product ID: " + productId);

        Assert.assertEquals(productId, 1);
    }

    //Negative Testing 🔴 MUST KNOW
    @Test
    public void getInvalidProduct() {

        Response response =
                given()
                        .when()
                        .get("https://dummyjson.com/products/99999");

        response.then()
                .statusCode(404);

        System.out.println("Status Code: " + response.getStatusCode());
    }

    //negative
    @Test
    public void deleteInvalidProduct() {

        Response response =
                given()
                        .when()
                        .delete("https://dummyjson.com/products/99999");

        response.then()
                .statusCode(404);

        System.out.println(
                "Status Code: " + response.getStatusCode()
        );
    }




    //chaining
    @Test
    public void productRequestChaining() {

        // Step 1: Get all products
        Response productsResponse =
                given()
                        .when()
                        .get("https://dummyjson.com/products");

        productsResponse.then()
                .statusCode(200);

        // Step 2: Extract first product ID
        int productId =
                productsResponse.jsonPath()
                        .getInt("products[0].id");

        System.out.println("Extracted Product ID: " + productId);

        // Step 3: Use extracted ID in another request
        Response productResponse =
                given()
                        .pathParam("id", productId)
                        .when()
                        .get("https://dummyjson.com/products/{id}");

        // Step 4: Validate response
        productResponse.then()
                .statusCode(200);

        int returnedId =
                productResponse.jsonPath().getInt("id");

        String title =
                productResponse.jsonPath().getString("title");

        System.out.println("Returned Product ID: " + returnedId);
        System.out.println("Product Title: " + title);

        // Step 5: Verify that the IDs match
        Assert.assertEquals(returnedId, productId);
    }
}
