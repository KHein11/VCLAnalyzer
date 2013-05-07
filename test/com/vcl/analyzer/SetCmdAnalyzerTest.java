/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.SetRecord;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author kyihein
 */
public class SetCmdAnalyzerTest {
    
    public SetCmdAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        StatDBConfig dbConfig = StatDBConfig.getInstance();
        dbConfig.setStatDbName("/Users/kyihein/NUS/VCL/vcl_stat.db");
    }
    
    @Test
    public void testFindBy() throws Exception {
        SetRecord sr = SetCmdAnalyzer.findBy("resources/adapt/adapt", 4, null);
        
        if(sr != null) {
            assertNotNull(sr.getVarName());
            
            System.out.println("getVarName " + sr.getVarName());
            System.out.println("getVarValue " + sr.getVarValue());
            if(sr.isMultiValue()) {
                assertNotNull(sr.getValueList());
                System.out.println("getValueListString " + sr.getValueListString());
            }
        }
        
        sr = SetCmdAnalyzer.findBy("resources/adapt/adapt", 12, null);
        
        if(sr != null) {
            System.out.println("getVarName " + sr.getVarName());
            System.out.println("getVarValue " + sr.getVarValue());
            assertNotNull(sr.getVarName());
            
            if(sr.isMultiValue()) {
                assertNotNull(sr.getValueList());
                System.out.println("getValueListString " + sr.getValueListString());
            }
        }
    }
}