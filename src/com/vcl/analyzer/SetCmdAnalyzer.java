/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import static com.vcl.analyzer.Analyzer.dbConfig;
import com.vcl.analyzer.model.SetRecord;
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
public class SetCmdAnalyzer extends Analyzer {

    public static SetRecord findBy(String cmdFileName, int lineNo, VisitCondition visit) throws SQLException {
        SetRecord setRecord = null;

        StringBuilder sb = new StringBuilder();
        sb.append("select set_cmd.cmd_id, set_cmd.var_id, set_cmd.value, ");
        sb.append("set_cmd.multi_value, variable.var_name ");
        sb.append("from set_cmd, variable, cmd, cmd_file where ");
        sb.append("set_cmd.var_id = variable.var_id and ");
        sb.append("set_cmd.cmd_id = cmd.cmd_id and ");
        sb.append("cmd.file_id = cmd_file.file_id and cmd_file.file_name = '");
        sb.append(cmdFileName);
        sb.append("' and cmd.line_no =");
        sb.append(lineNo);

        if (visit != null) {
            sb.append(" and adapter_file.visit ");
            sb.append(visit.getVisitCondition());
        }

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {
            if (rs.next()) {
                setRecord = new SetRecord();
                setRecord.setCmdId(rs.getLong("cmd_id"));
                setRecord.setVarId(rs.getLong("var_id"));
                setRecord.setVarName(rs.getString("var_name"));
                setRecord.setVarValue(rs.getString("value"));
                setRecord.setMultiValue(rs.getInt("multi_value") == 1 ? true : false);

            }

            if (setRecord != null && setRecord.isMultiValue()) {
                StringBuilder msb = new StringBuilder();
                msb.append("select value from multi_value ");
                msb.append("where set_id = ").append(setRecord.getCmdId());
                msb.append(" order by value_no");

                try (ResultSet mrs = stmt.executeQuery(msb.toString())) {
                    List<String> valueList = new ArrayList<>();
                    StringBuilder tmp = new StringBuilder();
                    while (mrs.next()) {
                        valueList.add(mrs.getString("value"));
                        tmp.append(mrs.getString("value")).append(",");
                    }
                    setRecord.setValueList(valueList);
                    tmp.setLength(tmp.length() - 1);
                    setRecord.setValueListString(tmp.toString());
                }
            }
        }

        return setRecord;
    }
}
