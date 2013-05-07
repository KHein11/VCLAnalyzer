/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.StatDBConfig;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author kyihein
 */
public class AnalyzerController implements Initializable {
    public static final int NO_MENU = -1;
    public static final int CMD_MENU = 0;
    public static final int CMD_FILE_MENU = 1;
    public static final int ADAPT_MENU = 2;
    public static final int ASSIGN_MENU = 3;
    public static final int YIELD_MENU = 4;
    public static final int AFFECT_MENU = 5;
    public static final int MATCH_MENU = 6;
    
    private int currentMenu = NO_MENU;
    
    private StatDBConfig statDbConfig = StatDBConfig.getInstance();
    
    private Stage stage;
    private String statDbFile;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Hyperlink cmdMenu;
    @FXML
    private Label noDbLabel;
    
    @FXML
    private void openDbAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQLite Database (*.db)", "*.db");
        fc.getExtensionFilters().add(extFilter);
        File f = fc.showOpenDialog(stage);
        if (f != null) {
            statDbFile = f.getPath();
            stage.setTitle(Analyzer.ANALYZER_NAME + " - " + statDbFile);
            noDbLabel.setVisible(false);
            statDbConfig.setStatDbName(statDbFile);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cmdMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != CMD_MENU) {
            loadQueryPane("CommandQuery.fxml");
            currentMenu = CMD_MENU;
        }
    }
    
    @FXML
    private void adaptMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != ADAPT_MENU) {
            loadQueryPane("AdaptQuery.fxml");
            currentMenu = ADAPT_MENU;
        }
    }

    @FXML
    private void cmdFileMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != CMD_FILE_MENU) {
            loadQueryPane("CommandFileQuery.fxml");
            currentMenu = CMD_FILE_MENU;
        }
    }
    
    @FXML
    private void assignMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != ASSIGN_MENU) {
            loadQueryPane("AssignQuery.fxml");
            currentMenu = ASSIGN_MENU;
        }
    }
    
    @FXML
    private void yieldMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != YIELD_MENU) {
            loadQueryPane("YieldQuery.fxml");
            currentMenu = YIELD_MENU;
        }
    }
    
    @FXML
    private void affectMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != AFFECT_MENU) {
            loadQueryPane("AffectQuery.fxml");
            currentMenu = AFFECT_MENU;
        }
    }
    
    @FXML
    private void matchMenuAction(ActionEvent event) throws IOException {
        if (statDbFile != null && !statDbFile.equals("") && currentMenu != MATCH_MENU) {
            loadQueryPane("MatchQuery.fxml");
            currentMenu = MATCH_MENU;
        }
    }
    
    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadQueryPane(String queryFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(queryFileName));
        Pane cmdPane = (Pane) fxmlLoader.load();

        try {
            borderPane.setCenter(cmdPane);
        } catch (Exception e) {
            System.err.println("Database error occured : " + e.getMessage());
        }
    }
}
