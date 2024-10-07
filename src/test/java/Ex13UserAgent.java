import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class Ex13UserAgent {


    @Test
    public void getUserAgent() {
        Response response = RestAssured
                .given()
                .headers("User Agen", "Android")
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .andReturn();

        Headers headers = response.getHeaders();
        System.out.println(headers);



    }
}
