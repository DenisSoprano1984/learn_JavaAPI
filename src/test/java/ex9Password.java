import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.Headers;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;



public class ex9Password {
    String [] popularPassword = new String[] { "12345678", "12345678", "12345678", "12345", "12345678", "12345", "12345678", "123456789", "qwerty", "qwerty", "abc123", "qwerty", "12345678", "qwerty", "12345678", "qwerty", "12345678", "password", "abc123", "qwerty", "abc123", "qwerty", "12345", "football", "12345", "12345", "1234567", "monkey", "monkey", "123456789", "123456789", "123456789", "qwerty", "123456789", "111111", "12345678", "1234567", "letmein", "111111", "1234", "football", "1234567890", "letmein", "1234567", "12345", "letmein", "dragon", "1234567", "baseball", "1234", "1234567", "1234567", "sunshine", "iloveyou", "trustno1", "111111", "iloveyou", "dragon", "1234567", "princess", "football", "qwerty", "111111", "dragon", "baseball", "adobe123[a]", "football", "baseball", "1234", "iloveyou", "iloveyou", "123123", "baseball", "iloveyou", "123123", "1234567", "welcome", "login", "admin", "princess", "abc123", "111111", "trustno1", "admin", "monkey", "1234567890", "welcome", "welcome", "admin", "qwerty123", "iloveyou", "1234567", "1234567890", "letmein", "abc123", "solo", "monkey", "welcome", "1q2w3e4r", "master", "sunshine", "letmein", "abc123", "111111", "abc123", "login", "666666", "admin", "sunshine", "master", "photoshop[a]", "111111", "1qaz2wsx", "admin", "abc123", "abc123", "qwertyuiop", "ashley", "123123", "1234", "mustang", "dragon", "121212", "starwars", "football", "654321", "bailey", "welcome", "monkey", "access", "master", "flower", "123123", "123123", "555555", "passw0rd", "shadow", "shadow", "shadow", "monkey", "passw0rd", "dragon", "monkey", "lovely", "shadow", "ashley", "sunshine", "master", "letmein", "dragon", "passw0rd", "654321", "7777777", "123123", "football", "12345", "michael", "login", "sunshine", "master", "!@#$%^&*", "welcome", "654321", "jesus", "password1", "superman", "princess", "master", "hello", "charlie", "888888", "superman", "michael", "princess", "696969", "qwertyuiop", "hottie", "freedom", "aa123456", "princess", "qazwsx", "ninja", "azerty", "123123", "solo", "loveme", "whatever", "donald", "dragon", "michael", "mustang", "trustno1", "batman", "passw0rd", "zaq1zaq1", "qazwsx", "password1", "password1", "Football", "password1", "0", "trustno1", "starwars", "password1", "trustno1", "qwerty123", "123qwe","password","password","123456","123456","123456","123456","123456","123456","123456","123456","123456","password","password","password","password","password","password","123456789" };
    Integer i = 0;
    String lastPassword = "";
    String authorisedSuccessful = "<html>\n" +
            "  <body>You are authorized</body>\n" +
            "</html>";
    String responsFromServer = "<html>\n" +
            "  <body>You are NOT authorized</body>\n" +
            "</html>";
    String notAuth = "<html>\n" +
            "  <body>You are NOT authorized</body>\n" +
            "</html>";


    @Test
    public void testPassword() {

        while (notAuth.equals(responsFromServer)) {

            Response response = RestAssured
                    .given()
                    .queryParam("login", "super_admin")
                    .queryParam("password", popularPassword[i])
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();


//            response.print();

            //Получение куки
//            System.out.println("\nPretty text:");
//            response.prettyPrint();

//            System.out.println("\nHeaders:");
            Headers responseHeaders = response.getHeaders();
//           System.out.println(responseHeaders);

//            System.out.println("\nCookies:");

              Map<String, String> responseCookies = response.getCookies();
//          System.out.println(responseCookies);

            String responseCook = response.getCookie("auth_cookie");
//          System.out.println(responseCook);


//          System.out.println(i);
//           System.out.println("Попробовали пароль: " + popularPassword[i]);
            lastPassword = popularPassword[i];

            // тут будет запрос который проверяет куку которую мы получили в ответ на предыдущий запрос
Map<String, String> cookies = new HashMap<>();
cookies.put("auth_cookie", responseCook);

            Response response2 = RestAssured
                    .given()
                    .cookies(cookies)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();

            response2.prettyPrint();
          response2.print();
      System.out.println("Сохранено в переменную после ответа с сервера " + responsFromServer);







            i = i + 1;



      System.out.println("Следующей пароль будет: " + popularPassword[i]);

        }
        System.out.println("ответ сервера: " + responsFromServer);
        System.out.println("Всего было итераций: " + i);
        System.out.println("Наверное это Ваш пароль: " + lastPassword);

    }
    }
// 1. Создать массив
// 2. Создать запрос на https://playground.learnqa.ru/ajax/api/get_secret_password_homework с  login - правильный, но передан неправильный password
// 3. Сохранить куку с шага 2
// 4. Созадать запрос на адрес https://playground.learnqa.ru/ajax/api/check_auth_cookie с сохраненной кукой
// 5. проверять ответ и если он не You are authorized - запускать цикл заново
// 6. вероятно while / создать переменную someAuthResult - Назначить ответ "You are NOT authorized"
// while ( someRes != “You are authorized”) делать запрос
// нужна переменная n = 0
// в запросе пароля будет массив с нуля и счетчик после каждого цикла должен n+1