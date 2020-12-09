package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityDTO implements EntityDTO {
    private int id;
    private String name;
    private String topic;
}
