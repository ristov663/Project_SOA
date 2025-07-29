package mk.ukim.finki.soa.masterthesis.pact;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.soa.masterthesis.model.oldEvents.student.StudentCreatedExternalEvent;
import mk.ukim.finki.soa.masterthesis.model.valueObject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import au.com.dius.pact.provider.junit5.HttpTestTarget;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("student_provider")
@Consumer("master_thesis_consumer")
@PactFolder("pacts")
public class StudentCreatedEventProducerTest {

    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String KEY_CONTENT_TYPE = "contentType";

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 9092, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Student with ID Student:00000000-0000-0000-0000-000000000001 exists")
    public void studentWithSpecificIdExists() {
        System.out.println("Setting up provider state: Student with specific ID exists");
    }

    @PactVerifyProvider("Student Created Event")
    public MessageAndMetadata verifyMessage() throws JsonProcessingException {
        StudentCreatedExternalEvent event = new StudentCreatedExternalEvent(
                new StudentId("Student:00000000-0000-0000-0000-000000000001"),
                new StudentIndex("211151"),
                "Bojan Ristov",
                new Email("bojan.ristov@students.finki.ukim.mk")
        );

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> metaData = Map.of(
                KEY_CONTENT_TYPE, JSON_CONTENT_TYPE
        );

        return new MessageAndMetadata(
                objectMapper.writeValueAsString(event).getBytes(),
                metaData
        );
    }
}
