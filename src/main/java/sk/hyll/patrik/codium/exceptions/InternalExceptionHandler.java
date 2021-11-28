package sk.hyll.patrik.codium.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sk.hyll.patrik.codium.helpers.Logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom handling of inter server errors(500)
 * @link https://stackoverflow.com/questions/48508285/how-to-handle-internal-server-error-500-on-spring-rest-api
 */
@ControllerAdvice
@Profile("production")
public class InternalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(InternalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.error("505 error: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occured... 😥");
    }
}

