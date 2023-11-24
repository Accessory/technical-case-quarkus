package de.hermes;

import com.google.gson.Gson;
import de.hermes.entities.Job;
import de.hermes.requests.EnterPathRequest;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(CustomResource.class)
public class RobotControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/tibber-developer-test/hallo-welt")
                .then()
                .statusCode(200)
                .body(is("Hallo Welt"));
    }

    @Test
    public void testEnterPathEndpoint() throws IOException {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/robotcleanerpathheavy.json")))) {
            var enterPathRequest = new Gson().fromJson(reader, EnterPathRequest.class);
            Job result = given()
                    .body(enterPathRequest)
                    .contentType(ContentType.JSON)
                    .when().post("/tibber-developer-test/enter-path")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON).extract().body().as(Job.class);

            assertTrue(result.id > 0);
            assertEquals(10000, result.commands);
            assertEquals(993737501, result.result);
        }
    }
}