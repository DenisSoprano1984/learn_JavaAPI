import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IIISimplTests {


    @Test
    public void testSimpleFor200(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map")
                .andReturn();
        assertEquals("Unexpected status", 200, response.statusCode());


    }
    @Test
    public void testSimpleFor404(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/map42")
                .andReturn();
        assertEquals("Unexpected status", 404, response.statusCode());


    }


}
