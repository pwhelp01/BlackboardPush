/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.richmond.blackboardpush.*;

/**
 *
 * @author whelptp
 */
public class testIsInPersonUser {
    
    public testIsInPersonUser() {
    }
    
    @Test
    public void test() {
        
        // Get properties
        try {
            ApplicationProperties props = new ApplicationProperties();
            
            PowerCampusDAO pc = new PowerCampusDAO(props.getDbserver(), props.getDbport(), props.getDbname());
            
            pc.createConnection("sa", "r1ddl3r");
            boolean result = pc.isInPersonUser("cvzsP000084573");
            pc.closeConnection();
            
            System.out.println(result);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
        
        
    }
}
