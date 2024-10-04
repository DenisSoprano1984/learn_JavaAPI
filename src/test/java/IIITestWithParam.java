import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Map;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class IIITestWithParam {

@ParameterizedTest
@ValueSource(strings = {"", "Johan","Peter"})


    public void testHelloMethodWithoutName(String name){
        Map<String, String> queryParams = new HashMap<>();

        if(name.length() > 0 ){
            queryParams.put("name", name);
        }

    JsonPath response = RestAssured
                .given()
                .queryParams(queryParams)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
    String answer = response.getString("answer");

    String expectedName = (name.length() > 0) ?  name : "someone" ;

    assertEquals("The answer is not expected", answer, "Hello, " + expectedName);




    }
    @Test
    public void testHelloMethodWithName(){
        String name = "username";

        JsonPath response = RestAssured
                .given()
                .queryParam("name", name)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();
        String answer = response.getString("answer");
        assertEquals("The answer is  expected", answer, "Hello, "+ name );




    }


}
