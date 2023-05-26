package com.cn.jmw;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年04月11日 16:17
 * @Version 1.0
 */
public enum AdapterEnum {

    JDBC("com.cn.jmw.adapter.JdbcAdapter"), // JDBC适配器
    MYSQL("com.cn.jmw.adapter.MysqlAdapter"), // MySQL适配器
    ORACLE, // Oracle适配器
    SQLSERVER, // SQL Server适配器
//    HIVE, // Hive适配器
//    HBASE, // HBase适配器
//    HDFS, // HDFS适配器
//    KAFKA, // Kafka适配器
//    REDIS, // Redis适配器
//    ES, // Elasticsearch适配器
//    SOLR, // Solr适配器
    SQLITE, // SQLite适配器
    H2, // H2适配器
    MONGODB("com.cn.jmw.adapter.MongoDbAdapter"); // MongoDB适配器

    public String className; // 适配器类名

    /**
     * 获取适配器类名。
     * @return 适配器类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 构造函数，创建适配器枚举。
     */
    AdapterEnum(){

    }

    /**
     * 构造函数，创建适配器枚举。
     * @param className 适配器类名
     */
    AdapterEnum(String className){
        this.className = className;
    }

    /**
     * 根据适配器名称获取适配器枚举。
     * @param name 适配器名称
     * @return 适配器枚举
     */
    public static AdapterEnum getAdapterEnum(String name){
        for (AdapterEnum adapterEnum : AdapterEnum.values()) {
            if (adapterEnum.name().equalsIgnoreCase(name)) {
                return adapterEnum;
            }
        }
        return null;
    }
}
