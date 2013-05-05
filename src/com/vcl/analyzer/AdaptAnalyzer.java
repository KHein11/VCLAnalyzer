/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.AdaptRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kyihein
 */
public class AdaptAnalyzer extends Analyzer {

    public static List<Long> findAdapterFileId(String fileName, int visit, int lineNo) throws ClassNotFoundException, SQLException {
        List<Long> idList = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("Select cmd_file.file_id from cmd_file, cmd, adapt_cmd ");
        sb.append("where cmd.cmd_id = adapt_cmd.cmd_id ");
        sb.append("and adapt_cmd.adapting_file_id = cmd_file.file_id ");
        sb.append("and file_name = '");
        sb.append(fileName);
        sb.append("'");

        if (visit > 0) {
            sb.append(" and visit = ");
            sb.append(visit);
        }

        if (lineNo > 0) {
            sb.append(" and cmd.line_no =");
            sb.append(lineNo);
        }


        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString())) {
            while (rs.next()) {
                idList.add(rs.getLong("file_id"));
            }
        }

        return idList;
    }

    public static List<AdaptRecord> findAllAdaptedByCmdFileTransitive(String adapter) throws ClassNotFoundException, SQLException {
        List<AdaptRecord> adapts = findAllAdaptedByCmdFile(adapter);

        Map<Long, String> visited = new HashMap<>();
        LinkedList<AdaptRecord> toVisit = new LinkedList<>();

        CommandFileAnalyzer cfa = new CommandFileAnalyzer();
        List<Long> adapterIdList = cfa.findFileId(adapter);

        //To avoid loop during bfs, we marked adapter id and name as visited
        for (Long id : adapterIdList) {
            visited.put(id, adapter);
        }

        toVisit.addAll(adapts);

        while (!toVisit.isEmpty()) {
            AdaptRecord tmpRec = toVisit.remove();
            if (!visited.containsKey(tmpRec.getAdaptedFileId())) {
                List<AdaptRecord> tmpList = findAllAdaptedByCmdFile(tmpRec.getAdaptedFileName(), new VisitCondition(VisitCondition.EQ, tmpRec.getAdapteeVisit()));

                adapts.addAll(tmpList);
                toVisit.addAll(tmpList);
                visited.put(tmpRec.getAdaptedFileId(), tmpRec.getAdaptedFileName());
            }
        }

        return adapts;

    }

    public static List<AdaptRecord> findAllAdaptedByCmdFile(String adapter) throws ClassNotFoundException, SQLException {
        return findAllAdaptedByCmdFile(adapter, null);
    }

    public static List<AdaptRecord> findAllAdaptedByCmdFile(String adapter, VisitCondition visit) throws ClassNotFoundException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("Select adapt_cmd.cmd_id,adapting_file_id,adapted_file_id,");
        sb.append("adapter_file.file_name as adapter_name,adapted_file.file_name ");
        sb.append("as adaptee_name,adapter_file.visit as adapter_visit, adapted_file.visit as adaptee_visit ");
        sb.append("from adapt_cmd, cmd_file as adapter_file, ");
        sb.append("cmd_file as adapted_file where adapting_file_id = ");
        sb.append("adapter_file.file_id and adapted_file_id = adapted_file.file_id ");
        sb.append("and adapter_file.file_name = '");
        sb.append(adapter);
        sb.append("'");

        if (visit != null) {
            sb.append(" and adapter_file.visit ");
            sb.append(visit.getVisitCondition());
        }

        List<AdaptRecord> adapts = processAdaptQuery(sb.toString());

        return adapts;
    }

    public static List<AdaptRecord> findAllAdaptingCmdFileTransitive(String adapter) throws ClassNotFoundException, SQLException {
        List<AdaptRecord> adapts = findAllAdaptingCmdFile(adapter);

        Map<Long, String> visited = new HashMap<>();
        LinkedList<AdaptRecord> toVisit = new LinkedList<>();

        CommandFileAnalyzer cfa = new CommandFileAnalyzer();
        List<Long> adapterIdList = cfa.findFileId(adapter);

        //To avoid loop during bfs, we marked adapter id and name as visited
        for (Long id : adapterIdList) {
            visited.put(id, adapter);
        }

        toVisit.addAll(adapts);

        while (!toVisit.isEmpty()) {
            AdaptRecord tmpRec = toVisit.remove();
            if (!visited.containsKey(tmpRec.getAdapterFileId())) {
                List<AdaptRecord> tmpList = findAllAdaptingCmdFile(tmpRec.getAdapterFileName(), new VisitCondition(VisitCondition.EQ, tmpRec.getAdapterVisit()));

                adapts.addAll(tmpList);
                toVisit.addAll(tmpList);
                visited.put(tmpRec.getAdapterFileId(), tmpRec.getAdapterFileName());
            }
        }

        return adapts;

    }

    public static List<AdaptRecord> findAllAdaptingCmdFile(String adaptee) throws ClassNotFoundException, SQLException {
        return findAllAdaptingCmdFile(adaptee, null);
    }

    public static List<AdaptRecord> findAllAdaptingCmdFile(String adaptee, VisitCondition visit) throws ClassNotFoundException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("Select adapt_cmd.cmd_id,adapting_file_id,adapted_file_id,");
        sb.append("adapter_file.file_name as adapter_name,adapted_file.file_name ");
        sb.append("as adaptee_name, adapter_file.visit as adapter_visit, adapted_file.visit as adaptee_visit ");
        sb.append("from adapt_cmd, cmd_file as adapter_file, ");
        sb.append("cmd_file as adapted_file where adapting_file_id = ");
        sb.append("adapter_file.file_id and adapted_file_id = adapted_file.file_id ");
        sb.append("and adapted_file.file_name = '");
        sb.append(adaptee);
        sb.append("'");

        if (visit != null) {
            sb.append(" and adapted_file.visit ");
            sb.append(visit.getVisitCondition());
        }

        List<AdaptRecord> adapts = processAdaptQuery(sb.toString());

        return adapts;
    }

    public static List<AdaptRecord> findAllAdaptedByCmd(String adaptCmdExpr, String cmdFileName, int lineNo, VisitCondition visit) throws ClassNotFoundException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("Select adapt_cmd.cmd_id,adapting_file_id,adapted_file_id,");
        sb.append("adapter_file.file_name as adapter_name,adapted_file.file_name ");
        sb.append("as adaptee_name,adapter_file.visit as adapter_visit, adapted_file.visit as adaptee_visit ");
        sb.append("from adapt_cmd, cmd, cmd_file as adapter_file, ");
        sb.append("cmd_file as adapted_file where adapting_file_id = ");
        sb.append("adapter_file.file_id and adapted_file_id = adapted_file.file_id ");
        sb.append("and cmd.cmd_id = adapt_cmd.cmd_id and adapter_file.file_name = '");
        sb.append(cmdFileName);
        sb.append("' and adapt_cmd.adapt_expr ='");
        sb.append(adaptCmdExpr);
        sb.append("'");

        if (visit != null) {
            sb.append(" and adapter_file.visit ");
            sb.append(visit.getVisitCondition());
        }

        if (lineNo > 0) {
            sb.append(" and cmd.line_no =");
            sb.append(lineNo);
        }
        List<AdaptRecord> adapts = processAdaptQuery(sb.toString());
        return adapts;
    }

    private static List<AdaptRecord> processAdaptQuery(String query) throws ClassNotFoundException, SQLException {
        List<AdaptRecord> adapts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbConfig.getConnName());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                AdaptRecord adapt = new AdaptRecord();
                adapt.setCmdId(rs.getLong("cmd_id"));
                adapt.setAdapterFileId(rs.getLong("adapting_file_id"));
                adapt.setAdaptedFileId(rs.getLong("adapted_file_id"));
                adapt.setAdaptedFileName(rs.getString("adaptee_name"));
                adapt.setAdapterFileName(rs.getString("adapter_name"));
                adapt.setAdapterVisit(rs.getInt("adapter_visit"));
                adapt.setAdapteeVisit(rs.getInt("adaptee_visit"));
                adapts.add(adapt);
            }
        }
        return adapts;
    }
}
