package com.cn.jmw.driver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cn.jmw.base.NonRelationalDataBaseConnection;
import com.cn.jmw.color.ThreadColor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jmw
 * @Description 默认非关系型数据库驱动类。
 * @date 2023年05月06日 14:35
 * @Version 1.0
 */
@Slf4j
public abstract class DefaultNonRelationalDataBaseDriver {

    private Map<String, NonRelationalDataBaseConnection> connections = new HashMap<>();


    /**
     * 构造函数，创建默认非关系型数据库驱动。
     */
    public DefaultNonRelationalDataBaseDriver() {
        log.info(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName() + "——创建默认非关系型数据库驱动"));
    }

    /**
     * 获取默认非关系型数据库连接。
     * @return 默认非关系型数据库连接
     */
    public NonRelationalDataBaseConnection getDefaultConnection() {
        return getConnection("default");
    }

    /**
     * 获取指定名称的非关系型数据库连接。
     * @param name 数据库连接名称
     * @return 指定名称的非关系型数据库连接
     */
    public NonRelationalDataBaseConnection getConnection(String name) {
        NonRelationalDataBaseConnection connection = connections.get(name);
        if (connection != null) {
            connection = createConnection(name, connection);
        } else {
            connection = createConnection(name, connection);
            connections.put(name, connection);
        }
        return connection;
    }

        /**
     * 创建指定名称的非关系型数据库连接。
     * @param name 数据库连接名称
     * @return 指定名称的非关系型数据库连接
     */
    public abstract NonRelationalDataBaseConnection createConnection(String name,NonRelationalDataBaseConnection connection);

    /**
     * 关闭指定名称的非关系型数据库连接。
     * @param name 数据库连接名称
     */
    public void closeConnection(String name) throws IOException {
        NonRelationalDataBaseConnection connection = connections.get(name);
        if (connection != null) {
            connection.close();
            connections.remove(name);
        }
    }

    /**
     * 关闭所有非关系型数据库连接。
     */
    public void closeAllConnections() throws IOException {
        for (String name : connections.keySet()) {
            closeConnection(name);
        }
    }
}
