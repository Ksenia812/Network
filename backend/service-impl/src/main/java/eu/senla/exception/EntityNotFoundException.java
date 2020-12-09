package eu.senla.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityNotFoundException extends RuntimeException {
    public static Class<?> clazz;
    public static Object parameterValue;
    public static String nameParameter;


    public EntityNotFoundException(Class<?> entityClass, String parameter, Object value) {
        clazz = entityClass;
        nameParameter = parameter;
        parameterValue = value;
    }
}
