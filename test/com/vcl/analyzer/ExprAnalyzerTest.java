/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vcl.analyzer;

import com.vcl.analyzer.model.ExprRecord;
import java.sql.SQLException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kyihein
 */
public class ExprAnalyzerTest {
    
    public ExprAnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        StatDBConfig dbConfig = StatDBConfig.getInstance();
        dbConfig.setStatDbName("/Users/kyihein/NUS/VCLProcessor/vcl_stat.db");
    }
    
    @Test
    public void testFindBy() throws SQLException {
        ExprRecord er = ExprAnalyzer.findBy("?@@@C?", "resources/adapt/adapt", 9, null);

        if(er != null) {
            assertNotNull(er.getExpr());
            System.out.println("getValue " + er.getValue());
            System.out.println("getExpr " + er.getExpr());
        }
    }
}