import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;


public class Ex6PrintRedirect {


    @Test
    public void testParsingJson(){
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        response.prettyPrint();

        String message = response.get("messages.message[1]");
        System.out.println(message);

    }
}
