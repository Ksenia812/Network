package eu.senla.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SortDto {
    private String sortParameter;
    private SortDirection sortDirection;

    @Override
    public boolean equals(Object obj) {
        return this.sortDirection.equals(obj);
    }
}
