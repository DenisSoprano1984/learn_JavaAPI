import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class Ex10ShortPhrase {

    String hello = "Hello, world!";


    @Test
    public void testForFifteenSimbols() {

        String phrase = (hello.length() > 15) ?  "больше" : "меньше или равно" ;

        System.out.println( hello.length() + " знаков, а значит фраза  '" + hello + "' длинной " + phrase  + " 15 символов");

            assertEquals("Unexpected length < 16 но " + hello.length(), true , hello.length()>=15);


        }


}
