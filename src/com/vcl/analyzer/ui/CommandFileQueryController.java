/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.CommandFileAnalyzer;
import com.vcl.analyzer.VisitCondition;
import com.vcl.analyzer.model.CmdFileRecord;
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
 *
 * @author kyihein
 */
public class CommandFileQueryController  implements Initializable {
    @FXML
    private TextField varNameValue;
    @FXML
    private ComboBox<String> visitBox;
    @FXML
    private TextField visitValue;
    @FXML
    private TableView<CmdFileRecord> cmdFileTable;
    @FXML
    private Label visitErrorMsg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visitBox.getItems().setAll(AnalyzerConfig.ITEM_ALL, AnalyzerConfig.ITEM_EQ, AnalyzerConfig.ITEM_GT, AnalyzerConfig.ITEM_GTE, AnalyzerConfig.ITEM_LT, AnalyzerConfig.ITEM_LTE, AnalyzerConfig.ITEM_IN);
        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
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
        if(visitErrorMsg.isVisible()) {
            visitErrorMsg.setVisible(false);
        }
        
        VisitCondition visitCondition = null;
        
        try {
            if (!visitBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
                visitCondition = new VisitCondition(visitBox.getValue(), visitValue.getText());
            }
        } catch (Exception e) {
            visitErrorMsg.setVisible(true);
            return;
        }
        
        try {
            String varNameInput = varNameValue.getText();
            List<CmdFileRecord> cmdFileRecords = CommandFileAnalyzer.findAllCmdFileModifiedVar(varNameInput, visitCondition);
            ObservableList<CmdFileRecord> data = cmdFileTable.getItems();
            data.setAll(cmdFileRecords);
        } catch (SQLException e) {
            System.err.println("Error occured accessing database : " + e.getMessage());
        }
    }
    
    @FXML
    private void resetAction(ActionEvent event) {
        varNameValue.setText("");
        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitValue.setDisable(true);
        visitErrorMsg.setVisible(false);
    }
}
