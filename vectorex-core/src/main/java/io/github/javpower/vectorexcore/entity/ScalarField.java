package io.github.javpower.vectorexcore.entity;

import lombok.Data;

@Data
public class ScalarField {
    private String name;
    private Boolean isPrimaryKey;
    private Boolean autoID;
}
