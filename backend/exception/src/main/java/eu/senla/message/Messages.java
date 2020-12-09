package eu.senla.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Messages {

    @Autowired
    private MessageSource messageSource;
    private MessageSourceAccessor accessor;

    @PostConstruct
    public void setupMessages() {
        accessor = new MessageSourceAccessor(messageSource);
    }

    public String get(String key) {
        return accessor.getMessage(key);
    }

}