/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.CmdRecord;
import java.sql.SQLException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kyihein
 */
public class MatchAnalyzerTest {
    
    public MatchAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        StatDBConfig dbConfig = StatDBConfig.getInstance();
        dbConfig.setStatDbName("/Users/kyihein/NUS/VCLProcessor/vcl_stat.db");
    }
    
    @Test
    public void testFindMatchedInsert() throws SQLException {
        CmdRecord breakRecord = BreakAnalyzer.findBy("resources/insert/a", 11, null);
        System.out.println(" testFindMatchedInsert() ");
        if(breakRecord != null) {
            List<CmdRecord> crList = MatchAnalyzer.findMatchedInsert(breakRecord.getCmdId());
            for(CmdRecord cr : crList) {
                assertNotNull(cr.getCmdId());
            assertNotNull(cr.getCmdValue());
            assertNotNull(cr.getCmdFileName());
            assertNotNull(cr.getVisit());
            
            System.out.println(" -------------------- ");
            System.out.println("getCmdId " + cr.getCmdId());
            System.out.println("getCmdValue " + cr.getCmdValue());
            System.out.println("getCmdFileName " + cr.getCmdFileName());
            System.out.println("getLineNo " + cr.getLineNo());
            System.out.println("getVisit " + cr.getVisit());
            }
        }
    }
    
    @Test
    public void testFindMatchedBreak() throws SQLException {
        CmdRecord insertRecord = InsertAnalyzer.findBy("resources/insert/insert", 4, null);

        System.out.println(" testFindMatchedBreak() ");
        
        if(insertRecord != null) {
            List<CmdRecord> crList = MatchAnalyzer.findMatchedBreak(insertRecord.getCmdId());
            for(CmdRecord cr : crList) {
                assertNotNull(cr.getCmdId());
            assertNotNull(cr.getCmdValue());
            assertNotNull(cr.getCmdFileName());
            assertNotNull(cr.getVisit());
            
            System.out.println(" -------------------- ");
            System.out.println("getCmdId " + cr.getCmdId());
            System.out.println("getCmdValue " + cr.getCmdValue());
            System.out.println("getCmdFileName " + cr.getCmdFileName());
            System.out.println("getLineNo " + cr.getLineNo());
            System.out.println("getVisit " + cr.getVisit());
            }
        }
    }
}