package mk.ukim.finki.soa.masterthesis.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "student_provider", hostInterface = "localhost")
class PactStudentServiceUnitTest {

    @Pact(consumer = "master_thesis_consumer")
    public RequestResponsePact getStudentById(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("Student with ID Student:00000000-0000-0000-0000-000000000001 exists")
                .uponReceiving("A request to fetch student by ID")
                .path("/students/Student:00000000-0000-0000-0000-000000000001")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(
                        """
                        {
                            "id": {
                                "value": "Student:00000000-0000-0000-0000-000000000001"
                            },
                            "name": "Anastasija Tashkova",
                            "email": "anastasija.tashkova@students.finki.ukim.mk",
                            "studentIndex": "211299",
                            "enrollment": {
                                "program": "Software engineering and information systems",
                                "year": 4
                            },
                            "validated": true
                        }
                        """.trim()
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getStudentById", port = "9999")
    void whenGetStudentById_thenReturnStudent(MockServer mockServer) {
        // Arrange
        String url = mockServer.getUrl() + "/students/Student:00000000-0000-0000-0000-000000000001";

        // Act
        var response = new org.springframework.web.client.RestTemplate()
                .getForEntity(url, String.class);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getHeaders().getContentType().toString()).contains("application/json");
        assertThat(response.getBody()).contains("Student:00000000-0000-0000-0000-000000000001", "Anastasija Tashkova", "anastasija.tashkova@students.finki.ukim.mk", "211299", "Software engineering and information systems", "4", "true");
    }
}
