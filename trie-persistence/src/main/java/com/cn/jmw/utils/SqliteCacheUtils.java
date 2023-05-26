package com.cn.jmw.utils;

import com.cn.jmw.pojo.NodeTable;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.File;
import java.sql.*;
import java.util.List;

/**
 * @author jmw
 * @Description Utility class for working with SQLite database.
 * @date 2023年04月21日 17:04
 * @Version 1.0
 */
public class SqliteCacheUtils implements AutoCloseable{

    // Constants for SQLite driver and URL
    protected static final String SQLITE_DRIVER_FILE = "org.sqlite.JDBC";
    protected static final String SQLITE_URL_FILE = "jdbc:sqlite:";

        // SQL statements
    protected static final String DATA_TABLE =
            "        CREATE TABLE IF NOT EXISTS DATA_SQLITE (\n" +
            "                ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "                NAME VARCHAR(200) NOT NULL,\n" +
            "                CODE INTEGER NOT NULL,\n" +
            "                TYPE INTEGER NOT NULL,\n" +
            "                DELETED INT NOT NULL DEFAULT 0\n" +
            "        );";

    protected static final String CHECK_EXISTS_SQL =
            "SELECT EXISTS(SELECT name from sqlite_master where type = 'table' and name = 'DATA_SQLITE')";

    protected static final String QUERY_SQL =
            "    SELECT NAME, CODE, TYPE FROM DATA_SQLITE WHERE DELETED = 0";

    protected static final String INSERT_SQL =
            "        INSERT INTO DATA_SQLITE ( NAME, CODE, TYPE) VALUES ( ?, ?, ?)";

    protected static final String UPDATE_SQL =
            "        UPDATE DATA_SQLITE SET NAME = ?, CODE = ?, TYPE = ? WHERE ID = ?";

    protected static final String DELETE_SQL =
            "        UPDATE DATA_SQLITE SET DELETED = 1 WHERE NAME = ? AND CODE = ? AND TYPE = ?";

    // Flag to check if database exists
    public static boolean dbExist;

    // Connection and QueryRunner objects
    protected Connection connection;
    protected QueryRunner queryRunner;

    /**
     * Constructor that initializes the connection and creates the table if it doesn't exist.
     * @param path The path to the SQLite database file.
     */
    public SqliteCacheUtils(String path){
        init(path);
    }

    /**
     * Initializes the connection and creates the table if it doesn't exist.
     * @param path The path to the SQLite database file.
     */
    public void init(String path){
        File file = new File(path);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        connection = getConnection(path);
        queryRunner = new QueryRunner();
        tableExist();
        if(!dbExist){
            createTable();
        }
    }

    /**
     * Gets a connection to the SQLite database.
     * @param sqliteFilePath The path to the SQLite database file.
     * @return A Connection object.
     */
    public Connection getConnection(String sqliteFilePath) {
        Connection conn;
        try {
            Class.forName(SQLITE_DRIVER_FILE);
            conn = DriverManager.getConnection(SQLITE_URL_FILE + sqliteFilePath);
            conn.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * Checks if the table exists in the database.
     * @return True if the table exists, false otherwise.
     */
    public boolean tableExist(){
        boolean query;
        try {
            query = queryRunner.query(connection, CHECK_EXISTS_SQL, new ScalarHandler<Boolean>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dbExist = query;
        return query;
    }

    /**
     * Creates the table in the database.
     */
    public boolean createTable(){
        int update;
        try {
            update = queryRunner.update(connection, DATA_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return update>0;
    }

    /**
     * Inserts a row into the database.
     * @param name The name of the row.
     * @param code The code of the row.
     * @param type The type of the row.
     * @return The number of rows affected.
     */
    public int insert(String name, int code, int type){
        int update;
        try {
            update = queryRunner.update(connection, INSERT_SQL,name, code,type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return update;
    }


    /**
     * Deletes a row from the database.
     * @param name The name of the row.
     * @param code The code of the row.
     * @param type The type of the row.
     * @return True if the row was deleted, false otherwise.
     */
    public boolean delete(String name, int code, int type){
        int update;
        try {
            update = queryRunner.update(connection, DELETE_SQL, name, code,type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return update > 0;
    }

    /**
     * Queries the database for rows.
     * @param name The name of the row.
     * @param code The code of the row.
     * @param type The type of the row.
     * @return A list of NodeTable objects.
     */
    public List<NodeTable> query(String name, int code, int type){
        List<NodeTable> query = null;
        try {
            query = queryRunner.query(connection, QUERY_SQL, new BeanListHandler<NodeTable>(NodeTable.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    @Override
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
