/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *
 * @author pete
 */
public class ApplicationProperties {
    
    private String dbserver;
    private String dbport;
    private String dbname;
    
    public ApplicationProperties() throws Exception {
        
        Properties props = new Properties();
        
        try {
            props.load(new FileInputStream("blackboardpush.properties"));
            this.dbserver = props.getProperty("dbserver");
            this.dbport = props.getProperty("dbport");
            this.dbname = props.getProperty("dbname");
        }
        catch (IOException e) {
            throw e;
        }
    }
    
    public String getDbserver() {
        return this.dbserver;
    }
    
    public String getDbport() {
        return this.dbport;
    }
    
    public String getDbname() {
        return this.dbname;
    }
    
}
