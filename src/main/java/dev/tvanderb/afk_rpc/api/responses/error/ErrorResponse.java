package dev.tvanderb.afk_rpc.api.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.tvanderb.afk_rpc.api.responses.DefaultResponse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ErrorResponse extends DefaultResponse {

    private final UUID errorId;

    public ErrorResponse(@NotNull String message, @NotNull UUID errorId) {
        super(message);

        this.errorId = errorId;
    }

    @JsonProperty("error-id")
    @NotNull
    public String getErrorId() {
        return errorId.toString();
    }

}
