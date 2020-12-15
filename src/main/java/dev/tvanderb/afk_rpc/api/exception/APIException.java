package com.ts_mc.smcb.main.api.exception;

import com.ts_mc.smcb.main.Utils;
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

    @NotNull
    public String getStringRequest() {
        return Utils.Request.getAsRequestLog(request);
    }

}
