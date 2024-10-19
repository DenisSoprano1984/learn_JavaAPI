package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

@Epic("Registration cases")
@Feature("Registrations")

public class UserRegisterCase extends BaseTestCase {

    String url = "https://playground.learnqa.ru/api/user/";

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("this test Create User With Existing Email! ")
    @DisplayName("Test negative  create user!2")
//    public void testCreateUserWithExistingEmail() {
//        String email = "vinkotov@example.com";
//
//
//        Map<String, String> userData = new HashMap<>();
//        userData.put("email", email);
//        userData = DataGenerator.getRegistrationData(userData);
//
//
//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();
//
////        System.out.println(responseCreateAuth.asString());
////        System.out.println(responseCreateAuth.statusCode());
//
//        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
//        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");


    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";


        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);


        Response responseCreateAuth = apiCoreRequests
                .responseCreateUser(
                        url,
                        userData);


        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
    }




    @Test
    @Description("this test Create User Successfully 'Description'")
    @DisplayName("Test positive create user 'DisplayName'")
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getrandomEmail();
        Map<String, String> userData = DataGenerator.getRegistrationData();

//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();

        Response responseCreateUserSuccessefully = apiCoreRequests
                .responseCreateUser(url, userData);

 //     System.out.println(responseCreateAuth.asString());
//        System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateUserSuccessefully, 200);
        Assertions.assertJsonHasField(responseCreateUserSuccessefully, "id");
    }


    // Создание пользователя с некорректным email - без символа @
    @Test
    @Description("this test Create User With Out At")
    @DisplayName("Test negative create user not valid email")
    public void testCreateUserWithOutAt() {
        String email = "vinkotovexample.com";


        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);


//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();

//        System.out.println(responseCreateAuth.asString());
//        System.out.println(responseCreateAuth.statusCode());

        Response testWithoutAt = apiCoreRequests
                .responseCreateUser(url,
                        userData

        );

        Assertions.assertResponseCodeEquals(testWithoutAt, 400);
        Assertions.assertResponseTextEquals(testWithoutAt, "Invalid email format");

}
    // Создание пользователя с очень коротким именем в один символ
    @Test
    @Description("this test Create User With Short First Name")
    @DisplayName("Test negative create user With Short First Name")
    public void testCreateUserWithShortFirstName() {
        String email = "vinkoto@vexample.com";
        String firstName = "u";


        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);


        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post(url)
                .andReturn();

//     System.out.println(responseCreateAuth.asString());
//      System.out.println(responseCreateAuth.statusCode());

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTextEquals(responseCreateAuth, "The value of 'firstName' field is too short");
    }

    //Создание пользователя с очень длинным именем - длиннее 250 символов
    @Test
    @Description("this test Create User With to long First Name")
    @DisplayName("Test negative create user With to long First Name")
    public void testCreateUserWithToLongFirstName() {
        String email = "vinkoto@vexample.com";
        String firstName = "ECeHmdbrXNOhcwaTwn466zAh5udh8UGP7sROg0lTz4Buiw5toCGO1hCJ3x8O2N6r8Dp9Ot4CwUDqofY56KRlbeKUAv15HHaotD1l7jFeu3iZKfAhrSDWNUxlMpBuoIoz2FhvElSkhPtc4GmlSTuhrisEQ35LoxRMRi1sSVjdN2OZrAlKiDogzqQuftCFvhU5K6G2cbi81JXFshFYwAGAmivSx34x66tP0GjDeMWRw1K5duez6uI4AF2BpUJ";


        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("firstName", firstName);
        userData = DataGenerator.getRegistrationData(userData);


//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();

//     System.out.println(responseCreateAuth.asString());
//      System.out.println(responseCreateAuth.statusCode());

        Response testToLongFirstName = apiCoreRequests
                .responseCreateUser(url, userData);

        Assertions.assertResponseCodeEquals(testToLongFirstName, 400);
        Assertions.assertResponseTextEquals(testToLongFirstName, "The value of 'firstName' field is too long");
    }


    // Создание пользователя без указания одного из полей - с помощью @ParameterizedTest необходимо проверить, что отсутствие любого параметра не дает зарегистрировать пользователя
    //@Test
    @Description("this test Create User Without 1 fiend")
    @DisplayName("Test negative create user Without 1 fiend")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public void testCreateUserWithOutField (String field) {
        // String email = "vinkoto@vexample.com";
//       String email = DataGenerator.getrandomEmail();
       // String firstName = "";

        //{firstName=learnqa, lastName=learnqa, password=123, email=learnqa202410161732@example.mom, username=learnqa}
//        Map<String, String> userData2 = new HashMap<>();
//        userData2.put("email", email);
//        userData2.put("firstName","learnqa");
//        userData2.put("lastName","learnqa");
//        userData2.put("username","learnqa");
//        //userData2.put("password","learnqa");


        //Map<String, String> userData = DataGenerator.getRegistrationData();


//        Response responseCreateAuth = RestAssured
//                .given()
//                .body(userData)
//                .post("https://playground.learnqa.ru/api/user/")
//                .andReturn();

//        Response responseWithoutOneField = apiCoreRequests
//                .responseCreateUser(url, userData2);

//      System.out.println(responseWithoutOneField.asString());
//      System.out.println(responseWithoutOneField.statusCode());
      System.out.println(field);

//      System.out.println(userData2);

        RequestSpecification spec = RestAssured.given();
        spec.baseUri(url);

      if (field.equals("email")){
          Response responseWoField = apiCoreRequests.responseWOemail(url);

          Assertions.assertResponseCodeEquals(responseWoField, 400);
          Assertions.assertResponseTextEquals(responseWoField, "The following required params are missed: " + field );

      } else if (field.equals("password")){
          Response responseWoField = apiCoreRequests.responseWOPassword(url);

          Assertions.assertResponseCodeEquals(responseWoField, 400);
          Assertions.assertResponseTextEquals(responseWoField, "The following required params are missed: " + field );
      }  else if (field.equals("username")){
          Response responseWoField = apiCoreRequests.responseWOUsername(url);

          Assertions.assertResponseCodeEquals(responseWoField, 400);
          Assertions.assertResponseTextEquals(responseWoField, "The following required params are missed: " + field );
      } else if (field.equals("firstName")){
          Response responseWoField = apiCoreRequests.responseWOFirstName(url);

          Assertions.assertResponseCodeEquals(responseWoField, 400);
          Assertions.assertResponseTextEquals(responseWoField, "The following required params are missed: " + field );
      } else if (field.equals("lastName")){
          Response responseWoField = apiCoreRequests.responseWOLastName(url);

          Assertions.assertResponseCodeEquals(responseWoField, 400);
          Assertions.assertResponseTextEquals(responseWoField, "The following required params are missed: " + field );
      }else {
          throw new IllegalArgumentException("Condition value is known: " + field);
      }

        //Assertions.assertResponseCodeEquals(responseWithoutOneField, 400);
        //Assertions.assertResponseTextEquals(responseWithoutOneField, "The following required params are missed: " + field );
    }
}
