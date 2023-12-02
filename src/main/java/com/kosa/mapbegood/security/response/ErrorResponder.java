package com.kosa.mapbegood.security.response;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());

        if (Objects.isNull(message)) {
            ErrorResponse errorResponse = ErrorResponse.of(status);
            response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
        } else {
            ErrorResponse errorResponse = ErrorResponse.of(status, message);
            response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
        }
    }
}
