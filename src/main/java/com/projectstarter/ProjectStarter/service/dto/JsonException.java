package com.projectstarter.ProjectStarter.service.dto;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
public class JsonException extends RuntimeException {

    public JsonException(final String message) {
        super(message);
    }

    public JsonException(final String message, Throwable exception) {
        super(message, exception);
    }
}
