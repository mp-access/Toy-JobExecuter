package ch.uzh.ifi.access.jobe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonRootName(value = "run_spec")
@JsonTypeName(value = "run_spec")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class RunSpec implements Serializable {

    public enum Language {PYTHON3}

    private Language languageId;

    @JsonProperty(value = "sourcecode")
    private String sourceCode;

    @JsonProperty(value = "sourcefilename")
    private String sourceFileName;

    @JsonProperty(value = "language_id")
    public String getLanguageId() {
        return languageId.toString().toLowerCase();
    }

}
