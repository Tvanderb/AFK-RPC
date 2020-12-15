package dev.tvanderb.afk_rpc.api.exception;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

public class UnauthorizedException extends APIException {

    public UnauthorizedException(@NotNull String message, @NotNull HttpServletRequest req) {
        super(message, req);
    }

}
