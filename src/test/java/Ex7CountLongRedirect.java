import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Ex7CountLongRedirect {


    @Test
    public void testRedirectCaunter() {

        String urlGet = "https://playground.learnqa.ru/api/long_redirect";
        String urlRedirect;
        int redirectCount = 0;
        int statusCode;
        System.out.println("первая ссылка: " + urlGet);

        Response response = RestAssured
                .given()
                //.log().all()
                .redirects()
                .follow(false)
                .when()
                .post(urlGet)
                .andReturn();

        statusCode = response.getStatusCode();
        urlRedirect = response.getHeader("Location");
        System.out.println("первый редирект: " + urlRedirect);
        System.out.println("статус код после первого редиректа: " + statusCode);

        while (statusCode != 200 ) {

            Response response1 = RestAssured
                    .given()
                    //.log().all()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(urlGet)
                    .andReturn();


            statusCode = response1.getStatusCode();
            urlRedirect = response1.getHeader("Location");
            redirectCount = redirectCount +1;
            System.out.println("статус код в цикле редиректа: " + statusCode);

            System.out.println("число редиректов уже: " + redirectCount);
            System.out.println("следующий адресс для редирект: " + urlRedirect);

            urlGet = urlRedirect;

            System.out.println("урл get после присвоения ему адреса переменной urlRedirect: " + urlGet);
        }
        System.out.println("Каунтер редиректов для ДЗ7: " + redirectCount);
        System.out.println("последний статус код: " + statusCode);
        }
        // коммит и пуш
        }


