/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author pete
 */
public class MainController implements Initializable {

    @FXML TextField txtPeopleId;
    @FXML TextArea areaOutput;
    @FXML Button btnProcess;
    
    PowerCampusDAO pc;
    ApplicationProperties props;
    String username;
    String password;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Attach event handler to Process button
        this.btnProcess.setOnAction(this::btnProcessAction);
        
        // Get PowerCAMPUS database properties
        getApplicationProperties();
        this.pc = new PowerCampusDAO(this.props.getDbserver(), this.props.getDbport(), this.props.getDbname());
    }    
    
    
    /**
     * Event handler for the Process button
     * @param event Button click event
     */
    private void btnProcessAction(ActionEvent event) {
        
        String userInput = this.txtPeopleId.getText().trim();
        
        // Check if user input is valid        
        if(!validateInput(userInput)) {
            this.areaOutput.setText("Not a valid People ID" 
                    + System.lineSeparator()
                    + "People IDs are nine digits (0-9) long");
            return;
        }
                
        
        try {
            // Check student is in PERSONUSER table
            pc.createConnection(this.username, this.password);
            
            if(!pc.isInPersonUser(userInput)) {
                this.areaOutput.setText("Student/Faculty not in PERSONUSER table");
                pc.closeConnection();
                return;
            }
            
            // Student is in PERSONUSER table, so force Blackboard update
            String result = pc.forcePerson(userInput);
            this.areaOutput.setText(result);
            
            // Close connection
            pc.closeConnection();
            
        }
        catch (SQLException e) {
            // Error occured in database connection.  Display error and tidy up
            this.areaOutput.setText("Error connecting to the PowerCampus database");
            pc.closeConnection();
        }

    }
     
    /**
     * Validate a user input for People ID prior to executing against the database
     * 
     * @param peopleId User input for People ID
     * @return True if People ID is in valid format (9 digits [0-9] long)
     */
    private boolean validateInput(String peopleId) {
        
        // Create regex for pattern matching
        Pattern testPattern = Pattern.compile("[0-9]{9}");
        Matcher testString = testPattern.matcher(peopleId);
        
        // Test regex for valid People ID and return result
        if(testString.matches()) {
            return true;
        }
        else {
            return false;
        }
       
    }
    
    /**
     * Get application properties from the properties file
     */
    private void getApplicationProperties() {
        try {
            this.props = new ApplicationProperties();
            System.out.println(this.props.toString());  // Debug
            System.out.println(this.props.getDbname()); // Debug
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load properties file");
            alert.showAndWait();
            System.exit(0);
        }
    }
    
    /* Getters and setters */
    /**
     * Set the database username 
     * @param username Database username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Set the database password
     * @param password Database password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Set the PowerCAMPUS DAO object
     * @param pc PowerCAMPUSDAO
     */
    public void setPc(PowerCampusDAO pc) {
        this.pc = pc;
    }
    
}
