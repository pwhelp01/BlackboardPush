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
        this.btnProcess.setOnAction(this::btnProcessAction);
        getApplicationProperties();
        this.pc = new PowerCampusDAO(this.props.getDbserver(), this.props.getDbport(), this.props.getDbname());
    }    
    
    
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
            // Check student is in PersonUser
            pc.createConnection(this.username, this.password);
            
            if(!pc.isInPersonUser(userInput)) {
                this.areaOutput.setText("Student/Faculty not in PERSONUSER table");
                pc.closeConnection();
                return;
            }
            
            String result = pc.forcePerson(userInput);
            this.areaOutput.setText(result);
            
            pc.closeConnection();
            
        }
        catch (SQLException e) {
            this.areaOutput.setText("Error connecting to the PowerCampus database");
            pc.closeConnection();
        }

    }
        
    public boolean validateInput(String peopleId) {
        
        Pattern testPattern = Pattern.compile("[0-9]{9}");
        Matcher testString = testPattern.matcher(peopleId);
        
        if(testString.matches()) {
            return true;
        }
        else {
            return false;
        }
       
    }
    
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
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPc(PowerCampusDAO pc) {
        this.pc = pc;
    }
    
    public void initData() {
        this.areaOutput.setText(this.username + System.lineSeparator() + this.password);
        
    }

    
}
