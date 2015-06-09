/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
            System.out.println(e.getMessage());
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
    
    
    public boolean isInPersonUser(String peopleId) throws SQLException {
        
        String peopleCodeId = "P" + peopleId;
        
        CallableStatement cs = this.connection.prepareCall("{call MIS_spIsInPersonUser(?)}");
        cs.setString(1, peopleCodeId);
        
        ResultSet result = cs.executeQuery();
        
        boolean rs = false;
        
        while(result.next()) {
            
            rs = (boolean) result.getBoolean(1);
            
        }
        
        result.close();
        cs.close();
        
        return rs;
        
    }
    
    
    public String forcePerson(String peopleId) throws SQLException {
        
        String peopleCodeId = "P" + peopleId;
        
        CallableStatement cs = this.connection.prepareCall("{call MIS_splmsforcepeople(?)}");
        cs.setString(1, peopleCodeId);
        
        ResultSet result = cs.executeQuery();
        String output = "";
        
        while(result.next()) {
            output = result.getString(1);
        }
        
        result.close();
        cs.close();
        
        return output;
      
    }
}
