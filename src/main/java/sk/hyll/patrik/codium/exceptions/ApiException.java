package sk.hyll.patrik.codium.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * Simple structure used to send information to the user
 */
public class ApiException {
    // Error message
    private final String message;
    // HTTP status code
    private final HttpStatus httpStatus;
    // Date and time information
    private final ZonedDateTime zonedDateTime;

    // Constructor
    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }

    // Getters
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
