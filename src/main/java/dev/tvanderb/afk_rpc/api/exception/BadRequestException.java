package com.ts_mc.smcb.main.api.exception;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

public class BadRequestException extends APIException {

    public BadRequestException(@NotNull String reason, @NotNull HttpServletRequest req) {
        super(reason, req);
    }

}
