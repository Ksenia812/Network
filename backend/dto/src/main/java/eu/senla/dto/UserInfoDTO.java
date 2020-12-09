package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class UserInfoDTO implements EntityDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "asdasd")
    private String email;

    public UserInfoDTO() {
    }

    public UserInfoDTO(int id) {
        this.id = id;
    }

    public String getBirthday() {
        return dateFormat.format(birthday);
    }
}
