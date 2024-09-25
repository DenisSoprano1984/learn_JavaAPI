import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class CheckTypePost {


    @Test
    public void testParsingJson(){
        Map<String, Object> body = new HashMap<>();
        body.put("param", "values");
        body.put("param0", "values0");
        body.put("param1", "values1");
        body.put("param11", "values11");

        Response response = RestAssured
                .given()
                .body(body)
                //.body("{\"paramPamPam\": \"Valueees\"}")
                //.body("paramPamPam=Valueees&param = values")
                //.queryParam("param", "values")
                //.queryParam("paramPamPam", "Valueees")
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        //response.prettyPrint();
        response.print();


    }
}
