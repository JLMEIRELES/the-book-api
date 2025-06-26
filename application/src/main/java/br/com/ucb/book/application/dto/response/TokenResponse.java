package br.com.ucb.book.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    @Schema(
            description = "Token JWT no formato Bearer",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    @JsonProperty("access_token")
    private String accessToken;
}
