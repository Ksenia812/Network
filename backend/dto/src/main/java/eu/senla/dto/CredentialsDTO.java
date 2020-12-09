package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO implements EntityDTO {
    private int id;
    private String login;
    private String password;
}
