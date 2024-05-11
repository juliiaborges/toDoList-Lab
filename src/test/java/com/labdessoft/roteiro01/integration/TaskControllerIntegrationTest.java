package com.labdessoft.roteiro01.integration;

import com.labdessoft.roteiro01.Roteiro01Application;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = Roteiro01Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

    
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/api/task").then().statusCode(200);
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
        get("/api/task/1").then().statusCode(200)
                .assertThat().body("description", equalTo("Primeira tarefa"));
    }
}