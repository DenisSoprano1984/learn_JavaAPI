import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


public class Ex11Cookie {

    @Test
    public void getCookie () {
            Response response = RestAssured
            .get("https://playground.learnqa.ru/api/homework_cookie")
                    .andReturn();

            Map<String, String> cuca = response.getCookies();
            //    System.out.println(cuca);
                String cucaValue = cuca.get("HomeWork");
           //     System.out.println(cucaValue);

                // assertTrue(cuca.containsKey("HomeWork"), "Кука отсутствует");

        //assertNotNull(response.getCookie("HomeWork"),"кука не пришла ");
        assertNotNull(cucaValue, "не пришла почему-то");
     //   assertTrue(response.getCookie("HomeWork")!= null, "кука не пришла");
        assertTrue(cucaValue.contains("hw_value"), "Значение cookie не соответсвует ожидаемому");

//
//
//        response.prettyPrint();
//        response.getHeaders();
//        Headers responseHeaders = response.getHeaders();
//        System.out.println(responseHeaders);
//        String cookieParsing = response.getCookie("HomeWork");
//        System.out.println(cookieParsing);
//
//        String cookieParsing1 = response.getCookie("expires");
//        System.out.println("Что такое Expire и как это забрать? "  + cookieParsing1);
//
//        String cookieParsing2 = response.getCookie("Max-Age");
//        System.out.println(cookieParsing2);
//
//        String cookieParsing3 = response.getCookie("path");
//        System.out.println(cookieParsing3);
//
//        String cookieParsing4 = response.getCookie("domain");
//        System.out.println(cookieParsing4);



    }
}
