package ch.uzh.ifi.access.jobe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunResult {

    @JsonProperty(value = "run_id")
    private String runId;

    private Status outcome;

    @JsonProperty(value = "cmpinfo")
    private String cmpInfo;

    @JsonProperty(value = "stdout")
    private String stdout;

    @JsonProperty(value = "stderr")
    private String stderr;

    @JsonProperty(value = "outcome")
    void setOutcome(int status){
        outcome = Status.getById(status);
    }

    enum Status{
        COMPILATION_ERROR(11), RUNTIME_ERROR(12), TIME__LIMIT_EXCEEDED(13), OK(15);

        private final int id;

        Status(int id) { this.id = id; }

        int getValue() { return id; }

        static Status getById(int id){
            for(Status s : values()) {
                if(s.id == id) {
                    return s;
                }
            }
            return null;
        }

    }
}
