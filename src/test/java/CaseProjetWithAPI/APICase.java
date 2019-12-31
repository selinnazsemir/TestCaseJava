package CaseProjetWithAPI;


import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.testng.internal.TestResult;
import org.testng.remote.ConnectionInfo;

import java.sql.Connection;

public class APICase
{
    @Test
    public void GetAPIAllBook()
    {
        //GET methodu ile tüm kitapların listesini getirme işlemini test etme.
        //Beklenilen çıktı: Tüm kitaplar: Kitaplar Listesi.

        RestAssured.baseURI = "http://localhost:59087/api/";
        RequestSpecification request = RestAssured
                .given()
                .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()));
        Response response = request
                .when()
                .get("/Book/allbook")
                .then()
                .statusCode(200)
                .extract().response();
        int responseCode = response.getStatusCode();

        System.out.println("Status Code: "+responseCode);
        System.out.println("Tüm Kitaplar: " +response.asString());

    }
    @Test
    public void GetAPIWithID()
    {
        //Lütfen testi başlatmadan önce Put Testini çalıştırıp kitap ekleyiniz:)
        //GET methodu ile ID'ye göre, ID'si girilen kitabın bilgilerini getirme işlemini test etme.
        //Beklenilen çıktı: ID'si verilen kitap bilgileri.
        RestAssured.baseURI = "http://localhost:59087/api/";
        RequestSpecification request = RestAssured
                .given()
                .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()));
        Response response = request
                .when()
                .queryParam("book_id",1)
                .get("/Book/book")
                .then()
                .statusCode(200)
                .extract().response();
        int responseCode = response.getStatusCode();

        System.out.println("Status Code: "+responseCode);
        System.out.println("Gelen Kitap: "+response.asString());

    }
    @Test
    public void GetAPIWithIDNotFound()
    {
        //GET methodu ile ID'ye göre, ID'si girilen kitap sistemde kayıtlı değilse işlemini test etme.
        //Beklenilen çıktı: Sonuc: Kitap Bulunamadı.
        RestAssured.baseURI = "http://localhost:59087/api/";
        RequestSpecification request = RestAssured
                .given()
                .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()));
        Response response = request
                .when()
                .queryParam("book_id",50)
                .get("/Book/book")
                .then()
                .statusCode(404)
                .extract().response();
        int responseCode = response.getStatusCode();

        System.out.println("Status Code: "+responseCode);
        System.out.println("Sonuc: "+response.asString());
    }

    @Test
    public void PutAPI()
    {
     //PUT Methodu ile yeni kitap ekleyebilme testi.
     // Beklenilen console çıktısı: Kayıt Başarılı, Status Code: 200

        RestAssured.baseURI ="http://localhost:59087/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("author", "test1");
        requestParams.put("title", "deneme2");
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.put("/Book/putaddbook");
        int statusCode = response.getStatusCode();

        System.out.println(response.asString());
        System.out.println("Status Code: "+statusCode);


    }
    @Test
    public void PutMethodErrorMessageControl()
    {
        //PUT Methoduyla kitap eklerken, author ve title bilgisi girilmediğinde,
        //"'eror': 'Field' 'author' is required'" hata mesajı döndürdüğünü kontrol etme case'i
        //Beklenilen çıktı: "'eror': 'Field' 'author' is required'"

        RestAssured.baseURI ="http://localhost:59087/api/";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("author", "");
        requestParams.put("title", "");
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.put("/Book/putaddbook");
        int statusCode = response.getStatusCode();

        System.out.println(response.asString());
        System.out.println("Status Code: "+statusCode);
    }
    @Test
    public void PutMethodSimilarBookControl()
    {
        //PUT Methodu ile aynı isimde birden fazla kitap eklenmeye çalıştığında hata mesajı gönderme durumunu test etme.
        // Beklenilen çıktı: "Another book with similartitle and author already exists."

        RestAssured.baseURI ="http://localhost:59087/api/";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("author", "deneme");
        requestParams.put("title", "deneme");
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.put("/Book/putaddbook");
        int statusCode = response.getStatusCode();

        System.out.println(response.asString());
        System.out.println("Status Code: "+statusCode);
    }



}
