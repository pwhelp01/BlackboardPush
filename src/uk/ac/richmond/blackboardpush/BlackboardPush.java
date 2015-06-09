/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.richmond.blackboardpush;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author pete
 */
public class BlackboardPush extends Application {
    
    ApplicationProperties props;
    
    /**
     * Start the Blackboard push application
     * @param stage Not used
     * @throws Exception Not used
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setTitle("Richmond, The American International University - Blackboard Push");
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Entry point to the Blackboard Push application
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
      
}
