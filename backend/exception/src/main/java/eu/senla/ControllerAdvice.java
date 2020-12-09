package eu.senla;

import com.fasterxml.jackson.databind.JsonMappingException;
import eu.senla.dto.MessageErrorDTO;
import eu.senla.exception.EntityNotFoundException;
import eu.senla.exception.SaveEntityException;
import eu.senla.exception.SortParameterNotFoundException;
import eu.senla.exception.UpdateInfoException;
import eu.senla.exception.EntityNotUniqueException;
import eu.senla.message.MessageBuilder;
import eu.senla.security.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;



@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageBuilder messageBuilder;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public MessageErrorDTO handleEntityNotFoundException() {
        return messageBuilder.build("entity.not.found.exception", EntityNotFoundException.clazz, EntityNotFoundException.nameParameter,
                EntityNotFoundException.parameterValue);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SaveEntityException.class})
    public MessageErrorDTO handleSaveEntityException() {
        return messageBuilder.build("save.entity.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UpdateInfoException.class})
    public MessageErrorDTO handleUpdateInfoException() {
        return messageBuilder.build("update.info.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SQLGrammarException.class})
    public MessageErrorDTO handleSQLGrammarException() {
        return messageBuilder.build("sql.grammar.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NullPointerException.class})
    public MessageErrorDTO handleNullPointerException() {
        return messageBuilder.build("null.pointer.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({JsonMappingException.class})
    public MessageErrorDTO handleJsonMappingException() {
        return messageBuilder.build("json.mapping.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({JwtAuthenticationException.class})
    public MessageErrorDTO handleJwtAuthenticationException() {
        return messageBuilder.build("jwt.authentication.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ExpiredJwtException.class})
    public MessageErrorDTO handleExpiredJwtException() {
        return messageBuilder.build("jwt.expiration.exception");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AuthenticationException.class})
    public MessageErrorDTO handleAuthenticationException() {
        return messageBuilder.build("authentication.exception");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EntityNotUniqueException.class})
    public MessageErrorDTO handleEntityNotUniqueException() {
        return messageBuilder.build("entity.not.unique.exception", EntityNotUniqueException.clazz, EntityNotUniqueException.errorParameter);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SortParameterNotFoundException.class})
    public MessageErrorDTO handleSortParameterNotFoundException() {
        return messageBuilder.build("sort.parameter.not.found.exception", AbstractDao.errorParameter);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        MessageErrorDTO errorDto = messageBuilder.build("not.valid.arguments.exception");
        return new ResponseEntity<>(errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
