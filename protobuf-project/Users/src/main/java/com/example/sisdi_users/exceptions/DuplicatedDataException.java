package com.example.sisdi_users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.MalformedURLException;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Object Already Exists")
public class DuplicatedDataException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    public DuplicatedDataException(final String string) {
        super(string);
    }

    public DuplicatedDataException(final String string, final MalformedURLException ex) {
        super(string, ex);
    }

    public DuplicatedDataException(final Class<?> clazz, final long id) {
        super(String.format("Entity %s with id %d already exists", clazz.getSimpleName(), id));
    }

    public DuplicatedDataException(final Class<?> clazz, final String id) {
        super(String.format("Entity %s with id %s already exists", clazz.getSimpleName(), id));
    }
}
