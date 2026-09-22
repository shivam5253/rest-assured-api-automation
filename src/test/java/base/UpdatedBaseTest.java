
package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class UpdatedBaseTest  {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {

        requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri("https://jsonplaceholder.typicode.com")
                        .setContentType("application/json")
                        .build();
    }
}
