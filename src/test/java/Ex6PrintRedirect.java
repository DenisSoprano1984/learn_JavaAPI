import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;


public class Ex6PrintRedirect {


    private static final Logger log = LoggerFactory.getLogger(Ex6PrintRedirect.class);

    @Test
    public void testParsingJson(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader11", "myValue11");

        Response response = RestAssured
                .given()
                .log().all()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        response.prettyPrint();

        String locationHeader = response.getHeader("Location");
        System.out.println("Адрес для перехода:" + " " + locationHeader);

    }
}
