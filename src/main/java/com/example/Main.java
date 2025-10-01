package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private MainController mainController;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        
        mainController = fxmlLoader.getController();
        
        stage.setTitle("FxFlix - Binging Redefined");
        stage.setScene(scene);
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.show();
    }
    
    @Override
    public void stop() {
       
        if (mainController != null) {
            mainController.shutdown();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
