/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.CommandFileAnalyzer;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author kyihein
 */
public class AdaptQueryController implements Initializable {
    public static final String ITEM_ADAPTED_BY_CMD_FILE = "adapted by command file";
    public static final String ITEM_ADAPTED_BY_COMD = "adapted by command";
    public static final String ITEM_ADAPTING_COMD_FILE = "adapting command file";
    @FXML
    private ComboBox<String> adaptTypeBox;
    @FXML
    private ComboBox<String> cmdFileBox;
    @FXML
    private ComboBox<String> visitBox;
    @FXML
    private TextField visitValue;
    @FXML
    private TableView cmdFileTable;
    @FXML
    private Label visitErrorMsg;
    @FXML
    private Label exprLabel;
    @FXML
    private Label lineNoLabel;
    @FXML
    private TextField exprValue;
    @FXML
    private TextField lineNoValue;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adaptTypeBox.getItems().setAll(ITEM_ADAPTED_BY_CMD_FILE, ITEM_ADAPTED_BY_COMD, ITEM_ADAPTING_COMD_FILE);        
            
            setCmdFieldsVisible(false);
            
            List<String> cmdFiles = CommandFileAnalyzer.getAllFileNames();
            cmdFileBox.getItems().addAll(cmdFiles);
            
            visitBox.getItems().setAll(AnalyzerConfig.ITEM_ALL, AnalyzerConfig.ITEM_EQ, AnalyzerConfig.ITEM_GT, AnalyzerConfig.ITEM_GTE, AnalyzerConfig.ITEM_LT, AnalyzerConfig.ITEM_LTE, AnalyzerConfig.ITEM_IN);
            visitBox.setValue(AnalyzerConfig.ITEM_ALL);
            
        } catch (SQLException ex) {
            System.err.println("Database error occured : " + ex.getMessage());
        }
    }
    
    private void setCmdFieldsVisible(boolean visible) {
        exprLabel.setVisible(visible);
        exprValue.setVisible(visible);
        lineNoLabel.setVisible(visible);
        lineNoValue.setVisible(visible);
    }
    
    @FXML
    private void onAdaptTyeAction(ActionEvent event) {
        if(adaptTypeBox.getValue().equals(ITEM_ADAPTED_BY_COMD)) {
            setCmdFieldsVisible(true);
        } else {
            setCmdFieldsVisible(false);
        }
    }
    
    @FXML
    private void executeQueryAction(ActionEvent event) {
        
    }
    
    @FXML
    private void resetAction(ActionEvent event) {
        adaptTypeBox.setValue(null);
        cmdFileBox.setValue(null);
        setCmdFieldsVisible(false);
        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitValue.setDisable(true);
    }
}
