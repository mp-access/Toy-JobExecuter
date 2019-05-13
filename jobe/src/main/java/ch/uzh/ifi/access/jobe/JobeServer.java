package ch.uzh.ifi.access.jobe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JobeServer {

    private static final Logger log = LoggerFactory.getLogger(JobeServer.class);

    private static final String apiURL = "http://localhost:4000/jobe/index.php/restapi";

    private RestTemplate restTemplate;

    JobeServer() {
        restTemplate = new RestTemplate();
    }

    void getSupportedLanguages() {
        ResponseEntity<String> response = restTemplate.getForEntity(apiURL + "/languages", String.class);
        log.debug(response.getBody());
    }

    void runSimpleSpec() throws JsonProcessingException {
        var spec = RunSpec.builder()
                .languageId(RunSpec.Language.PYTHON3)
                .sourceFileName("example.py")
                .sourceCode("print(\\\"Hello world!\\\")\\n")
                .build();


        var mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(spec);
        HttpEntity<RunSpec> request = new HttpEntity<>(spec);
        log.debug(request.toString());
        log.debug(jsonString);


        ResponseEntity<RunResult> response = restTemplate.postForEntity(apiURL + "/runs", request, RunResult.class);
        log.debug(response.getBody().getOutcome().toString());
    }
}
