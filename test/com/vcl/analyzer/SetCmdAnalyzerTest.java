/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.ExprRecord;
import com.vcl.analyzer.model.SetRecord;
import java.util.List;
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
        dbConfig.setStatDbName("/Users/kyihein/NUS/VCLProcessor/vcl_stat.db");
    }
    
    @Test
    public void testFindAffectExpr() throws Exception {
        ExprRecord er = ExprAnalyzer.findBy("?@@@C?", "resources/adapt/adapt", 9, null);

        if(er != null) {
            System.out.println("er.getCmdId() " + er.getCmdId());
            List<SetRecord> srList = SetAnalyzer.findAffectExpr(er.getCmdId());
            assertNotNull(srList);
            
            for(SetRecord sr : srList) {
                assertNotNull(sr.getVarName());
                System.out.println("getVarName " + sr.getVarName());
                if(sr.isMultiValue()) {
                    assertNotNull(sr.getValueListString());
                    System.out.println("getValueListString " + sr.getValueListString());
                } else {
                    assertNotNull(sr.getVarValue());
                    System.out.println("getVarValue " + sr.getVarValue());
                }
            }
            
            
        }
        
    }
    
    @Test
    public void testFindBy() throws Exception {
        SetRecord sr = SetAnalyzer.findBy("resources/adapt/adapt", 4, null);
        
        if(sr != null) {
            assertNotNull(sr.getVarName());
            
            System.out.println("getVarName " + sr.getVarName());
            System.out.println("getVarValue " + sr.getVarValue());
            if(sr.isMultiValue()) {
                assertNotNull(sr.getValueList());
                System.out.println("getValueListString " + sr.getValueListString());
            }
        }
        
        sr = SetAnalyzer.findBy("resources/adapt/adapt", 13, null);
        
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