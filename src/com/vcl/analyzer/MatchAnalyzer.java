/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import static com.vcl.analyzer.Analyzer.dbConfig;
import com.vcl.analyzer.model.CmdRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyihein
 */
public class MatchAnalyzer extends Analyzer {
    public static List<CmdRecord> findMatchedInsert(Long breakId) throws SQLException {
        List<CmdRecord> cmdRecords = new ArrayList<>();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Select cmd.cmd_id, cmd.file_id, cmd.cmd_value,");
        sb.append("cmd.line_no, cmd_file.file_name, cmd_file.visit ");
        sb.append("from cmd, cmd_file, insert_cmd, break_insert ");
        sb.append("where cmd.file_id = cmd_file.file_id and ");
        sb.append("cmd.cmd_id = insert_cmd.cmd_id and ");
        sb.append("insert_cmd.cmd_id = break_insert.insert_id and ");
        sb.append("break_insert.break_id = ").append(breakId);
        
        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {
            while (rs.next()) {
                CmdRecord cmd = getCmdRecord(rs);
                cmdRecords.add(cmd);
            }

        }
        
        return cmdRecords;
    }
    
    public static List<CmdRecord> findMatchedBreak(Long insertId) throws SQLException {
        List<CmdRecord> cmdRecords = new ArrayList<>();
        
        StringBuilder sb = new StringBuilder();
        sb.append("Select cmd.cmd_id, cmd.file_id, cmd.cmd_value,");
        sb.append("cmd.line_no, cmd_file.file_name, cmd_file.visit ");
        sb.append("from cmd, cmd_file, break_cmd, break_insert ");
        sb.append("where cmd.file_id = cmd_file.file_id and ");
        sb.append("cmd.cmd_id = break_cmd.cmd_id and ");
        sb.append("break_cmd.cmd_id = break_insert.break_id and ");
        sb.append("break_insert.insert_id = '").append(insertId).append("'");
        
        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {
            while (rs.next()) {
                CmdRecord cmd = getCmdRecord(rs);
                cmdRecords.add(cmd);
            }

        }
        
        return cmdRecords;
    }
    
    private static CmdRecord getCmdRecord(ResultSet rs) throws SQLException {
        CmdRecord cmd = new CmdRecord();
        cmd.setCmdId(rs.getLong("cmd_id"));
        cmd.setCmdValue(rs.getString("cmd_value"));
        cmd.setCmdFileName(rs.getString("file_name"));
        cmd.setVisit(rs.getInt("visit"));
        cmd.setLineNo(rs.getInt("line_no"));
        return cmd;
    }
}
