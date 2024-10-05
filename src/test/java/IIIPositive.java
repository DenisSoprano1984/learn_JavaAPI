import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.http.Headers;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.internal.assertion.CookieMatcher.getCookies;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IIIPositive {

    @Test
    public void positiveTest() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        responseGetAuth.prettyPrint();
        System.out.println(authData);

        Map<String, String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();
        int userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");

assertEquals(200, responseGetAuth.statusCode(), "Не известный статускод");
assertTrue(cookies.containsKey("auth_sid"), "Ответ не содержит нужного ключа");
assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Response doesn`t heve 'x-csrf-token' headers");
assertTrue(responseGetAuth.jsonPath().getInt("user_id")> 0, "user id shoud be greater than null");


        JsonPath responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", responseGetAuth.getHeader("x-csrf-token"))
                .cookie("auth_sid", responseGetAuth.getCookie("auth_sid"))
                .get("https://playground.learnqa.ru/api/user/auth")
                .jsonPath();


int userIdOnCheck = responseCheckAuth.getInt("user_id");
assertTrue(userIdOnCheck >0 , "unexpected user id " +  userIdOnCheck);

assertEquals(
        userIdOnAuth,
        userIdOnCheck,
        "user id  from auth request is not equal to user_id from check request"
);








    }

}
