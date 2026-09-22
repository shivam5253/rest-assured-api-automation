
package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Phase5Test{

    @BeforeMethod
    public void setup() {
        System.out.println("Before Test");
    }

    @Test
    public void getUser() {
        System.out.println("Get User API");
    }

    @Test
    public void createUser() {
        System.out.println("Create User API");
    }

    @AfterMethod
    public void cleanup() {
        System.out.println("After Test");
    }


}
