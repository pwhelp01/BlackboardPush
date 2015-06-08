/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author pete
 */
public class LoginController implements Initializable {
    
    @FXML private TextField txtUsername;
    @FXML private Button btnLogin;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;
    
    private ApplicationProperties props;
    private PowerCampusDAO pc;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogin.setOnAction(this::btnLoginAction);
    }    
    
    private void btnLoginAction(ActionEvent event) {
        
        // Get username and passord from 
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        getApplicationProperties();
        
        try{ 
            // authenticate(username, password);
            loadMainScene((Stage) this.btnLogin.getScene().getWindow(), this.pc, username, password);
        }
        /* catch (SQLException e) {
            this.lblError.setVisible(true);
        } */
        catch (IOException e) {
            
        }
    }
    
    private void getApplicationProperties() {
        try {
            this.props = new ApplicationProperties();
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
    
    private void authenticate(String username, String password) throws SQLException {
        this.pc = new PowerCampusDAO(this.props.getDbserver(), this.props.getDbport(), this.props.getDbname());
        pc.createConnection(username, password);
        pc.closeConnection();
    }
    
    private void loadMainScene(Stage stage, PowerCampusDAO pc, String username, String password) throws IOException {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = (Parent) fxmlLoader.load(); 
            
            MainController controller = fxmlLoader.<MainController>getController();
            
            controller.setPc(pc);
            controller.setUsername(username);
            controller.setPassword(password);
            controller.initData();
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        }
        catch (IOException e) {
            throw e;
        }
    }
}
