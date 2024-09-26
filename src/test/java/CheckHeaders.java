import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import io.restassured.http.Headers;

import java.util.HashMap;
import java.util.Map;


public class CheckHeaders {


    @Test
    public void testHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader11", "myValue11");

        Response response = RestAssured
                .given()
                .headers(headers)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();
        //response.print();
        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);
        String locationHeader = response.getHeader("Location");
        System.out.println(locationHeader);


    }
}
