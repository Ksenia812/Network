package eu.senla.dto;

import lombok.Getter;

@Getter
public enum SortDirection {

    ASC("asc"),
    DESC("desc");

    private final String sortDirection;

    SortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
