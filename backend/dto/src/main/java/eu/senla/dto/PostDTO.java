package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO implements EntityDTO {

    private String header;
    private String text;
    private String userName;
    private String userSurname;

}
