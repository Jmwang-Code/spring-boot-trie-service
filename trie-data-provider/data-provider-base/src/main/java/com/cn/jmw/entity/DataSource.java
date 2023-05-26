package com.cn.jmw.entity;

import lombok.Data;

import java.util.List;

/**
 * 数据源实体类。
 */
@Data
public class DataSource {
  /**
     * 数据源类型。
     */
    private String type;

    /**
     * 数据库驱动类名。
     */
    private String driverClassName;

    /**
     * 数据库连接字符串。
     */
    private String url;

    /**
     * 数据库用户名。
     */
    private String username;

    /**
     * 数据库密码。
     */
    private String password;

    /**
     * 是否使用SQL语句。
     */
    private boolean useSql = true;

    /**
     * SQL代码列表。
     */
    private List<SqlCode> sqlCode;

    /**
     * 是否使用SQL生成器。
     */
    private boolean useSqlGenerator = false;
    private List<ProviderEntity.SqlQenerator> sqlQenerators;
    /**
     * 最大等待时间（毫秒）。
     */
    private int maxWaitMillis = 60 * 1000;

    /**
     * 数据加载类型。
     */
    private LoadOn loadOn;
}
