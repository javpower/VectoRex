package io.github.javpower.vectorexcore.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScalarField implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Boolean isPrimaryKey;
}
