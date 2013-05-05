/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.vcl.analyzer.CommandFileAnalyzer;
import com.vcl.analyzer.StatDBConfig;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kyihein
 */
public class CommandFileAnalyzerTest {
    
    public CommandFileAnalyzerTest() {
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
    public void testGetAllFileNames() throws SQLException {
        List<String> fileNames = CommandFileAnalyzer.getAllFileNames();
        assertNotNull(fileNames);
        assertNotSame(fileNames.size(), 0);
        System.out.println("testGetAllFileNames -- fileNames");
        for(String f : fileNames) {
            System.out.println(f);
        }
    }
}