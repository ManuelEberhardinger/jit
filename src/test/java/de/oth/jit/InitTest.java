/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import org.junit.*;
import java.io.*;
import static org.junit.Assert.*;

/**
 *
 * @author manueleberhardinger
 */
public class InitTest {
    
    private static Init instance;
    
    public InitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new Init(new File("./testFolder"));
    }
    
    @AfterClass
    public static void tearDownClass() throws FileNotFoundException {
        FileUtils.deleteFolder(instance.getPath());
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Init.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Init instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of execute method, of class Init.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        String arg = "";
        Init instance = null;
        boolean expResult = false;
        boolean result = instance.execute(arg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
