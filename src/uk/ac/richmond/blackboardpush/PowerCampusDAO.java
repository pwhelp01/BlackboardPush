/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author pete
 */
public class PowerCampusDAO {
    
    String dbServer;
    String dbPort;
    String dbName;
    String username;
    String password;
    Connection connection;
    
    public PowerCampusDAO(String dbServer, String dbPort, String dbName) {
        this.dbServer = dbServer;
        this.dbPort = dbPort;
        this.dbName = dbName;
    }
    
    public void createConnection(String username, String password) throws SQLException {
        
        String connectionString = "jdbc:jtds:sqlserver://"
            +this.dbServer
            +":"
            +this.dbPort
            +";DatabaseName="
            +this.dbName;
        
        try {
            this.connection = DriverManager.getConnection(connectionString, username, password);
        }
        catch (SQLException e) {
            throw e;
        }
   
    }
    
    
    public void closeConnection() {
        
        try {
            this.connection.close();
        }
        catch (SQLException e) {
            
        }
    }
    
}
