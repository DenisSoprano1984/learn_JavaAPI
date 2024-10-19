package tests;

import io.qameta.allure.Description;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase {

    String url = "https://playground.learnqa.ru/api/user/";

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("this test for try to change name but authorized Another user")
    @DisplayName("изменение от имени от Другого пользователя")
    public void testEditButAuthorisedAnotherUser() {

        //Generate user for edit firstname
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests
                .responseCreateUserJson(url + "/api/user/", userData);

        System.out.println("какой-то ответ : " + responseCreateAuth.getString("$"));

        int userId = responseCreateAuth.getInt("id");
        String originalName = userData.get("firstName"); // Записали, чтоб проверить неизменность имени

        System.out.println("Оригинальное имя тут: " + originalName);
        System.out.println("Номер созданного пользователя : " + userId);

        Map<String, String> authDataAnotherAcc = new HashMap<>();
        authDataAnotherAcc.put("email", "vinkotov@example.com");
        authDataAnotherAcc.put("password", "1234");

        Response authDataAnotherAccFor = apiCoreRequests
                .getAuthRequest(url + "/api/user/login", authDataAnotherAcc);

        System.out.println(authDataAnotherAccFor.asString());

        //edit
        String newName = "ChangedName";

        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .putNewUserData(
                        url + "/api/user/" + userId,
                        this.getHeader(authDataAnotherAccFor, "x-csrf-token"),
                        this.getCookie(authDataAnotherAccFor, "auth_sid"),
                        editData);

        System.out.println("Ответ после попытке изменения " + responseEditUser.asString());
        Assertions.assertJsonHasField(responseEditUser, "error");
        Assertions.asserJsonByName(responseEditUser,
                "error",
                "Please, do not edit test users with ID 1, 2, 3, 4 or 5.");


    }
}