package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class UserRegistrationDTO implements EntityDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String name;
    private String surname;
    private Date birthday;
    private CredentialsDTO credentialsDto;
    private String email;

    public String getBirthday() {
        return dateFormat.format(birthday);
    }
}
