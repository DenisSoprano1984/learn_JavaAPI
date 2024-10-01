import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Ex8GetToken {
    String urlForJob = "https://playground.learnqa.ru/ajax/api/longtime_job";
    int second; // Тут будем хранить время ожидания
    String token;  // Создание переменной для хранения токена


    @Test
    public void testGetTokens() throws InterruptedException {

        Map<String, String> params = new HashMap<>(); // Создание рапаметров для вставки во второй запрос с токеном
        params.put("token", token ); // сами данные с токеном

       JsonPath response = RestAssured // первый запрос для создания задачи
               .get(urlForJob)
               .jsonPath();

       response.prettyPrint();
       token = response.get("token");
       second = response.get("seconds");

       System.out.println(token);

        System.out.println("тут записан токен перед If " + token);
        System.out.println("тут записан время в сек " + second);
        second = second * 1000;
        System.out.println("тут записано время в мс " + second);
        sleep(second);

}





}


