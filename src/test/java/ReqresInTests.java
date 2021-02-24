import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ReqresInTests {

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void createUserPost() {
        String data = "{" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"" +
                "}";
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("id", is(notNullValue()));
    }

    @Test
    public void deleteUser() {
        given()
                .when()
                .delete("/user/2")
                .then()
                .statusCode(204);
    }

    @Test
    public void getUser2() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    public void successfulRegistration() {
        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .post("/register")
                .then()
                .statusCode(200)
                .log().body()
                .body("token", is(notNullValue()));
    }

    @Test
    public void unSuccessfulRegistration() {
        String data = "{\n" +
                "    \"email\": \"sydney@fife\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .post("/register")
                .then()
                .statusCode(400)
                .log().body()
                .body("error", is("Missing password"));
    }
}
