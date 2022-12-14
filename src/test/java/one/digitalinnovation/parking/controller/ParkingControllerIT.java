package one.digitalinnovation.parking.controller;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerIT /*extends AbstractContainerBase*/ {
    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest() {
        System.out.println(randomPort);
        RestAssured.port = randomPort;
    }

    @Test
    void whenfindAllThenCheckResult() {
        /*
        // qdo se quer uma lista de objetos como retorno
        RestAssured
                .given()
                .when()
                .get("/parking")
                .then()
                .extract()
                .response()
                .body().prettyPrint();
         */
        /*
        RestAssured
                .given()
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("license[0]", Matchers.equalTo("DMS-1111"));

         */
        /*
        RestAssured
                .given()
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());

         */
        /*
        RestAssured
                .given()
                .auth()
                .basic("user", "dio")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());

         */
        /*
        RestAssured
                .given()
                .auth()
                .basic("user", "12345")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());

         */

        RestAssured
                .given()
                .header("Authorization", "Basic mfawiq4wlnasdkjar")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenCreateThenCheckCreated() {
        var createDTO = new ParkingCreateDTO();
        createDTO.setColor("AMARELO");
        createDTO.setLicense("WRT-5555");
        createDTO.setModel("BRASILIA");
        createDTO.setState("SP");

        RestAssured
                .given()
                .auth()
                .basic("user", "dio")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("WRT-5555"))
                .body("color", Matchers.equalTo("AMARELO"));

    }
}
