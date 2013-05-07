/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.CommandFileAnalyzer;
import com.vcl.analyzer.SetAnalyzer;
import com.vcl.analyzer.VisitCondition;
import com.vcl.analyzer.model.CmdFileRecord;
import com.vcl.analyzer.model.SetRecord;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AssignQueryController implements Initializable {

    @FXML
    private TextField lineNoValue;
    @FXML
    private ComboBox<String> visitBox;
    @FXML
    private TextField visitValue;
    @FXML
    private Label visitErrorMsg;
    @FXML
    private Label lineNoErrorMsg;
    @FXML
    private Label assignedValueLabel;
    @FXML
    private TextField assignedValue;
    @FXML
    private ComboBox<String> cmdFileBox;
    private List<String> cmdFiles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setResultVisible(false);
            cmdFiles = CommandFileAnalyzer.getAllFileNames();
            cmdFileBox.getItems().setAll(cmdFiles);
            if (cmdFiles.size() > 0) {
                cmdFileBox.setValue(cmdFiles.get(0));
            }

        } catch (SQLException ex) {
            System.err.println("Database error occured : " + ex.getMessage());
        }

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
        hideErrorMsg();
        
        VisitCondition visit = null;
        int lineNo = 0;
        String cmdFileName = "";
        
        try {
            if (!visitBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
                visit = new VisitCondition(visitBox.getValue(), visitValue.getText());
            }
        } catch (Exception e) {
            visitErrorMsg.setVisible(true);
            return;
        }

        try {
            lineNo = Integer.parseInt(lineNoValue.getText());
        } catch (Exception e) {
            lineNoErrorMsg.setVisible(true);
            return;
        }
        
        cmdFileName = cmdFileBox.getValue();
        
        try {
            SetRecord sr = SetAnalyzer.findBy(cmdFileName, lineNo, visit);
            setResultVisible(true);
            if (sr != null) {
                if(sr.isMultiValue()) {
                    assignedValue.setText(sr.getValueListString());
                } else {
                    assignedValue.setText(sr.getVarValue());
                }
            } else {
                assignedValue.setVisible(false);
            }
        } catch (SQLException e) {
            System.err.println("Error occured accessing database : " + e.getMessage());
        }
    }

    @FXML
    private void resetAction(ActionEvent event) {
        hideErrorMsg();
        lineNoValue.setText("");
        
        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitValue.setDisable(true);
        
        if(cmdFiles.size() > 0) {
           cmdFileBox.setValue(cmdFiles.get(0));
        }
        setResultVisible(false);
    }

    private void hideErrorMsg() {
        if (visitErrorMsg.isVisible()) {
            visitErrorMsg.setVisible(false);
        }
        
        if (lineNoErrorMsg.isVisible()) {
            lineNoErrorMsg.setVisible(false);
        }
    }

    private void setResultVisible(boolean visible) {
        assignedValueLabel.setVisible(visible);
        assignedValue.setVisible(visible);
    }
}
