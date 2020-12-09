package eu.senla.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityNotUniqueException extends RuntimeException{

    public static String errorParameter;
    public static Class<?> clazz;

    public EntityNotUniqueException(Class<?> entityClass, String parameter) {
        clazz = entityClass;
        errorParameter = parameter;
        log.error("{} has not unique {}", entityClass, parameter);
    }
}
