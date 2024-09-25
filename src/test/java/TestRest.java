import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class TestRest {
    @Test
    public void testRestAssured(){

        Map<String, String> params = new HashMap<>();
        params.put("name", "Johan");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String name = response.get("answer2");
        if (name == null) {
            System.out.println("The kay 'answer2' is absent");
        } else {
            System.out.println("name");
        }
        System.out.println(response);
        response.prettyPrint();


    }
}
