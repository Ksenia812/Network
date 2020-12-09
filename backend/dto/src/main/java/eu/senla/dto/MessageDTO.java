package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class MessageDTO implements EntityDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Integer id;
    @NotNull
    private String text;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    @NotNull
    private Integer userId;

    public String getDate() {
        if (date != null) {
            return dateFormat.format(date);
        }
        return dateFormat.format(new Date());
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
