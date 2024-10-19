package tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.BaseTestCase;
import lib.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;


import java.util.HashMap;
import java.util.Map;

import lib.ApiCoreRequests;

@Epic("Authorisations cases")
@Feature("Auhtorisation")

public class UserAuthTest extends BaseTestCase {

    String url = "https://playground.learnqa.ru/api_dev";
    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @BeforeEach
public void loginUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(url + "/user/login", authData);
//                .given()
//                .body(authData)
//                .post("https://playground.learnqa.ru/api/user/login")
//                .andReturn();

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");


    }





    @Test
    @Description("this test successfully authorize user by email and password")
    @DisplayName("Test positive auth user")
    public void testAuthUser(){
//        Map<String, String> authData = new HashMap<>();
//        authData.put("email", "vinkotov@example.com");
//        authData.put("password", "1234");
//
//        Response responseGetAuth = RestAssured
//                .given()
//                .body(authData)
//                .post("https://playground.learnqa.ru/api/user/login")
//                .andReturn();
//
//        Map<String, String> cookies = responseGetAuth.getCookies();
//        Headers headers = responseGetAuth.getHeaders();
//        int userIdOnAuth = responseGetAuth.jsonPath().getInt("user_id");
//
//
//        assertEquals(200, responseGetAuth.statusCode(), "Unexpekted status code") ;
//        assertTrue(cookies.containsKey("auth_sid"), "Response doesn`t heve 'auth_sid' cookie");
//        assertTrue(headers.hasHeaderWithName("x-csrf-token"), "Response doesn`t have 'x-csrt-token' token");
//        assertTrue(responseGetAuth.jsonPath().getInt("user_id") > 0, "User id shoud be greater than 0");




        Response responseCheckAuth = apiCoreRequests
                .makeGetRequest(
                        url + "/user/auth",
                        this.header,
                        this.cookie
                );
//                .given()
//                .header("x-csrf-token", this.header)
//                .cookie("auth_sid", this.cookie)
//                .get("https://playground.learnqa.ru/api/user/auth")
//                .andReturn();

Assertions.asserJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth );

//        System.out.println(userIdOnAuth);
//        System.out.println(userIdOnChek);
//        System.out.println(authData);
//        System.out.println(headers);
//        System.out.println(cookies);

    }

    @Description("This test check authorization status wo sending auth cookie or token")
    @DisplayName("Test negative auth user")
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition){
//        Map<String, String> authData = new HashMap<>();
//        authData.put("email", "vinkotov@example.com");
//        authData.put("password", "1234");
//
//        Response responseGetAuth = RestAssured
//                .given()
//                .body(authData)
//                .post("https://playground.learnqa.ru/api/user/login")
//                .andReturn();
//
//        Map<String, String> cookies = responseGetAuth.getCookies();
//        Headers headers = responseGetAuth.getHeaders();

        RequestSpecification spec = RestAssured.given();
        spec.baseUri( url + "/user/auth");

        if (condition.equals("cookie")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestWhithCookie(
                    url + "/user/auth",
                    this.cookie
            );
            Assertions.asserJsonByName(responseForCheck, "user_id", 0);
        } else if (condition.equals("headers")) {
            Response responseForCheck = apiCoreRequests.makeGetRequestwithTokenOnly(
                    url + "/user/auth",
                    this.header
            );
            Assertions.asserJsonByName(responseForCheck, "user_id", 0);
        } else {

            throw new IllegalArgumentException("Condition value is known: " + condition);
        }


//        if (condition.equals("cookie")){
//            spec.cookie("auth_sid", this.cookie);
//        } else if (condition.equals("headers")){
//            spec.header("x-csrf-token", this.header);
//        } else {
//            throw new IllegalArgumentException("Condition value is known " + condition);
//        }

    Response responseForCheck = spec.get().andReturn();
       Assertions.asserJsonByName(responseForCheck, "user_id", 0);

    }

}
