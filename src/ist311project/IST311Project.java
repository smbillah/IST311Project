/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ist311project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author smbillah
 */
public class IST311Project extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);        
    }

    @Override
    public void start(Stage stage) throws Exception {
        // loading from FXML
      Parent root = FXMLLoader.load(getClass().getResource("TableView.fxml")); 
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
        
    }
    
}
