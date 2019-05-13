package ch.uzh.ifi.access.jobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
