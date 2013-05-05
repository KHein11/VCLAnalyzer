/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.CmdRecord;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author oracle
 */
public class CommandAnalyzerTest {
    
    
    public CommandAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        StatDBConfig dbConfig = StatDBConfig.getInstance();
        dbConfig.setStatDbName("/Users/kyihein/NUS/VCL/vcl_stat.db");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllCmdTypes() throws Exception {
        List<String> cmdTypes = CommandAnalyzer.getAllCmdTypes();
        assertNotNull(cmdTypes);
        assertNotSame(cmdTypes.size(), 0);
        
        System.out.println("testGetAllCmdTypes() -- cmdTypes");
        for(String t : cmdTypes) {
            System.out.println(t);
        }
    }
    
    /**
     * Test of findCmdFromFile method, of class CommandAnalyzer.
     */
    @Test
    public void testFindCmdFromFile() throws Exception {
        System.out.println("findCmdFromFile");
        String cmdFileName;
        String cmdType;
        VisitCondition visitCondition;
        
        System.out.println("Testing with parameters - cmdFileName, null, null");
        cmdFileName = "resources/adapt/adapt";
        List<CmdRecord> result = CommandAnalyzer.findCmdFromFile(cmdFileName, null, null);
        assertNotNull(result);
       
        for(CmdRecord cr : result) {
            assertNotNull(cr.getCmdId());
            assertNotNull(cr.getLineNo());            
            assertNotNull(cr.getCmdFileId());
            assertNotNull(cr.getCmdFileName());
            
            System.out.println(cr.getCmdValue() + " in " + cr.getCmdFileName() + " at " + cr.getLineNo() + " , visit - " + cr.getVisit());
        }
        
        System.out.println("Testing with parameters - cmdFileName, cmdType, null");
        cmdType = "adapt";
        result = CommandAnalyzer.findCmdFromFile(cmdFileName, cmdType, null);
        assertNotNull(result);
       
        for(CmdRecord cr : result) {
            assertNotNull(cr.getCmdId());
            assertNotNull(cr.getLineNo());            
            assertNotNull(cr.getCmdFileId());
            assertNotNull(cr.getCmdFileName());
            
            System.out.println(cr.getCmdValue() + " in " + cr.getCmdFileName() + " at " + cr.getLineNo() + " , visit - " + cr.getVisit());
        }
        
        visitCondition = new VisitCondition(VisitCondition.GTE, 1);
        System.out.println("Testing with parameters - cmdFileName, cmdType, visitCondition");
        cmdType = "adapt";
        result = CommandAnalyzer.findCmdFromFile(cmdFileName, cmdType, visitCondition);
        assertNotNull(result);
       
        for(CmdRecord cr : result) {
            assertNotNull(cr.getCmdId());
            assertNotNull(cr.getLineNo());            
            assertNotNull(cr.getCmdFileId());
            assertNotNull(cr.getCmdFileName());
            
            System.out.println(cr.getCmdValue() + " in " + cr.getCmdFileName() + " at " + cr.getLineNo() + " , visit - " + cr.getVisit());
        }
    }
}