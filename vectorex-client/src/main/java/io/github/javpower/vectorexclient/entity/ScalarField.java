package io.github.javpower.vectorexclient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScalarField implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Boolean isPrimaryKey;
}
