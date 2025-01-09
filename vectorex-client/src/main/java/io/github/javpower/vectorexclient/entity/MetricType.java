package io.github.javpower.vectorexclient.entity;

/**
 * 枚举类，用于表示不同的距离函数。
 * 每个枚举常量对应一个具体的距离函数实例。
 */
public enum MetricType {

    /**
     * 浮点数的余弦距离函数.
     * 余弦距离用于衡量两个向量之间的相似度，值越小表示越相似.
     */
    FLOAT_COSINE_DISTANCE(),

    /**
     * 浮点数的内积函数.
     * 内积用于计算两个向量的点积，结果是一个标量值.
     */
    FLOAT_INNER_PRODUCT(),

    /**
     * 浮点数的欧几里得距离函数.
     * 欧几里得距离是最常用的距离度量，用于计算两个向量之间的直线距离.
     */
    FLOAT_EUCLIDEAN_DISTANCE(),

    /**
     * 浮点数的堪培拉距离函数.
     * 堪培拉距离用于计算两个向量之间的相对距离，适用于稀疏数据集.
     */
    FLOAT_CANBERRA_DISTANCE(),

    /**
     * 浮点数的布雷-柯蒂斯距离函数.
     * 布雷-柯蒂斯距离用于计算两个向量之间的相对差异，适用于生态学等领域.
     */
    FLOAT_BRAY_CURTIS_DISTANCE(),

    /**
     * 浮点数的相关距离函数.
     * 相关距离用于衡量两个向量之间的相关性，值越小表示相关性越高.
     */
    FLOAT_CORRELATION_DISTANCE(),

    /**
     * 浮点数的曼哈顿距离函数.
     * 曼哈顿距离用于计算两个向量之间的路径距离，适用于城市规划等领域.
     */
    FLOAT_MANHATTAN_DISTANCE(),

}