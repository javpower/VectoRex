package io.github.javpower.vectorexclient.res;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class DbData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Map<String,Object> metadata;

}