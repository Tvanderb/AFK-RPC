package com.ts_mc.smcb.main.api.exception;
import com.ts_mc.smcb.internal.logging.LogLevel;
import com.ts_mc.smcb.main.Main;
import com.ts_mc.smcb.main.api.Response;
import com.ts_mc.smcb.main.Utils;
import com.ts_mc.smcb.main.api.responses.DefaultResponse;
import com.ts_mc.smcb.main.api.responses.error.ErrorResponse;
import com.ts_mc.smcb.main.api.responses.error.UnsupportedMediaTypeResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public Response<DefaultResponse> handleUnauthorized(UnauthorizedException e, HttpServletRequest r) {
        String message = "[" + Utils.Request.getAsRequestLog(r) + "] Handled unauthorized request: " + e.getMessage();
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        return new Response<>(Main.getStormAPI().getNextId(), 401, new DefaultResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public Response<DefaultResponse> handleBadRequest(BadRequestException e, HttpServletRequest r) {
        String message = "[" + Utils.Request.getAsRequestLog(r) + "] Handled bad request: " + e.getMessage();
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        return new Response<>(Main.getStormAPI().getNextId(), 400, new DefaultResponse(e.getMessage()));

    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Response<UnsupportedMediaTypeResponse> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException e, HttpServletRequest r) {
        MediaType requested = e.getContentType();
        List<MediaType> supportedMediaTypes = e.getSupportedMediaTypes();

        String requestedStr = "";
        if (requested != null) {
            requestedStr = requested.toString();
        }

        String message = "[" + Utils.Request.getAsRequestLog(r) + "] [requested \"" + requestedStr + "\"] [supported \"" + Utils.convertToStringList(supportedMediaTypes) + "\"] Handled request with an unsupported media type.";
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        return new Response<>(Main.getStormAPI().getNextId(), 415, new UnsupportedMediaTypeResponse(requested, supportedMediaTypes));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingRequestHeaderException.class})
    public Response<DefaultResponse> handleMissingRequestHeader(MissingRequestHeaderException e, HttpServletRequest r) {
        String message = "[" + Utils.Request.getAsRequestLog(r) + "] Handled bad request.";
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        return new Response<>(Main.getStormAPI().getNextId(), 400, new DefaultResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Response<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest r) {
        String message = "[" + Utils.Request.getAsRequestLog(r) + "] Handled unreadable request";
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        UUID id = Main.getLoggingSystem().log(getClass(), e);
        return new Response<>(Main.getStormAPI().getNextId(), 400, new ErrorResponse("An error occurred regarding your request. Please report the error ID.", id));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Response<ErrorResponse> handleInternalServerError(Exception e, HttpServletRequest r) {
        UUID errorId = UUID.randomUUID();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        String message = "[" + Utils.Request.getAsRequestLog(r) + "] [" + errorId.toString() + "] An exception occurred during a server request: \n\n" + sw.toString() + "\n";
        Main.getLoggingSystem().log(getClass(), LogLevel.ERROR,  message);

        return new Response<>(Main.getStormAPI().getNextId(), 500, new ErrorResponse("An internal server error occurred. Please report the error ID.", errorId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Response<DefaultResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest r) {
        String message = "[" + Utils.Request.getAsRequestLog(r) + "] Handled request with invalid HTTP method.";
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        List<HttpMethod> supportedMethods = Arrays.asList(e.getSupportedHttpMethods().toArray(new HttpMethod[0]));
        String requestedMethod = e.getMethod();

        return new Response<>(Main.getStormAPI().getNextId(), 400, new DefaultResponse("This endpoint only supports: " + Utils.convertToStringList(supportedMethods) + ". You sent " + requestedMethod + "."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Response<DefaultResponse> handleConstraintViolation(ConstraintViolationException e, HttpServletRequest r) {
        String message = "[" + Utils.Request.getAsRequestLog(r) + "] Handled an invalid payload.";
        Main.getLoggingSystem().log(getClass(), LogLevel.DEBUG,  message);

        return new Response<>(Main.getStormAPI().getNextId(), 400, new DefaultResponse("Request payload was invalid. Please refer to the Trinity docs."));
    }

}
