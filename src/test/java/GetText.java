import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class GetText {
    @Test
    public void GetText(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/get_text");
        response.andReturn();
        response.prettyPrint();

    }
}
