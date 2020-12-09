package eu.senla.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityCannotSaveException extends RuntimeException {

    public EntityCannotSaveException(Class<?> clazz) {
        log.error("Cannot save entity of {}", clazz);
    }
}
