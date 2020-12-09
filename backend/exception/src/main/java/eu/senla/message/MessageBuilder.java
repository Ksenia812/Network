package eu.senla.message;

import eu.senla.dto.MessageDTO;
import eu.senla.dto.MessageErrorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Builder class for messages
 */
@Component
@RequiredArgsConstructor
public class MessageBuilder {

    private final Messages messages;

    public MessageErrorDTO build(String key) {
        String message = messages.get(key);
        return getMessageErrorDTO(key, message);
    }

    public MessageErrorDTO build(String key, String parameter) {
        String message = parameter + " " + messages.get(key);
        return getMessageErrorDTO(key, message);
    }

    public MessageErrorDTO build(String key, Class<?> clazz, String parameter) {
        String message = clazz.getSimpleName() + " " + messages.get(key) + " " + parameter;
        return getMessageErrorDTO(key, message);
    }

    public MessageErrorDTO build(String key, Class<?> clazz, String parameter, Object value) {
        String message = clazz.getSimpleName() + " " + messages.get(key) + " " + parameter + " = " + value;
        return getMessageErrorDTO(key, message);
    }

    private MessageErrorDTO getMessageErrorDTO(String key, String message) {
        MessageErrorDTO dto = new MessageErrorDTO();
        dto.setMessage(message);
        dto.setKey(key);
        return dto;
    }
}
