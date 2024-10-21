package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
import lib.ApiCoreRequests;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Get information about users")
@Feature("Users information")

public class UserGetTestCase extends BaseTestCase {

    String url = "https://playground.learnqa.ru/api_dev";

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();


    @Test
    @Tag("Какой-то=тэг=для=удаления")
    @Tag("Какой-то_ТЕГ_для_отчета+2")
    @Tag("42")
    @Description("Проверки на поля которых не должно быть при запросе без авторизации")
    @DisplayName("Проверка на поля в ответе на не авторизованного пользователя")
    public void testGetUserDataNotAuth(){

        Response responseUserData = RestAssured
                .get(url + "/user/2")
                .andReturn();

        System.out.println(responseUserData.asString());
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }
    @Test
    @Tag("Какой-то=тэг=для=удаления")
    @Tag("Какой-то_ТЕГ_для_отчета+2")
    @Tag("42")
    @Tag("Какой-то ТЕГ для отчета 3")
    @Description("Проверки на наличие полей у авторизованного пользователя")
    @DisplayName("Проверка на поля в ответе на авторизованного пользователя")
    public void testGetUserDetailsAuthAsSameUser() {
        String email = "vinkotov@example.com";

        Map<String, String> authData = new HashMap<>();
        authData.put("email", email);
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(url + "/user/login")
                .andReturn();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserDate = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get(url + "/user/2")
                .andReturn();

        String[] expectedFields = {"username", "firstName" , "lastName","email" };
        Assertions.assertJsonHasFields(responseUserDate, expectedFields);
        System.out.println(responseUserDate.asString());

//        Assertions.assertJsonHasField(responseUserDate, "username");
//                Assertions.assertJsonHasField(responseUserDate, "firstName");
//        Assertions.assertJsonHasField(responseUserDate, "lastName");
//        Assertions.assertJsonHasField(responseUserDate, "email");
    }


    @Test
    @Tag("Какой-то=тэг=для=удаления")
    @Tag("Какой-то_ТЕГ_для_отчета+2")
    @Tag("42")
    @Tag("Ex16")
    @Description("Проверки на наличие полей у авторизованного пользователя")
    @DisplayName("Ex16: Запрос данных другого пользователя")
    public void testGetUserDetailsAuthAsAnotherUser() {
        String email = "vinkotov@example.com";

        Map<String, String> authData = new HashMap<>();
        authData.put("email", email);
        authData.put("password", "1234");

// Авторизация под пользователем для запроса данных
        Response responseGetAuth = apiCoreRequests
                .getAuthRequest(url +"/user/login", authData);

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        System.out.println(responseGetAuth.asString());

        // Запрос данных не авторизованного аккаунта
        Response responseUserDate = apiCoreRequests
                .getRequestUserInfo(url + "/user/", header, cookie, 1);

        // Сами проверки на отсутствие совпадений с полями которых быть не должно + Проверка на наличие поля которое должно быть
        Assertions.assertJsonHasField(responseUserDate, "username");
        Assertions.assertJsonHasNotField(responseUserDate, "firstName");
        Assertions.assertJsonHasNotField(responseUserDate, "lastName");
        Assertions.assertJsonHasNotField(responseUserDate, "email");
        Assertions.assertJsonHasNotField(responseUserDate, "id");


    }



}
