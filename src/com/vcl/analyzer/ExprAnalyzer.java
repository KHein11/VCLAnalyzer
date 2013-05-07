/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import static com.vcl.analyzer.Analyzer.dbConfig;
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
public class ExprAnalyzer extends Analyzer {
    public static ExprRecord findBy(String expr, String cmdFileName, int lineNo, VisitCondition visit) throws SQLException {
        ExprRecord er = null;
            
        StringBuilder sb = new StringBuilder();
        sb.append("select expr_cmd.cmd_id, expr_cmd.expr, expr_cmd.value ");
        sb.append("from expr_cmd, cmd, cmd_file where ");
        sb.append("expr_cmd.expr = '").append(expr).append("' ");
        sb.append("and expr_cmd.cmd_id = cmd.cmd_id ");
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
                er = new ExprRecord();
                er.setCmdId(rs.getLong("cmd_id"));
                er.setExpr(rs.getString("expr"));
                er.setValue(rs.getString("value"));
            }
        }
        return er;
    }
}
