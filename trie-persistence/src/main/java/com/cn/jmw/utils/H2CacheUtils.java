package com.cn.jmw.utils;

import com.cn.jmw.pojo.NodeTable;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author jmw
 * @Description Utility class for working with H2 database.
 * @date 2023年04月27日 11:21
 * @Version 1.0
 */
public class H2CacheUtils implements AutoCloseable{

        // Constants for H2 driver and URL
    protected static final String H2_DRIVER_FILE = "org.h2.Driver";
    protected static final String H2_URL_FILE = "jdbc:h2:file:";

    // SQL statements
    protected static final String DATA_TABLE =
            "CREATE TABLE IF NOT EXISTS DATA_H2 (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
            "NAME VARCHAR NOT NULL," +
            "CODE INTEGER NOT NULL," +
            "TYPE INTEGER NOT NULL," +
            "DELETED INT NOT NULL DEFAULT 0);";


    protected static final String CHECK_EXISTS_SQL =
             "SELECT EXISTS(SELECT table_name from information_schema.tables where table_name = 'DATA_H2')";

    protected static final String QUERY_SQL =
            "SELECT ID, NAME, CODE, TYPE FROM DATA_H2 WHERE DELETED = 0";

    protected static final String INSERT_SQL =
            "INSERT INTO DATA_H2 (NAME, CODE, TYPE) VALUES (?, ?, ?)";

    protected static final String UPDATE_SQL =
            "UPDATE DATA_H2 SET NAME = ?, CODE = ?, TYPE = ? WHERE ID = ?";

    protected static final String DELETE_SQL =
            "UPDATE DATA_H2 SET DELETED = 1 WHERE NAME = ? AND CODE = ? AND TYPE = ?";

    // Flag to check if database exists
    public static boolean dbExist;

    // Connection and QueryRunner objects
    protected Connection connection;
    protected QueryRunner queryRunner;

    /**
     * Constructor that initializes the connection and creates the table if it doesn't exist.
     * @param path The path to the H2 database file.
     */
    public H2CacheUtils(String path){
        init(path);
    }

    /**
     * Initializes the connection and creates the table if it doesn't exist.
     * @param path The path to the H2 database file.
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
     * Gets a connection to the H2 database.
     * @param sqliteFilePath The path to the H2 database file.
     * @return A Connection object.
     */
    public Connection getConnection(String sqliteFilePath) {
        Connection conn;
        try {
            Class.forName(H2_DRIVER_FILE);
            conn = DriverManager.getConnection(H2_URL_FILE + sqliteFilePath);
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
    public void createTable(){
        try {
            queryRunner.update(connection, DATA_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            update = queryRunner.update(connection, INSERT_SQL, name, code, type);
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
            update = queryRunner.update(connection,DELETE_SQL , name, code,type);
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
            query = queryRunner.query(connection, QUERY_SQL ,new BeanListHandler<NodeTable>(NodeTable.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    /**
     * Closes the connection to the database.
     */
    @Override
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
