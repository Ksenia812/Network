package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class PostInfoDTO implements EntityDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Integer id;
    private String header;
    private String text;
    private Date date;

    public String getDate() {
        return dateFormat.format(date);
    }
}
