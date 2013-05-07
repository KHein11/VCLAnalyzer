/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.CmdRecord;
import java.sql.SQLException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kyihein
 */
public class BreakAnalyzerTest {
    
    public BreakAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        StatDBConfig dbConfig = StatDBConfig.getInstance();
        dbConfig.setStatDbName("/Users/kyihein/NUS/VCLProcessor/vcl_stat.db");
    }
    
    @Test
    public void testFindBy() throws SQLException {
        CmdRecord cr = BreakAnalyzer.findBy("resources/insert/a", 11, null);
        
        if(cr != null) {
            assertNotNull(cr.getCmdId());
            assertNotNull(cr.getCmdValue());
            assertNotNull(cr.getCmdFileName());
            assertNotNull(cr.getVisit());
            
            System.out.println(cr.getCmdId());
            System.out.println(cr.getCmdValue());
            System.out.println(cr.getCmdFileName());
            System.out.println(cr.getLineNo());
            System.out.println(cr.getVisit());
        }
        
    }
}