package ch.uzh.ifi.access.jobe.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class RunSpecJsonTest {

    @Test
    void shouldMapToJson() throws JsonProcessingException {
        var spec = RunSpec.builder()
                .languageId(RunSpec.Language.PYTHON3)
                .sourceFileName("script.py")
                .sourceCode("print(\\\"Hello world!\\\")\\n")
                .build();

        var json = new ObjectMapper().writeValueAsString(spec);

        assertThat(json).contains("\"run_spec\":");
        assertThat(json).contains("\"language_id\":\"python3\"");
        assertThat(json).contains("\"sourcefilename\":\"script.py\"");
        assertThat(json).contains("\"sourcecode\":");
    }

}