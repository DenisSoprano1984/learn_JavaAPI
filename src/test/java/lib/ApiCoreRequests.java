package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class ApiCoreRequests {

    @Step("make a get-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();



    }

    @Step("make a get-request with auth cookie only")
    public Response makeGetRequestWhithCookie(String url, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();



    }
    @Step("make a get-request with token and auth cookie")
    public Response makeGetRequestwithTokenOnly(String url, String token) {
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();



    }
    @Step("make a POST-request with token and auth cookie")
    public Response makePostRequest(String url, Map<String, String> authData) {
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("make a request for create user!!")
    public Response testCreateUserWithExistingEmail (String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

        @Step("make a request for create user!!")
        public Response responseCreateUser (String url, Map<String, String> userData) {
            return given()
                    .filter(new AllureRestAssured())
                    .body(userData)
                    .post(url)
                    .andReturn();
    }

    @Step("make a request for create user!! 'Step for Test positive create user'")
    public Response testCreateUserSuccessfully (String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Test negative create user With Short First Name")
    public Response testCreateUserWithShortFirstName (String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("to try create step w/o email field")
    public Response responseWOemail (String url) {
        Map<String, String> userDataWoEmail = new HashMap<>();

       // userDataWoEmail.put("email", DataGenerator.getrandomEmail());
        userDataWoEmail.put("password", "123");
        userDataWoEmail.put("username", "learnqa");
        userDataWoEmail.put("firstName", "learnqa");
        userDataWoEmail.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())
                .body(userDataWoEmail)
                .post(url)
                .andReturn();

    }
    @Step("to try create step w/o Password field")
    public Response responseWOPassword (String url) {
        Map<String, String> userDataWoEmail = new HashMap<>();

        userDataWoEmail.put("email", DataGenerator.getrandomEmail());

        userDataWoEmail.put("username", "learnqa");
        userDataWoEmail.put("firstName", "learnqa");
        userDataWoEmail.put("lastName", "learnqa");

        return given()
                .filter(new AllureRestAssured())
                .body(userDataWoEmail)
                .post(url)
                .andReturn();

    }
    @Step("to try create step w/o username field")
    public Response responseWOUsername (String url) {
        Map<String, String> userDataWoEmail = new HashMap<>();

        userDataWoEmail.put("email", DataGenerator.getrandomEmail());
        userDataWoEmail.put("password", "123");
      //  userDataWoEmail.put("username", "learnqa");
        userDataWoEmail.put("firstName", "learnqa");
        userDataWoEmail.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())
                .body(userDataWoEmail)
                .post(url)
                .andReturn();

    }

    @Step("to try create step w/o firstName field")
    public Response responseWOFirstName (String url) {
        Map<String, String> userDataWoEmail = new HashMap<>();

        userDataWoEmail.put("email", DataGenerator.getrandomEmail());
        userDataWoEmail.put("password", "123");
        userDataWoEmail.put("username", "learnqa");
        //userDataWoEmail.put("firstName", "learnqa");
        userDataWoEmail.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())
                .body(userDataWoEmail)
                .post(url)
                .andReturn();

    }
    @Step("to try create step w/o LastName field")
    public Response responseWOLastName (String url) {
        Map<String, String> userDataWoEmail = new HashMap<>();

        userDataWoEmail.put("email", DataGenerator.getrandomEmail());
        userDataWoEmail.put("password", "123");
        userDataWoEmail.put("username", "learnqa");
        userDataWoEmail.put("firstName", "learnqa");
        //userDataWoEmail.put("lastName", "learnqa");
        return given()
                .filter(new AllureRestAssured())
                .body(userDataWoEmail)
                .post(url)
                .andReturn();

    }


}
