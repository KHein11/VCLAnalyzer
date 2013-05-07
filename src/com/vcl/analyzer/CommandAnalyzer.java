/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

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
public class CommandAnalyzer extends Analyzer {

    public static List<CmdRecord> findCmdFromFile(String cmdFileName, String cmdType, VisitCondition visit) throws SQLException {

        List<CmdRecord> cmdList = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("Select cmd.cmd_id, cmd.file_id, cmd.cmd_type_id, cmd.cmd_value,");
        sb.append("cmd.line_no, cmd_file.file_name, cmd_file.visit, cmd_type.cmd_name ");
        sb.append("from cmd, cmd_file, cmd_type ");
        sb.append("where cmd.file_id = cmd_file.file_id and cmd.cmd_type_id = cmd_type.cmd_type_id");
        
        if(cmdFileName != null && !cmdFileName.equals("")) {
            sb.append(" and cmd_file.file_name = '").append(cmdFileName).append("'");
        }

        if (cmdType != null && !cmdType.equals("")) {
            sb.append(" and cmd_type.cmd_name = '").append(cmdType).append("'");
        }

        if (visit != null) {
            sb.append(" and cmd_file.visit ").append(visit.getVisitCondition());
        }

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {
            while (rs.next()) {
                CmdRecord cmd = getCmdRecord(rs);
                cmdList.add(cmd);
            }

        }
        return cmdList;
    }
    
    public static List<String> getAllCmdTypes() throws SQLException {
        List<String> cmdTypes = new ArrayList<>();
        String sql = "Select distinct cmd_name from cmd_type order by cmd_name";

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String cmdType = rs.getString("cmd_name");
                cmdTypes.add(cmdType);
            }

        }

        return cmdTypes;
    }

    private static CmdRecord getCmdRecord(ResultSet rs) throws SQLException {
        CmdRecord cmd = new CmdRecord();
        cmd.setCmdId(rs.getLong("cmd_id"));
        cmd.setCmdType(rs.getString("cmd_name"));
        cmd.setCmdValue(rs.getString("cmd_value"));
        cmd.setCmdFileName(rs.getString("file_name"));
        cmd.setVisit(rs.getInt("visit"));
        cmd.setLineNo(rs.getInt("line_no"));
        return cmd;
    }
}
