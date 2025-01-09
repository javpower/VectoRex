package io.github.javpower.vectorexclient.res;

import lombok.Data;

@Data
public class VectorSearchResult {
    private String id;
    private Float score;
    private DbData data;

}