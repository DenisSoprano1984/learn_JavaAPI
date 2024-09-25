import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.path.json.JsonPath.with;


public class Ex5ParsingJson {


    @Test
    public void testParsingJson(){
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        response.prettyPrint();

        String message = response.get("messages.message[1]");

        //не знаю как использовать эту конструкцию из документации
        //String message = with(Object).get("[1].message");
        //System.out.println(message);
        //ерунда List<String> messages = jsonPath.getList("messages.message[1]");



        System.out.println(message);

    }
}
