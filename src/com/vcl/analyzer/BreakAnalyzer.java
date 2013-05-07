/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import static com.vcl.analyzer.Analyzer.dbConfig;
import com.vcl.analyzer.model.CmdRecord;
import com.vcl.analyzer.model.ExprRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kyihein
 */
public class BreakAnalyzer extends Analyzer {

    public static CmdRecord findBy(String cmdFileName, int lineNo, VisitCondition visit) throws SQLException {
        CmdRecord cr = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Select cmd.cmd_id, cmd.file_id, cmd.cmd_value,");
        sb.append("cmd.line_no, cmd_file.file_name, cmd_file.visit ");
        sb.append("from break_cmd, cmd, cmd_file where ");
        sb.append("break_cmd.cmd_id = cmd.cmd_id ");
        sb.append("and cmd.file_id = cmd_file.file_id ");
        sb.append("and cmd_file.file_name = '").append(cmdFileName).append("' ");
        sb.append("and cmd.line_no = ");
        sb.append(lineNo);

        if (visit != null) {
            sb.append(" and cmd_file.visit ");
            sb.append(visit.getVisitCondition());
        }

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {

            if (rs.next()) {
                cr = new ExprRecord();
                cr.setCmdId(rs.getLong("cmd_id"));
                cr.setCmdValue(rs.getString("cmd_value"));
                cr.setCmdFileName(rs.getString("file_name"));
                cr.setVisit(rs.getInt("visit"));
                cr.setLineNo(rs.getInt("line_no"));
            }
        }
        return cr;
    }
}
