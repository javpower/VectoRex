package io.github.javpower.vectorextest.model;

import io.github.javpower.vectorex.keynote.model.MetricType;
import io.github.javpower.vectorexcore.annotation.VectoRexCollection;
import io.github.javpower.vectorexcore.annotation.VectoRexField;
import io.github.javpower.vectorexcore.entity.DataType;
import lombok.Data;

import java.util.List;

@Data
@VectoRexCollection(name = "face")
public class Face {
    @VectoRexField(
            isPrimaryKey = true
    )
    private Long id;
    @VectoRexField
    private String name;

    @VectoRexField(
            dataType = DataType.FloatVector,
            dimension = 128,
            metricType = MetricType.FLOAT_COSINE_DISTANCE
    )
    private List<Float> vector;
}
