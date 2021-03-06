/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import model.config.ConnexionConfig;
import model.config.DatabaseConfig;
import utils.StringUtils;

/**
 *
 * @author tkint
 */
public class Connexion {

    private static Connexion instance;
    private Connection connection = null;
    private Statement statement = null;

    private Connexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            ConnexionConfig connexion = ConnexionConfig.getInstance();

            String url = connexion.getUrl();
            String port = connexion.getPort();
            String login = connexion.getLogin();
            String password = connexion.getPassword();
            String database = connexion.getDatabase();

            String jdbcUrl = "jdbc:mysql://" + url + ":" + port + "/" + database;

            connection = DriverManager.getConnection(jdbcUrl, login, password);

            statement = connection.createStatement();

        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        return statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public static String getTableName(Class c) {
        String tableName = null;
        try {
            DatabaseConfig config = DatabaseConfig.getInstance();
            
            String methodName = "get" + c.getSimpleName().substring(0, c.getSimpleName().length() - 3);
            
            Method method = config.getClass().getMethod(methodName);
            
            //Method method = config.getClass().getMethod("get" + StringUtils.toUpperFirst(table));
            
            tableName = method.invoke(config).toString();

        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tableName;
    }
    
    public static String getTableName(String table) {
        String tableName = null;
        try {
            DatabaseConfig config = DatabaseConfig.getInstance();
            
            Method method = config.getClass().getMethod("get" + StringUtils.toUpperFirst(table));
            
            tableName = method.invoke(config).toString();

        } catch (Exception ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tableName;
    }

    public static Connexion getInstance() {
        if (instance == null) {
            instance = new Connexion();
        }
        return instance;
    }
}
