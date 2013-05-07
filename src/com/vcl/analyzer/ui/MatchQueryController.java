/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer.ui;

import com.vcl.analyzer.BreakAnalyzer;
import com.vcl.analyzer.CommandFileAnalyzer;
import com.vcl.analyzer.ExprAnalyzer;
import com.vcl.analyzer.InsertAnalyzer;
import com.vcl.analyzer.MatchAnalyzer;
import com.vcl.analyzer.SetAnalyzer;
import com.vcl.analyzer.VisitCondition;
import com.vcl.analyzer.model.AdaptRecord;
import com.vcl.analyzer.model.CmdRecord;
import com.vcl.analyzer.model.ExprRecord;
import com.vcl.analyzer.model.SetRecord;
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
public class MatchQueryController implements Initializable {

    public static final String FIND_TYPE_INSERT = "insert";
    public static final String FIND_TYPE_BREAK = "break";
    public static final String MATCH_TYPE_BREAK = "match break";
    public static final String MATCH_TYPE_INSERT = "match insert";
    @FXML
    private ComboBox<String> findTypeBox;
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
    private ComboBox<String> cmdFileBox;
    private List<String> cmdFiles;
    @FXML
    private Label matchedTypeLabel;
    @FXML
    private TableView<CmdRecord> cmdRecordTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            findTypeBox.getItems().setAll(FIND_TYPE_INSERT, FIND_TYPE_BREAK);
            findTypeBox.setValue(FIND_TYPE_INSERT);

            matchedTypeLabel.setText(MATCH_TYPE_BREAK);

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
    private void onFindTypeComboBoxAction(ActionEvent event) {
        if (findTypeBox.getValue().equals(FIND_TYPE_BREAK)) {
            matchedTypeLabel.setText(MATCH_TYPE_INSERT);
        } else {
            matchedTypeLabel.setText(MATCH_TYPE_BREAK);
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
        hideErrorMsg();

        VisitCondition visit = null;
        int lineNo = 0;
        String cmdFileName = "";
        String expr = "";

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
        List<CmdRecord> cmdRecords = null;
        ObservableList<CmdRecord> data = cmdRecordTable.getItems();
        
        try {
            if (findTypeBox.getValue().equals(FIND_TYPE_BREAK)) {
                CmdRecord insertRec = InsertAnalyzer.findBy(cmdFileName, lineNo, visit);
                System.out.println("insertRec " + insertRec);
                if (insertRec != null) {
                    cmdRecords = MatchAnalyzer.findMatchedBreak(insertRec.getCmdId());
                    System.out.println("cmdRecords.size() " + cmdRecords.size());
                    data.setAll(cmdRecords);
                }
                
            } else {
                CmdRecord breakRec = BreakAnalyzer.findBy(cmdFileName, lineNo, visit);
                System.out.println("breakRec " + breakRec);
                if (breakRec != null) {
                   cmdRecords = MatchAnalyzer.findMatchedInsert(breakRec.getCmdId());
                   System.out.println("cmdRecords.size() " + cmdRecords.size());
                   data.setAll(cmdRecords);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error occured accessing database : " + e.getMessage());
        }
    }

    @FXML
    private void resetAction(ActionEvent event) {
        hideErrorMsg();
        findTypeBox.setValue(FIND_TYPE_INSERT);
        matchedTypeLabel.setText(MATCH_TYPE_BREAK);

        lineNoValue.setText("");

        visitBox.setValue(AnalyzerConfig.ITEM_ALL);
        visitValue.setDisable(true);

        if (cmdFiles.size() > 0) {
            cmdFileBox.setValue(cmdFiles.get(0));
        }

    }

    private void hideErrorMsg() {
        if (visitErrorMsg.isVisible()) {
            visitErrorMsg.setVisible(false);
        }

        if (lineNoErrorMsg.isVisible()) {
            lineNoErrorMsg.setVisible(false);
        }
    }
}
