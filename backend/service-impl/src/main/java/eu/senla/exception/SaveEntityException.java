package eu.senla.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SaveEntityException extends RuntimeException {
    private static final String MESSAGE = "Can't save entity.";

    public SaveEntityException() {
        log.error(MESSAGE);
    }
}
