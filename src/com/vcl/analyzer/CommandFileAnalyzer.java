/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.CmdFileRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyihein
 */
public class CommandFileAnalyzer extends Analyzer {

    public static List<CmdFileRecord> findAllCmdFileModifiedVar(String varName, VisitCondition visit) throws SQLException {
        List<CmdFileRecord> cmdFileList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("Select distinct cmd_file.file_id, cmd_file.file_name, cmd_file.visit ");
        sb.append("from cmd_file, set_cmd, cmd, variable where ");
        sb.append("cmd.cmd_id = set_cmd.cmd_id and cmd.file_id = cmd_file.file_id ");
        sb.append("and set_cmd.var_id = variable.var_id and variable.var_name ='");
        sb.append(varName);
        sb.append("'");

        if (visit != null) {
            sb.append(" and cmd_file.visit ");
            sb.append(visit.getVisitCondition());
        }

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {

            while (rs.next()) {
                CmdFileRecord c = new CmdFileRecord();
                c.setFileId(rs.getLong("file_id"));
                c.setFileName(rs.getString("file_name"));
                c.setVisit(rs.getInt("visit"));
                cmdFileList.add(c);
            }

        }
        return cmdFileList;
    }

    public static List<Long> findFileId(String fileName) throws SQLException {
        List<Long> idList = new ArrayList<>();

        String sql = "Select file_id from cmd_file where file_name = ?";

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fileName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    idList.add(rs.getLong("file_id"));
                }
            }

        }

        return idList;
    }
    
    public static List<String> getAllFileNames() throws SQLException {
        List<String> fileNames = new ArrayList<>();
        String sql = "Select distinct file_name from cmd_file order by file_id";

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String file = rs.getString("file_name");
                fileNames.add(file);
            }

        }

        return fileNames;
    }
}
