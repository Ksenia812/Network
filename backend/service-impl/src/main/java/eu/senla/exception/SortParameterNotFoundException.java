package eu.senla.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SortParameterNotFoundException extends RuntimeException {

    public SortParameterNotFoundException(String sortParameter) {
        log.error("Sort parameter {} is not found", sortParameter);
    }
}
