package base;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class UpdatedBaseTest1 {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setup() {

        requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri(ConfigReader.get("baseUrl"))
                        .setContentType("application/json")
                        .build();
    }
}
