/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kyihein
 */
public class Analyzer extends Application {
    public static final String ANALYZER_NAME = "VCL Analyzer";
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Analyzer.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        AnalyzerController controller = (AnalyzerController)fxmlLoader.getController();
        controller.setStage(stage);        
        Scene scene = new Scene(root);
        stage.setTitle(ANALYZER_NAME);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}