import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class CheckTypeGet {


    @Test
    public void testCheckTypeGet(){
        Response response = RestAssured
                .given()
                .queryParam("param", "values")
                .queryParam("paramPamPam", "Valueees")
                .get("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        response.prettyPrint();
        response.print();


    }
}
