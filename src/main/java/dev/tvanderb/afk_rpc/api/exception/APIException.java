package dev.tvanderb.afk_rpc.api.exception;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

public class APIException extends Exception {

    private final HttpServletRequest request;

    public APIException(@NotNull String message, @NotNull HttpServletRequest cause) {
        super(message);

        request = cause;
    }

    @NotNull
    public HttpServletRequest getRequest() {
        return request;
    }

}
