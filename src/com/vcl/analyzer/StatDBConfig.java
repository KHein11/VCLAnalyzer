/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

/**
 *
 * @author kyihein
 */
public class StatDBConfig {
    private static final StatDBConfig INSTANCE = new StatDBConfig();
    public static final String SQLITE_CLASS_NAME = "org.sqlite.JDBC";
    public static final String SQLITE_CONN_NAME = "jdbc:sqlite:";
    private String statDbName;
    private String connName;
   
    
    private StatDBConfig() {
        try {
            Class.forName(SQLITE_CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            System.err.println("Database error occured : " + ex.getMessage());
        }    
    }   
    
    public static StatDBConfig getInstance() {
        return INSTANCE;
    }
    
    /**
     * @return the connName
     */
    public String getConnName() {
        return connName;
    }             

    /*
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }*/

    /**
     * @return the statDb
     */
    public String getStatDbName() {
        return statDbName;
    }

    /**
     * @param statDb the statDb to set
     */
    public void setStatDbName(String statDbName) {
        this.statDbName = statDbName;
        connName = SQLITE_CONN_NAME + statDbName;
    }

}
