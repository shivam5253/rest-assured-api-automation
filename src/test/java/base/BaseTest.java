package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    protected RequestSpecification requestSpec;

    public BaseTest() {

        requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri("https://jsonplaceholder.typicode.com")
                        .setContentType("application/json")
                        .build();
    }
}
