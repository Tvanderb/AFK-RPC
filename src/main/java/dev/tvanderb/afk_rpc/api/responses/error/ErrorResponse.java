package com.ts_mc.smcb.main.api.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ts_mc.smcb.main.api.responses.DefaultResponse;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlRootElement;
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
