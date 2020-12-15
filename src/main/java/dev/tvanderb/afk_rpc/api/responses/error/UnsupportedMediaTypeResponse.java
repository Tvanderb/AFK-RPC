package com.ts_mc.smcb.main.api.responses.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Name;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

public class UnsupportedMediaTypeResponse {

    private final String requested;
    private final List<String> supportedTypes = new ArrayList<>();

    public UnsupportedMediaTypeResponse(MediaType requested, @NotNull List<MediaType> supported) {
        if (requested == null) {
            this.requested = "";
        } else {
            this.requested = requested.toString();
        }

        for (MediaType m : supported) {
            supportedTypes.add(m.toString());
        }
    }

    @JsonProperty("requested-type")
    public String getRequestedType() {
        return requested;
    }

    @JsonProperty("supported-types")
    public String[] getSupportedTypes() {
        return supportedTypes.toArray(new String[0]);
    }

}
