package tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAuthTest {

    @Test
    public void userAuthTest () {

        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/ajax/api/user/login")
                .andReturn();

        Map<String, String> cookies = responseGetAuth.getCookies();
        Headers headers = responseGetAuth.getHeaders();
        int userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");





assertEquals(200, responseGetAuth.statusCode(), "Unexpected statuscode");
assertTrue(cookies.containsKey("auth_sid"), "Response doesn`t have 'auth_sid' cookie");
assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Response doesn`t have 'x-csrf-token' header");
        assertTrue(responseGetAuth.jsonPath().getInt("user_id")>0, "User id shoud be greater than 0");


        JsonPath responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", responseGetAuth.getHeader("x-csrf-token"))
                .cookie("auth_sid", responseGetAuth.getCookie("auth_sid"))
                .get("https://playground.learnqa.ru/ajax/api/user/auth")
                .jsonPath();

        int userIdOnChek = responseCheckAuth.getInt("user_id");
        assertTrue(userIdOnChek > 0 , "Unexpected user id " + userIdOnChek);
        assertEquals(
                userIdOnAuth,
                userIdOnChek,
                "user id oauth request is not equal to user_id from check request"
        );

//        System.out.println(userIdOnAuth);
//        System.out.println(userIdOnChek);
//        System.out.println(authData);
//        System.out.println(headers);
//        System.out.println(cookies);

    }
}
