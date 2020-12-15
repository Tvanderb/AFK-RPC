package dev.tvanderb.afk_rpc.api.exception;

import dev.tvanderb.afk_rpc.Main;
import dev.tvanderb.afk_rpc.api.Response;
import dev.tvanderb.afk_rpc.api.responses.DefaultResponse;
import dev.tvanderb.afk_rpc.api.responses.error.UnsupportedMediaTypeResponse;
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
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public Response<DefaultResponse> handleUnauthorized(UnauthorizedException e, HttpServletRequest r) {
        return new Response<>(Main.getApp().getNextId(), 401, new DefaultResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public Response<DefaultResponse> handleBadRequest(BadRequestException e, HttpServletRequest r) {
        return new Response<>(Main.getApp().getNextId(), 400, new DefaultResponse(e.getMessage()));
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

        return new Response<>(Main.getApp().getNextId(), 415, new UnsupportedMediaTypeResponse(requested, supportedMediaTypes));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingRequestHeaderException.class})
    public Response<DefaultResponse> handleMissingRequestHeader(MissingRequestHeaderException e, HttpServletRequest r) {
        return new Response<>(Main.getApp().getNextId(), 400, new DefaultResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Response<DefaultResponse> handleMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest r) {
        return new Response<>(Main.getApp().getNextId(), 400, new DefaultResponse("An error occurred regarding your request."));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Response<DefaultResponse> handleInternalServerError(Exception e, HttpServletRequest r) {
        return new Response<>(Main.getApp().getNextId(), 500, new DefaultResponse("An internal server error occurred."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Response<DefaultResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest r) {
        List<HttpMethod> supportedMethods = Arrays.asList(e.getSupportedHttpMethods().toArray(new HttpMethod[0]));
        String requestedMethod = e.getMethod();

        return new Response<>(Main.getApp().getNextId(), 400, new DefaultResponse("This endpoint only supports: " + convertToStringList(supportedMethods) + ". You sent " + requestedMethod + "."));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Response<DefaultResponse> handleConstraintViolation(ConstraintViolationException e, HttpServletRequest r) {
        return new Response<>(Main.getApp().getNextId(), 400, new DefaultResponse("Request payload was invalid."));
    }

    private static String convertToStringList(List<?> list) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            if (i == 0 && list.size() != 1) {
                sb.append(o.toString() + ", ");
            } else if (i == 0) {
                sb.append(o.toString());
            } else if (i < list.size() - 2) {
                sb.append(o.toString() + ", ");
            } else if (i == list.size() - 1) {
                sb.append(o.toString());
            }

            i++;
        }

        return sb.toString();
    }

}
