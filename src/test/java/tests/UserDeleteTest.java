package tests;

import io.qameta.allure.*;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


@Epic("Delete cases 'EPIC'")
@Feature("Delete 'Feature' ")
public class UserDeleteTest extends BaseTestCase {


    //String url = "https://playground.learnqa.ru/api_dev";
    String url = "https://playground.learnqa.ru/api_dev";
    //int vinUserId = 2;

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Issue(value = "API-27 'Issue'")
    @Link(name = "learnqa.ru 'Link -тут'", url = "https://playground.learnqa.ru/")
    @Owner(value = "Denis Soprano 2 'Owner'")
    //@Tags(value = "SomeTags")
    @Tag("Какой-то_тэг_для_удаления")
    @Tag(value = "Какой-то_ТЕГ_для+отчета")

    @Test

    @Severity(value = SeverityLevel.BLOCKER)
    @Description("this test for try to delete user with 2 id 'Description тут'")
    @DisplayName("Удаление второго акка 'DisplayName'")
    public void testTryToDeleteUserTwo() {

        //Авторизация под 2 акком

        Map<String, String> authDataTwoAcc = new HashMap<>();
        authDataTwoAcc.put("email", "vinkotov@example.com");
        authDataTwoAcc.put("password", "1234");

        Response authDataAnotherAccFor = apiCoreRequests
                .getAuthRequest(url + "/user/login", authDataTwoAcc);

        System.out.println(authDataAnotherAccFor.asString());
        String userIdString = String.valueOf(getIntFromJson(authDataAnotherAccFor,"user_id"));
        int userId = Integer.parseInt(userIdString);


        System.out.println("Айди из боди ответа авторизации " + userId);

        //Попытаемся удалить аккаунта 2

        Response responseDeleteUser = apiCoreRequests
                .deleteUser(
                        url,
                        this.getHeader(authDataAnotherAccFor, "x-csrf-token"),
                        this.getCookie(authDataAnotherAccFor, "auth_sid"),
                        userId);


        System.out.println("Ответ после попытки удаления " + responseDeleteUser.asString());
        Assertions.assertJsonHasField(responseDeleteUser, "error");
        Assertions.asserJsonByName(responseDeleteUser,
                "error",
                "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

    }















    @Test
    @Description("this test for delete created user")
    @DisplayName("Успешное удаление пользователя")
    @Tag("Какой-то=тэг=для=удаления")
    @Tag("Какой-то_ТЕГ_для_отчета+2")
    @Tag("42")
    @Tag("Какой-то ТЕГ для отчета 3")
    @Severity(value = SeverityLevel.MINOR)

    @Owner("Denis Soprano")
    public void testDeleteSuccessfully() {

        //Generate user for delete
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/user/", userData );

//        System.out.println("какой-то ответ : " + responseCreateAuth.getString("$"));

        int userId = responseCreateAuth.getInt("id");
        String originalEmail = userData.get("usermane"); // Записали, чтоб проверить неизменность

//        System.out.println("Создали пользователя: " + originalEmail);
//        System.out.println("ID созданного пользователя : " + userId);




        // Авторизация под созданным пользователем
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(url + "/user/login", authData);

//        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
//        this.header = this.getHeader(responseGetAuth,"x-csrf-token");



        //Удаление пользователя

        Response responseDeleteUser = apiCoreRequests
                .deleteUser(url, this.getHeader(responseGetAuth,"x-csrf-token"), this.getCookie(responseGetAuth,"auth_sid"), userId  );

        System.out.println("Ответ после попытке изменения " + responseDeleteUser.asString());
        Assertions.assertJsonHasField(responseDeleteUser, "success");
        Assertions.asserJsonByName(responseDeleteUser,
                "success",
                "!");


        //Запрос инфы по пользователю которого удалили под последней авторизацией

        Response responseUserData = apiCoreRequests
                .getRequestUserInfo(url + "/user/" ,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"),
                        userId);

//        System.out.println(responseUserData.asString());

        Assert.assertEquals("Что-то пошло не так, какого-то пользователя нашли","User not found" , responseUserData.asString());
//Запрос данных под неавторизованным пользователем
        Response responseDataWOAuth = apiCoreRequests
        .getUserinfoWOauth(url, userId);

        Assert.assertEquals("Что-то пошло не так, какого-то пользователя нашли","User not found" , responseDataWOAuth.asString());
    }











    @Issue(value = "API-27 'Issue'")
    @Link(name = "learnqa.ru 'Link -тут'", url = "https://playground.learnqa.ru/")
    @Owner(value = "Denis Soprano3 'Owner'")
    //@Tags(value = "SomeTags")
    @Test
    @Tag("42")
    @Tag("42-1")
    @Severity(value = SeverityLevel.MINOR)
    @Description("this test for delete created user ansuccessfully")
    @DisplayName("попытка удаления пользователя под другим")
    public void testDeleteUnSuccessfully() {

        //Generate user for delete
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/user/", userData );

      //  System.out.println("какой-то ответ : " + responseCreateAuth.getString("$"));

        int userId= responseCreateAuth.getInt("id");
        String originalUsername = userData.get("username"); // Записали, чтоб проверить неизменность

      System.out.println("Создали пользователя: " + originalUsername);
        System.out.println("ID созданного пользователя : " + userId);

        // Авторизация под 2 аккаунтом

        Map<String, String> authDataTwoAcc2 = new HashMap<>();
        authDataTwoAcc2.put("email", "vinkotov@example.com");
        authDataTwoAcc2.put("password", "1234");

        Response authDataAnotherAccFor = apiCoreRequests
                .getAuthRequest(url + "/user/login", authDataTwoAcc2);



        //Удаление пользователя

        Response responseDeleteUser = apiCoreRequests
                .deleteUser(url,
                        this.getHeader(authDataAnotherAccFor, "x-csrf-token"),
                        this.getCookie(authDataAnotherAccFor,"auth_sid"),
                        userId  );

        System.out.println("Ответ после попытке изменения " + responseDeleteUser.asString());
        Assertions.assertJsonHasField(responseDeleteUser, "error");
        Assertions.asserJsonByName(responseDeleteUser,
                "error",
                "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
        System.out.println("Ответ после попытки удаления " + responseDeleteUser.asString());


        //Запрос данных под неавторизованным пользователем
        Response responseDataWOAuth = apiCoreRequests
                .getUserinfoWOauth(url, userId);


        Assertions.asserJsonByName(responseDataWOAuth,
                "username",
                originalUsername);

    }


}