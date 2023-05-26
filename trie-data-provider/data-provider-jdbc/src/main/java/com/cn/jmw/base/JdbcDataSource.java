package com.cn.jmw.base;

import com.cn.jmw.color.ThreadColor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Slf4j
/**
 * @author jmw
 * @Description JDBC数据源类，用于获取数据库连接。
 * @date 2023年04月05日 18:13
 * @Version 1.0
 */
public class JdbcDataSource {

    /**
     * 获取数据库连接。
     * @param driver 数据库驱动
     * @param url 数据库URL
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return 数据库连接
     */
    public static Connection getConnection(String driver, String url, String username, String password)  {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
            log.info(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName()+"——获取连接"));
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
