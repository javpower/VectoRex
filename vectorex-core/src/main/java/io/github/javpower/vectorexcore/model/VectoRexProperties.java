package io.github.javpower.vectorexcore.model;

import lombok.Data;

import java.util.List;

/**
 * @author xgc
 **/
@Data
public class VectoRexProperties {
    private boolean enable;
    private String uri;

    private List<String> packages;
}