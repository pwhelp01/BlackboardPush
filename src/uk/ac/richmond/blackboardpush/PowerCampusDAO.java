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
    
    /**
     * Create a connection to the PowerCAMPUS database
     * @param username Username
     * @param password Password
     * @throws SQLException Connection error due to incorrect credentials or connection problem
     */
    public void createConnection(String username, String password) throws SQLException {
        
        // Build connection string
        String connectionString = "jdbc:jtds:sqlserver://"
            +this.dbServer
            +":"
            +this.dbPort
            +";DatabaseName="
            +this.dbName;
        
        // Create connection
        this.connection = DriverManager.getConnection(connectionString, username, password);
   
    }
    
    /**
     * Close connection to the PowerCAMPUS database
     */
    public void closeConnection() {
        
        try {
            this.connection.close();
        }
        catch (SQLException e) {
        }
    }
    
    /**
     * Check if a People ID is in the PERSONUSER table
     * @param peopleId People ID of person to check
     * @return True if the People ID is in the PERSONUSER table
     * @throws SQLException 
     */
    public boolean isInPersonUser(String peopleId) throws SQLException {
        
        // Create PeopleCodeID
        String peopleCodeId = "P" + peopleId;
        
        // Prepare statement for RPC
        CallableStatement cs = this.connection.prepareCall("{call MIS_spIsInPersonUser(?)}");
        cs.setString(1, peopleCodeId);
        
        // Excute RPC and get results
        ResultSet result = cs.executeQuery();
        
        // Concert SQL Server BIT datatype in result to Java Boolean  
        boolean rs = false;
        
        while(result.next()) {
            
            rs = (boolean) result.getBoolean(1);
            
        }
        
        // Tidy up connections
        result.close();
        cs.close();
        
        // Return result
        return rs;
        
    }
    
    /**
     * Push a person from PowerCAMPUS into Blackboard
     * 
     * @param peopleId People ID of the person to push into Blackboard
     * @return Output of the push operation
     * @throws SQLException Error occurring at database tier
     */
    public String forcePerson(String peopleId) throws SQLException {
        
        // Create PeopleCodeID
        String peopleCodeId = "P" + peopleId;
        
        // Prepare statement for RPC
        CallableStatement cs = this.connection.prepareCall("{call MIS_splmsforcepeople(?)}");
        cs.setString(1, peopleCodeId);
        
        // Excute RPC and get results
        ResultSet result = cs.executeQuery();
        String output = "";
        
        // Get string representation of result from resultset
        while(result.next()) {
            output = result.getString(1);
        }
        
        // Tidy up connection
        result.close();
        cs.close();
        
        // Return output
        return output;
      
    }
}
