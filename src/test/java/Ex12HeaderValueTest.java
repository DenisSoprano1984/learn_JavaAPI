import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex12HeaderValueTest {

    @Test
    public void headerValueTest (){

        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers  headers = response.getHeaders();
        //  System.out.println(headers);
        String headers1 = response.getHeader("Date");
      //  System.out.println(headers1);

        String headers2 = response.getHeader("x-secret-homework-header");
    //    System.out.println("какой-то секретный хедер для домашней работы " + headers2);

        assertTrue(response.header("x-secret-homework-header")!= null, "Секретный хедер не пришел");
        assertTrue(headers2.contains("secret"), "Отсутсвует секретное содержание в значении в хедере");


        //response.prettyPrint();




    }
}
