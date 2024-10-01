import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Ex8Tokens {
    String urlForJob = "https://playground.learnqa.ru/ajax/api/longtime_job";
    Integer second; // Тут будем хранить время ожидания
    String token;  // Создание переменной для хранения токена
    Integer secondToNotReady;
    Integer secondToIsReady;
    String status = "";
    String statusForSearch = "Job is NOT ready";
    String statusFromReal = "";
    String statusJobAfterReady = "";
    String resultJob = "";
    @Test
    public void testGetTokens() throws InterruptedException {


       JsonPath response = RestAssured // первый запрос для создания задачи
               .get(urlForJob)
               .jsonPath();


       token = response.get("token");
       second = response.get("seconds");

       System.out.println("создал задачу");

        // Для отладки System.out.println("тут записан токен перед If " + token);
        // Для отладки System.out.println("тут записан время в сек " + second);
        secondToNotReady = ((second) * 1000)/2;
        secondToIsReady = (second + 1) * 1000;
        // Для отладки System.out.println("тут записано время в мс не готово " + secondToNotReady);
        sleep(secondToNotReady);


        Map<String, String> params = new HashMap<>(); // Создание рапаметров для вставки во второй запрос с токеном
        params.put("token", token ); // сами данные с токеном


        JsonPath response2 = RestAssured // Тут проверим что задач не готова.
            .given()
            .queryParams(params) //

            .get(urlForJob)
            .jsonPath();

        System.out.println("делал один запрос с token ДО того, как задача готова");

        statusFromReal = response2.get("status");
        // Для отладки System.out.println("тут записано время в мс готово " + secondToIsReady);

        // Для отладки System.out.println(statusFromReal);



    if (statusForSearch.equals(statusFromReal)) {
          System.out.println("Статус " + statusForSearch + " найден в ответе все гуд");
       }
        System.out.println("жду " + second + "+1 секунд с помощью функции Thread.sleep()");

    sleep(secondToIsReady);

        // Запрос после жидания необходимого времени для получения ответа с успешной джобой

        JsonPath response3 = RestAssured // Тут проверим что задач не готова.
                .given()
                .queryParams(params) //
                .get(urlForJob)
                .jsonPath();



        statusJobAfterReady = response3.get("status");
        resultJob = response3.get("result");

       // Отладка System.out.println(statusJobAfterReady);
        // Отладка System.out.println(resultJob);

        if (statusJobAfterReady.equals("Job is ready")) {
            System.out.println("Тут результат соответвтует нашему ожиданию: " + statusJobAfterReady);

           if(resultJob != null ) {
                System.out.println("Поле result в наличии и имеет знание: " + resultJob);
            } else
            { System.out.println("поля результат нету :(" );
        }

    }
    }
}


