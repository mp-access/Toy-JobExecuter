package ch.uzh.ifi.access.jobe.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

class RunResultJsonTest {

    @Test
    void shouldMapToModel() throws IOException {
        var responseAsString = "{\n" +
                "  \"run_id\": null,\n" +
                "  \"outcome\": 11,\n" +
                "  \"cmpinfo\": \"  File \\\"example.py\\\", line 1\\n    print(\\\\\\\"Hello world!\\\\\\\")\\\\n\\n                            ^\\nSyntaxError: unexpected character after line continuation character\\n\\n\",\n" +
                "  \"stdout\": \"\",\n" +
                "  \"stderr\": \"\"\n" +
                "}";

        var model = new ObjectMapper().readValue(responseAsString, RunResult.class);

        assertThat(model.getOutcome()).isEqualTo(RunResult.Status.COMPILATION_ERROR);
        assertThat(model.getCmpInfo()).isNotBlank();
    }


}