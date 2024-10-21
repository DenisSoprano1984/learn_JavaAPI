package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;



@Epic("Edit cases/ака изменение тестов")
@Feature("Edit/редактирование")

public class UserEditTest extends BaseTestCase {

    String cookie;
    String header;
    String url = "https://playground.learnqa.ru/api_dev";


    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Tag("Какой-то тэг для удаления")
    @Tag("Какой-то ТЕГ для отчета")
    @Description("this test for edit new user")
    @DisplayName("изменение только что созданного пользователя")
    public void testEditJustCreatedTest() {
        //Generate user
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url+ "/user/")
                .jsonPath();

String userId = responseCreateAuth.getString("id");


//login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post(url + "/user/login")
                .andReturn();


        //edit
        String newName = "ChangedName";

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .body(editData)
                .put(url + "/user/" + userId)
                .andReturn();


        //get user data
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get(url + "/user/" + userId)
                .andReturn();

        //System.out.println(responseUserData.asString());

        Assertions.asserJsonByName(responseUserData, "firstName", newName);


    }






    @Test
    @Tag("Какой-то=тэг=для=удаления")
    @Tag("Какой-то_ТЕГ_для_отчета+2")
    @Tag("42")
    @Tag("Какой-то ТЕГ для отчета 3")
    @Description("this test for try to change name but unauthorized user")
    @DisplayName("изменение от имени от неавторизованного пользователя")
    public void testEditButNotAuthorised() {

        //Generate user for edit firstname
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/user/", userData );


        int userId = responseCreateAuth.getInt("id");
        String originalName = userData.get("firstName"); // Записали, чтоб проверить неизменность имени

       // System.out.println("Оригинальное имя тут: " + originalName);
        //System.out.println("Номер созданного пользователя : " + userId);

        //edit
        String newName = "ChangedName";

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .putNewUserData(url + "/user/" + userId, "header", "cookie", editData);

        //System.out.println("Ответ после попытке изменения " + responseEditUser.asString());
        Assertions.assertJsonHasField(responseEditUser, "error");
        Assertions.asserJsonByName(responseEditUser, "error", "Auth token not supplied");

        //login for check
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(url + "/user/login", authData);

        //get user data for check
        Response responseUserData = apiCoreRequests
                .getRequestUserInfo(url + "/user/" ,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"),
                        userId);

            //System.out.println(responseUserData.asString());

            Assertions.asserJsonByName(responseUserData, "firstName", originalName);

    }

    @Test
    @Tag("Какой-то=тэг=для=удаления")
    @Tag("Какой-то_ТЕГ_для_отчета+2")
    @Tag("42")
    @Tag("Какой-то ТЕГ для отчета 3")
    @Description("this test for try to change name but authorized Another user")
    @DisplayName("изменение от имени от Другого пользователя")
    public void testEditButAuthorisedAnotherUser() {

        //Generate user for edit firstname
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/user/", userData );

        System.out.println("какой-то ответ : " + responseCreateAuth.getString("$"));

        int userId = responseCreateAuth.getInt("id");
        String originalName = userData.get("firstName"); // Записали, чтоб проверить неизменность имени

         System.out.println("Оригинальное имя тут: " + originalName);
         System.out.println("Номер созданного пользователя : " + userId);

        Map<String, String> authDataAnotherAcc = new HashMap<>();
        authDataAnotherAcc.put("email", "vinkotov@example.com");
        authDataAnotherAcc.put("password", "1234");

        Response authDataAnotherAccFor = apiCoreRequests
                .getAuthRequest(url +"/user/login", authDataAnotherAcc);

        System.out.println(authDataAnotherAccFor.asString());

        //edit
        String newName = "ChangedName";

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .putNewUserData(
                        url + "/user/" + userId,
                        this.getHeader(authDataAnotherAccFor, "x-csrf-token"),
                        this.getCookie(authDataAnotherAccFor, "auth_sid"),
                        editData);

        System.out.println("Ответ после попытке изменения " + responseEditUser.asString());
        Assertions.assertJsonHasField(responseEditUser, "error");
        Assertions.asserJsonByName(responseEditUser,
                "error",
                "Please, do not edit test users with ID 1, 2, 3, 4 or 5.");

        //login for check
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(url + "/user/login", authData);

        //get user data for check
        Response responseUserData = apiCoreRequests
                .getRequestUserInfo(url + "/user/" ,
                        this.getHeader(responseGetAuth,"x-csrf-token"),
                        this.getCookie(responseGetAuth,"auth_sid"),
                        userId);

        System.out.println(responseUserData.asString());


        Assertions.asserJsonByName(responseUserData, "firstName", originalName);

    }






























    @Test
    @Description("this test for try to change email without @")
    @DisplayName("изменение c не валидным @")
    public void testEditUserWithBadAt() {

        //Generate user for edit email
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/user/", userData );

        //System.out.println("какой-то ответ : " + responseCreateAuth.getString("$"));

        int userId = responseCreateAuth.getInt("id");
        String originalEmail = userData.get("email"); // Записали, чтоб проверить неизменность

        System.out.println("Оригинальное имя тут: " + originalEmail);
        System.out.println("Номер созданного пользователя : " + userId);

        // Авторизация под пользователем кого только что создали
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(url + "/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");



        //edit
        String newEmail = "Changedmailgmail.com";

        Map<String, String> editData = new HashMap<>();
        editData.put("email", newEmail );

        Response responseEditUser = apiCoreRequests
                .putNewUserData(
                        url + "/user/" + userId,
                        this.header,
                        this.cookie,
                        editData);

        System.out.println("Ответ после попытке изменения " + responseEditUser.asString());
        Assertions.assertJsonHasField(responseEditUser, "error");
        Assertions.asserJsonByName(responseEditUser,
                "error",
                "Invalid email format");


        //get user data for check
        Response responseUserData = apiCoreRequests
                .getRequestUserInfo(url + "/user/" ,
                        this.header,
                        this.cookie,
                        userId);

        System.out.println(responseUserData.asString());


        Assertions.asserJsonByName(responseUserData, "email", originalEmail);

    }








    @Test
    @Description("this test for try to change  without too short name")
    @DisplayName("изменение c не валидной длиной")
    public void testEditUserWithShort() {

        //Generate user for edit email
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/user/", userData );

        //System.out.println("какой-то ответ : " + responseCreateAuth.getString("$"));

        int userId = responseCreateAuth.getInt("id");
        String originalName = userData.get("firstName"); // Записали, чтоб проверить неизменность

        System.out.println("Оригинальное имя тут: " + originalName);
        System.out.println("Номер созданного пользователя : " + userId);

        // Авторизация под пользователем кого только что создали
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest(url + "/user/login", authData);

        this.cookie = this.getCookie(responseGetAuth,"auth_sid");
        this.header = this.getHeader(responseGetAuth,"x-csrf-token");



        //edit
        String newName = "f";

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName );

        Response responseEditUser = apiCoreRequests
                .putNewUserData(
                        url + "/user/" + userId,
                        this.header,
                        this.cookie,
                        editData);

        System.out.println("Ответ после попытке изменения " + responseEditUser.asString());
        Assertions.assertJsonHasField(responseEditUser, "error");
        Assertions.asserJsonByName(responseEditUser,
                "error",
                "The value for field `firstName` is too short");


        //get user data for check
        Response responseUserData = apiCoreRequests
                .getRequestUserInfo(url + "/user/" ,
                        this.header,
                        this.cookie,
                        userId);

        System.out.println(responseUserData.asString());


        Assertions.asserJsonByName(responseUserData, "firstName", originalName);

    }



}
