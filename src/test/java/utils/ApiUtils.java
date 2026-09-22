package utils;

import io.restassured.response.Response;

public class ApiUtils {

    public static String getValue(Response response, String path) {
        return response.jsonPath().getString(path);
    }
}
