/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

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
public class VarAnalyzer extends Analyzer {

    public static List<String> findAllValuSetBy(String setCmd, String cmdFileName, VisitCondition visit) throws ClassNotFoundException, SQLException {
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {
            while (rs.next()) {
            }
        }
        return values;
    }
}
