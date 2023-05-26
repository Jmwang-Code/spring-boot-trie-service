package com.cn.jmw.base;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jmw
 * @Description 在QueryRunner基础上，设置了ResultSet的一些参数，防止OOM，提高性能batch
 * @date 2023年04月05日 17:59
 * @Version 1.0
 */
public class JdbcSqlQueryRunner extends QueryRunner {

    /**
     * 准备SQL语句并返回PreparedStatement对象。
     * @param conn 数据库连接
     * @param sql SQL语句
     * @return PreparedStatement对象
     * @throws SQLException 如果SQL语句无效或连接已关闭，则抛出SQLException异常
     */
    @Override
    protected PreparedStatement prepareStatement(Connection conn, String sql)
            throws SQLException {
        // 创建PreparedStatement对象，并设置ResultSet的类型、并发性和关闭方式
        // ResultSet.TYPE_FORWARD_ONLY：游标只能在结果集中向前移动
        // ResultSet.CONCUR_READ_ONLY：创建只读结果集，这是默认值
        // ResultSet.CLOSE_CURSORS_AT_COMMIT：需要配合事务使用，在提交事务的时候，会自动关闭当前事务创建的ResultSet
        PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
        // 设置ResultSet的Fetch Size，即一次从数据库查找的记录数
        // 这里设置为1000，可以根据实际情况进行调整，需要防止OOM
        ps.setFetchSize(1000);
        // 设置ResultSet的Fetch Direction，即结果集的遍历方向
        // ResultSet.FETCH_FORWARD：从头到尾正向遍历结果集
        ps.setFetchDirection(ResultSet.FETCH_FORWARD);
        return ps;
    }
}
