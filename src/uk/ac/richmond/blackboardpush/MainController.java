/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    PowerCampusDAO pc;
    String username;
    String password;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
