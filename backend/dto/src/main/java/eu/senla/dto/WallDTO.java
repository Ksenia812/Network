package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class WallDTO implements EntityDTO {
    private int id;
    private Set<PostDTO> posts;
}
