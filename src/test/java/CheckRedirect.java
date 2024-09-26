import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class CheckRedirect {


    @Test
    public void testRedirect(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader11", "myValue11");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();
        //response.print();
        //получение всех заголовHeaders responseHeaders = response.getHeaders();
        //System.out.println(responseHeaders);
String locationHeader = response.getHeader("Location");
System.out.println(locationHeader);


    }
}
