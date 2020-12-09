package eu.senla.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UpdateInfoException extends RuntimeException {
    private static final String MESSAGE = "Your entity can't be updated.Perhaps one of your fields is null";

    public UpdateInfoException() {
        log.error(MESSAGE);
    }
}
