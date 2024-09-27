import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class experimentFromInternet {

    @Test
    public void experimentFromInternet(){
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        response.prettyPrint();
    }
}
