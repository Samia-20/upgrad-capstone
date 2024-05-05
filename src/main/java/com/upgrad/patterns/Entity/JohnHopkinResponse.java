package com.upgrad.patterns.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JohnHopkinResponse {

    @JsonProperty("country")
    private String country;

    @JsonProperty("stats")
    private Stat stats;

}
