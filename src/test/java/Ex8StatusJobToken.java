import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Ex8StatusJobToken {
    String urlForJob = "https://playground.learnqa.ru/ajax/api/longtime_job";
    int second; // Тут будем хранить время ожидания
    String token = "QMyoTMyoTMxACMz0SOw0CNyAjM";  // Создание переменной для хранения токена


    @Test
    public void testGetTokens() throws InterruptedException {

        Map<String, String> params = new HashMap<>(); // Создание рапаметров для вставки во второй запрос с токеном
        params.put("token", token ); // сами данные с токеном

        Response response2 = RestAssured
                .given()
                .queryParams(params) //
                .log().all()
                .get(urlForJob)
                .andReturn();

        response2.prettyPrint();








}

}


