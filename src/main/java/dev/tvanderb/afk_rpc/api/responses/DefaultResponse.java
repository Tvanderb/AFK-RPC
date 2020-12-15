package com.ts_mc.smcb.main.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DefaultResponse {

    private String message;

    public DefaultResponse() {}

    /**
     * Create a new {@link DefaultResponse MessageResponse} object.
     *
     * @param message The message to associate with this response.
     */
    public DefaultResponse(@NotNull String message) {
        this.message = message;
    }

    /**
     * @return Get the response message.
     */
    @XmlElement(name = "message")
    @JsonProperty("message")
    @NotNull
    public String getMessage() {
        return message;
    }

}
