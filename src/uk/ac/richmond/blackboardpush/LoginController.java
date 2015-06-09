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
    
    /**
     * Actions to perform when the Scene is loaded
     * @param url Not used
     * @param rb Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Attach event handler to Login button
        this.btnLogin.setOnAction(this::btnLoginAction);
    }    
    
    /**
     * Event handler for Login button
     * @param event Button click event
     */
    private void btnLoginAction(ActionEvent event) {
        
        // Get username and passord from fields on UI
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        // Get application properties from file
        getApplicationProperties();
        
        // Attempt to authenticate the username / password against the PowerCAMPUS database
        // and load main scene if 
        try{ 
            authenticate(username, password);
            loadMainScene((Stage) this.btnLogin.getScene().getWindow(), this.pc, username, password);
        }
        catch (SQLException e) {
            // Display error about connecting to the database
            this.lblError.setVisible(true);
        }
        catch (IOException e) {
            // Display error about loading Main scene and exit application
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load main scene");
            alert.showAndWait();
            System.exit(0);
        }
    }
    
    /**
     * Get the Application properties from the properties file
     */
    private void getApplicationProperties() {
        try {
            this.props = new ApplicationProperties();
        }
        catch (Exception e) {
            // Display error dialog and exit the program
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load properties file");
            alert.showAndWait();
            System.exit(0);
        }
    }
    
    /**
     * Authenticate a user against the database
     * 
     * @param username Database username
     * @param password Database password 
     * @throws SQLException User could not be authenticated due to incorrect credentials or database connection failing
     */
    private void authenticate(String username, String password) throws SQLException {
        this.pc = new PowerCampusDAO(this.props.getDbserver(), this.props.getDbport(), this.props.getDbname());
        pc.createConnection(username, password);
        pc.closeConnection();
    }
    
    /**
     * Load the main scene of the application
     * 
     * @param stage Stage to place the scene on 
     * @param pc PowerCAMPUS database object
     * @param username Username of the authenticated user
     * @param password Password of the authenticated user
     * @throws IOException Main scene FXML file could not be found/loaded
     */
    private void loadMainScene(Stage stage, PowerCampusDAO pc, String username, String password) throws IOException {
        
            // Create new FXML loader and load main.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = (Parent) fxmlLoader.load(); 
            
            // Create an instance of the controller object
            MainController controller = fxmlLoader.<MainController>getController();
            
            // Inject the PowerCAMPUSDAO, username and password into the main controller
            controller.setPc(pc);
            controller.setUsername(username);
            controller.setPassword(password);
            
            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }
}
