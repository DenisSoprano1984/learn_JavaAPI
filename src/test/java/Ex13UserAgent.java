import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class Ex13UserAgent {

    String userAgent1 = "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    String platform1 = "Mobile"; // v
    String browser1 = "No"; // v
    String device1 = "Android"; // v

    String userAgent2 = "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1";
    String platform2 = "Mobile"; // v
    String browser2 = "Chrome"; // Nо' вместо Chrome
    String device2 = "iOS"; //  v

    String userAgent3 = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
    String platform3 = "Googlebot"; // Unknown' вместо 'Googlebot'
    String browser3 = "Unknown"; // v
    String device3 = "Unknown"; // v
    String userAgent4 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0";
    String platform4 = "Web"; // v
    String browser4 = "Chrome"; //  v
    String device4 = "No"; // v
    String userAgent5 = "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1";
    String platform5 = "Mobile"; // v
    String browser5 = "No"; // v
    String device5 = "iPhone"; // Unknown"


    @ParameterizedTest
    @ValueSource(strings = {"",
            "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
            "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
            "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
    })




    public void getUserAgent(String userAgent) {
        Map<String, String> header = new HashMap<>();
        header.put("user-agent", userAgent );

        JsonPath response = RestAssured
                .given()
                .headers(header)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        String agent = response.getString("user_agent");
        String platform = response.getString("platform");
        String browser = response.getString("browser");
        String device = response.getString("device");

        if (agent.equals(userAgent2)){

            assertEquals(platform, platform2, "Платформа не совпадате с ожиданием. Заказывали " + platform2 + " а получили: " + platform );
            assertEquals(browser, browser2, "Браузер не совпадате с ожиданием. Заказывали " + browser2 + " а получили: " + browser );
            assertEquals(device, device2, "устройсвот не совпадате с ожиданием. Заказывали " + device2 + " а получили: " + device );

        } else if (agent.equals(userAgent3)){
            assertEquals(platform,platform3, "Платформа не совпадает с ожиданием. Заказывали " + platform3 + " а получили " + platform );

        } else if (agent.equals(userAgent5)) {
            assertEquals(device,device5, "Платформа не совпадает с ожиданием Заказывали " + device5 + " а получили " + device);

        } else {

            System.out.println("тут выведу то что получилось для зароса с параметром хедера " + header);
            System.out.println("agent " + agent);
            System.out.println("platform " + platform);
            System.out.println("browser" + browser);
            System.out.println("device"+ device);
        }

        //assertEquals(platform, "Mobile");


//        System.out.println("agent " + agent);
//        System.out.println("platform " + platform);
//        System.out.println("browser" + browser);
//        System.out.println("device"+ device);

        //        Headers headers = response.getHeaders();
        //        response.prettyPrint();
        //        System.out.println(headers);



    }
}
