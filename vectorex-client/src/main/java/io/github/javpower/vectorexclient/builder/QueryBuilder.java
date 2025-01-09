package io.github.javpower.vectorexclient.builder;

import io.github.javpower.vectorexclient.req.CollectionDataPageReq;
import io.github.javpower.vectorexclient.req.ConditionFiledReq;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class QueryBuilder<T> {

    private String collectionName;
    private List<ConditionFiledReq> conditions = new ArrayList<>();
    private List<Float> vector;
    private String vectorFieldName;
    private Integer topK;
    private Integer pageIndex;
    private Integer pageSize;

    public QueryBuilder(String collectionName) {
        this.collectionName = collectionName;
    }
    public static QueryBuilder lambda(String collectionName){
        QueryBuilder builder = new QueryBuilder(collectionName);
        return builder;
    }

    public QueryBuilder<T> eq(String fieldName, Object value) {
        conditions.add(createCondition("eq", fieldName, value));
        return this;
    }

    public QueryBuilder<T> in(String fieldName, List<?> values) {
        conditions.add(createCondition("in", fieldName, values));
        return this;
    }

    public QueryBuilder<T> like(String fieldName, String value) {
        conditions.add(createCondition("like", fieldName, value));
        return this;
    }

    public QueryBuilder<T> between(String fieldName, Comparable start, Comparable end) {
        ConditionFiledReq condition = createCondition("between", fieldName, null);
        condition.setStart(start);
        condition.setEnd(end);
        conditions.add(condition);
        return this;
    }

    public QueryBuilder<T> gt(String fieldName, Comparable value) {
        conditions.add(createCondition("gt", fieldName, value));
        return this;
    }

    public QueryBuilder<T> lt(String fieldName, Comparable value) {
        conditions.add(createCondition("lt", fieldName, value));
        return this;
    }

    public QueryBuilder<T> ge(String fieldName, Comparable value) {
        conditions.add(createCondition("ge", fieldName, value));
        return this;
    }

    public QueryBuilder<T> le(String fieldName, Comparable value) {
        conditions.add(createCondition("le", fieldName, value));
        return this;
    }

    public QueryBuilder<T> and(QueryBuilder<T> other) {
        conditions.add(createNestedCondition("and", other.conditions));
        return this;
    }

    public QueryBuilder<T> or(QueryBuilder<T> other) {
        conditions.add(createNestedCondition("or", other.conditions));
        return this;
    }

    private ConditionFiledReq createCondition(String operator, String field, Object value) {
        ConditionFiledReq condition = new ConditionFiledReq();
        condition.setOperator(operator);
        condition.setField(field);
        condition.setValue(value);
        return condition;
    }


    public QueryBuilder<T> vector(String vectorFieldName, List<Float> vector) {
        this.vectorFieldName = vectorFieldName;
        this.vector = vector;
        return this;
    }

    public QueryBuilder<T> topK(Integer topK) {
        this.topK = topK;
        return this;
    }
    public QueryBuilder<T> page(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        return this;
    }
    public CollectionDataPageReq build() {
        CollectionDataPageReq req = new CollectionDataPageReq();
        req.setCollectionName(collectionName);
        req.setQuery(conditions);
        req.setVector(vector);
        req.setVectorFieldName(vectorFieldName);
        req.setTopK(topK);
        req.setPageIndex(pageIndex);
        req.setPageSize(pageSize);
        return req;
    }
    private ConditionFiledReq createNestedCondition(String operator, List<ConditionFiledReq> nestedConditions) {
        ConditionFiledReq condition = new ConditionFiledReq();
        condition.setOperator(operator);
        condition.setConditionFiledReq(nestedConditions);
        return condition;
    }
}