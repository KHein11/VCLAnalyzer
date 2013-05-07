/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.CommandAnalyzer;
import com.vcl.analyzer.CommandFileAnalyzer;
import com.vcl.analyzer.VisitCondition;
import com.vcl.analyzer.model.CmdRecord;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kyihein
 */
public class CommandQueryController implements Initializable {

    @FXML
    private ComboBox<String> cmdTypeBox;
    @FXML
    private ComboBox<String> cmdFileBox;
    @FXML
    private ComboBox<String> visitBox;
    @FXML
    private TextField visitValue;
    @FXML
    private TableView cmdTable;
    @FXML
    private Label visitErrorMsg;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cmdTypeBox.getItems().setAll(AnalyzerConfig.ITEM_ALL);
            
            List<String> cmdTypes = CommandAnalyzer.getAllCmdTypes();    
            cmdTypeBox.getItems().addAll(cmdTypes);
            cmdTypeBox.setValue(AnalyzerConfig.ITEM_ALL);
            cmdFileBox.getItems().setAll(AnalyzerConfig.ITEM_ALL);
            
            List<String> cmdFiles = CommandFileAnalyzer.getAllFileNames(); 
            cmdFileBox.getItems().addAll(cmdFiles);
            cmdFileBox.setValue(AnalyzerConfig.ITEM_ALL);
            visitBox.getItems().setAll(AnalyzerConfig.ITEM_ALL, AnalyzerConfig.ITEM_EQ, AnalyzerConfig.ITEM_GT, AnalyzerConfig.ITEM_GTE, AnalyzerConfig.ITEM_LT, AnalyzerConfig.ITEM_LTE, AnalyzerConfig.ITEM_IN);
            visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        } catch (SQLException ex) {
            System.err.println("Database error occured : " + ex.getMessage());
        }
    }

    @FXML
    private void onVisitComboBoxAction(ActionEvent event) {
        if (visitBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
            visitValue.setText("");
            visitValue.setDisable(true);
        } else {
            visitValue.setDisable(false);
        }
    }

    @FXML
    private void executeQueryAction(ActionEvent event) {
        ObservableList<CmdRecord> data = cmdTable.getItems();
       
        if(visitErrorMsg.isVisible()) {
            visitErrorMsg.setVisible(false);
        }
        
        String cmdFileName = "";
        String cmdType = "";
        VisitCondition visitCondition = null;

        if (!cmdTypeBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
            cmdType = cmdTypeBox.getValue();
        }

        if (!cmdFileBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
            cmdFileName = cmdFileBox.getValue();
        }

        try {
            if (!visitBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
                visitCondition = new VisitCondition(visitBox.getValue(), visitValue.getText());
            }
        } catch (Exception e) {
            visitErrorMsg.setVisible(true);
            return;
        }
        
        try {
            List<CmdRecord> cmdList = CommandAnalyzer.findCmdFromFile(cmdFileName, cmdType, visitCondition);
            data.setAll(cmdList);
        } catch (SQLException e) {
            System.err.println("Error occured accessing database : " + e.getMessage());
        }

    }

    @FXML
    private void resetAction(ActionEvent event) {
        cmdTypeBox.setValue(AnalyzerConfig.ITEM_ALL);
        cmdFileBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitValue.setDisable(true);
        visitErrorMsg.setVisible(false);
    }
}
