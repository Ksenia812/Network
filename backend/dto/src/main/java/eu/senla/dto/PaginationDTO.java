package eu.senla.dto;

import lombok.Data;
import lombok.Value;

import java.util.List;

//TODO rename
@Data
public class PaginationDTO {
    int elementNumber;
    int pageSize;
    List<SortDto> sortParameters;

    public PaginationDTO() {
    }

    public PaginationDTO(int elementNumber, int pageSize, List<SortDto> sortParameters) {
        this.elementNumber = elementNumber;
        this.pageSize = pageSize;
        this.sortParameters = sortParameters;
    }
}
