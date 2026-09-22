package data;

public class TestData {

    public static String createUserPayload() {

        return """
                {
                    "name": "Rahul",
                    "username": "rahul123",
                    "email": "rahul@example.com"
                }
                """;
    }
}