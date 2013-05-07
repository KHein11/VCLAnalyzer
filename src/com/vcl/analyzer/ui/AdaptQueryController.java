/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.AdaptAnalyzer;
import com.vcl.analyzer.CommandFileAnalyzer;
import com.vcl.analyzer.VisitCondition;
import com.vcl.analyzer.model.AdaptRecord;
import com.vcl.analyzer.model.CmdRecord;
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
public class AdaptQueryController implements Initializable {

    public static final String ITEM_ADAPTED_BY_CMD_FILE = "adapted by command file";
    public static final String ITEM_ADAPTED_BY_CMD = "adapted by command";
    public static final String ITEM_ADAPTING_CMD_FILE = "adapting command file";
    public static final String ITEM_ADAPTED_BY_CMD_FILE_TRANSITIVE = "adapted* by command file";
    public static final String ITEM_ADAPTED_BY_CMD_TRANSITIVE = "adapted* by command";
    public static final String ITEM_ADAPTING_CMD_FILE_TRANSITIVE = "adapting* command file";
    
    @FXML
    private ComboBox<String> adaptTypeBox;
    @FXML
    private ComboBox<String> cmdFileBox;
    @FXML
    private ComboBox<String> visitBox;
    @FXML
    private TextField visitValue;
    @FXML
    private TableView<AdaptRecord> adaptRecordTable;
    @FXML
    private Label visitErrorMsg;
    @FXML
    private Label lineNoErrorMsg;
    @FXML
    private Label exprLabel;
    @FXML
    private Label lineNoLabel;
    @FXML
    private TextField exprValue;
    @FXML
    private TextField lineNoValue;

    private List<String> cmdFiles;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            adaptTypeBox.getItems().setAll(ITEM_ADAPTED_BY_CMD_FILE, 
                    ITEM_ADAPTED_BY_CMD, ITEM_ADAPTING_CMD_FILE, 
                    ITEM_ADAPTED_BY_CMD_FILE_TRANSITIVE, 
                    ITEM_ADAPTING_CMD_FILE_TRANSITIVE);
            adaptTypeBox.setValue(ITEM_ADAPTED_BY_CMD_FILE);
            
            setCmdFieldsVisible(false);
            
            cmdFiles = CommandFileAnalyzer.getAllFileNames();
            cmdFileBox.getItems().addAll(cmdFiles);
            if(cmdFiles.size() > 0) {
                cmdFileBox.setValue(cmdFiles.get(0));
            }
            
            visitBox.getItems().setAll(AnalyzerConfig.ITEM_ALL, AnalyzerConfig.ITEM_EQ, AnalyzerConfig.ITEM_GT, AnalyzerConfig.ITEM_GTE, AnalyzerConfig.ITEM_LT, AnalyzerConfig.ITEM_LTE, AnalyzerConfig.ITEM_IN);
            visitBox.setValue(AnalyzerConfig.ITEM_ALL);

        } catch (SQLException ex) {
            System.err.println("Database error occured : " + ex.getMessage());
        }
    }

    private void setCmdFieldsVisible(boolean visible) {
        //exprLabel.setVisible(visible);
        //exprValue.setVisible(visible);
        lineNoLabel.setVisible(visible);
        lineNoValue.setVisible(visible);
    }

    @FXML
    private void onAdaptTyeAction(ActionEvent event) {
        if(adaptTypeBox.getValue().equals(ITEM_ADAPTED_BY_CMD)) {
            setCmdFieldsVisible(true);
        } else {
            setCmdFieldsVisible(false);
        }
        
        if(adaptTypeBox.getValue().equals(ITEM_ADAPTING_CMD_FILE_TRANSITIVE) ||
           adaptTypeBox.getValue().equals(ITEM_ADAPTED_BY_CMD_FILE_TRANSITIVE)) {
            visitBox.setValue(AnalyzerConfig.ITEM_ALL);
            visitBox.setDisable(true);
            visitValue.setDisable(true);
        } else {
            if(visitBox.isDisable()) {
                visitBox.setDisable(false);
            }
        }
        
    }

    @FXML
    private void onVisitComboBoxAction(ActionEvent event) {
        if (visitBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
            visitValue.setDisable(true);
        } else {
            visitValue.setText("");
            visitValue.setDisable(false);
        }
    }
    
    @FXML
    private void executeQueryAction(ActionEvent event) {
        List<AdaptRecord> adaptRecords = null;

        String cmdFile = cmdFileBox.getValue();
        VisitCondition visitCondition = null;
        int lineNo = 0;

        if (visitErrorMsg.isVisible()) {
            visitErrorMsg.setVisible(false);
        }

        if (lineNoErrorMsg.isVisible()) {
            lineNoErrorMsg.setVisible(false);
        }


        if (!visitBox.getValue().equals(AnalyzerConfig.ITEM_ALL)) {
            try {
                visitCondition = new VisitCondition(visitBox.getValue(), visitValue.getText());
            } catch (Exception e) {
                visitErrorMsg.setVisible(true);
                return;
            }
        }

        if (adaptTypeBox.getValue().equals(ITEM_ADAPTED_BY_CMD)) {
            try {
                lineNo = Integer.parseInt(lineNoValue.getText());
            } catch (Exception e) {
                lineNoErrorMsg.setVisible(true);
                return;
            }
        }

        try {
            switch (adaptTypeBox.getValue()) {
                case ITEM_ADAPTED_BY_CMD_FILE:
                    adaptRecords = AdaptAnalyzer.findAllAdaptedByCmdFile(cmdFile, visitCondition);
                    break;
                case ITEM_ADAPTED_BY_CMD:
                    adaptRecords = AdaptAnalyzer.findAllAdaptedByCmd(cmdFile, lineNo, visitCondition);
                    break;
                case ITEM_ADAPTING_CMD_FILE:
                    adaptRecords = AdaptAnalyzer.findAllAdaptingCmdFile(cmdFile, visitCondition);
                    break;
                case ITEM_ADAPTED_BY_CMD_FILE_TRANSITIVE:
                    adaptRecords = AdaptAnalyzer.findAllAdaptedByCmdFileTransitive(cmdFile);
                    break;
                case ITEM_ADAPTING_CMD_FILE_TRANSITIVE:
                    adaptRecords = AdaptAnalyzer.findAllAdaptingCmdFileTransitive(cmdFile);
                    break;
            }
            ObservableList<AdaptRecord> data = adaptRecordTable.getItems();
            data.setAll(adaptRecords);
            
        } catch (SQLException e) {
            System.err.println("Database error occured : " + e.getMessage());
        }
    }

    @FXML
    private void resetAction(ActionEvent event) {
        visitErrorMsg.setVisible(false);
        lineNoErrorMsg.setVisible(false);
        
        adaptTypeBox.setValue(ITEM_ADAPTED_BY_CMD_FILE);
        if(cmdFiles.size() > 0) {
           cmdFileBox.setValue(cmdFiles.get(0));
        }
        setCmdFieldsVisible(false);
        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitValue.setDisable(true);
    }
}
