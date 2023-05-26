package com.cn.jmw.adapter;

import com.cn.jmw.base.Counter;
import com.cn.jmw.base.JdbcDataSource;
import com.cn.jmw.base.JdbcSqlQueryRunner;
import com.cn.jmw.color.ThreadColor;
import com.cn.jmw.driver.DefaultNonRelationalDataBaseDriver;
import com.cn.jmw.entity.DataSource;
import com.cn.jmw.result.TrieResultSetHandler;
import com.cn.jmw.trie.Trie;
import com.cn.jmw.trie.entity.MultiCodeMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;


/**
 * @author jmw
 * @Description 使用次类以及子类  可以通过try 自动关闭资源.
 * JDBC适配器，用于从JDBC数据源中读取数据。
 * @param <Result> 要读取的数据类型。
 * @date 2023年04月10日 18:01
 * @Version 1.0
 */
@Slf4j
public class JdbcAdapter implements Adapter<Boolean> {

    protected final static String DUAL = "select 1 from dual";

    protected DataSource dataSource;

    protected Connection connection;

    protected QueryRunner queryRunner;

    protected Trie<Object,Object> trieNode;

    /**
     * 构造函数，初始化适配器。
     * @param dataSource 数据源
     * @param trieNode Trie树节点
     */
    public JdbcAdapter(DataSource dataSource, Trie<Object,Object> trieNode) {
        init(dataSource,trieNode);
    }


    /**
     * 初始化适配器。
     * @param dataSource 数据源
     * @param trieNode Trie树节点
     */
    public void init(DataSource dataSource, Trie<Object,Object> trieNode) {
        try {
            if (!DefaultNonRelationalDataBaseDriver.class.getName().equals(dataSource.getDriverClassName())) {
                Class.forName(dataSource.getDriverClassName());
                this.queryRunner = new JdbcSqlQueryRunner();
                this.connection = JdbcDataSource.getConnection(dataSource.getDriverClassName(), dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
            }
            this.dataSource = dataSource;
            this.trieNode = trieNode;
        } catch (Exception e) {
            log.error(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName() + "初始化数据源失败"), e);
        }
    }

    /**
     * 测试JDBC连接是否正常。
     * @return 如果连接正常，则为true，否则为false。
     */
    @Override
    public boolean test() {
        return connection != null;
    }

    /**
     * 从JDBC数据源中读取数据。
     * @return 读取的数据。
     */
    @Override
    public Boolean streamingRead() {
        Counter counter = new Counter();
        dataSource.getSqlCode().parallelStream().forEach(sqlCode -> {
            try {
                Long query = queryRunner.query(connection, sqlCode.getSql(), new TrieResultSetHandler() {
                    @Override
                    public Long handle(ResultSet rs) throws SQLException {
                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        while (rs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                if (rs.getString(i)!=null){
                                    trieNode.add(rs.getString(i), MultiCodeMode.Drop);
                                    log.info(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName() + "——" + rs.getString(i)));
                                }
                            }
                            counter.inc();
                        }
                        return counter.value();
                    }
                });

                log.info(ThreadColor.getColor8(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName()+"——"+sqlCode.getSql()+"——单词加载树量"+query));
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName() + "数据源流接入失败:") + dataSource.getDriverClassName(), e);
            }
        });
        return true;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(ThreadColor.getColor256(Thread.currentThread().getName()).getColoredString(Thread.currentThread().getName() + "关闭连接失败:") + dataSource.getDriverClassName(), e);
            }
        }
    }
}
