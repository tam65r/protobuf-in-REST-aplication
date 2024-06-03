package com.example.sisdi_plans.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.MalformedURLException;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Received Inconsistent Data")
public class InconsistencyDataException extends RuntimeException{
    public InconsistencyDataException(final String string) {
        super(string);
    }

    public InconsistencyDataException(final String string, final MalformedURLException ex) {
        super(string, ex);
    }

    public InconsistencyDataException(final Class<?> clazz, final long id) {
        super(String.format("Data from %s with id %d is inconsistent", clazz.getSimpleName(), id));
    }

    public InconsistencyDataException(final Class<?> clazz, final String id) {
        super(String.format("Data from %s with id %s is inconsistent", clazz.getSimpleName(), id));
    }

    public InconsistencyDataException(final Class<?> clazz, final long id, final String message) {
        super(String.format("Data from %s with id %d is inconsistent: %s", clazz.getSimpleName(), id, message));
    }

    public InconsistencyDataException(final Class<?> clazz, final String id, final String message) {
        super(String.format("Data from %s with id %s is inconsistent: %s", clazz.getSimpleName(), id, message));
    }
}
