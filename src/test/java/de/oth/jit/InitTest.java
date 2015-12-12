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
        instance.getPath().mkdir();
    }
    
    @AfterClass
    public static void tearDownClass() throws FileNotFoundException {
        //FileUtils.deleteFolder(instance.getPath());
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
        String expResult = "init";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of execute method, of class Init.
     */
    @Test
    public void testExecute() throws Exception {
        boolean expResult = true;
        boolean result = instance.execute(null);
        
        // Test if repository is created.
        assertEquals(expResult, result);
        
        // Test if objects and staging folder is created.
        Init directory = new Init(new File(instance.getPath().getCanonicalPath() + "/.jit"));
        
        File[] folders = directory.getPath().listFiles();
        
        for(File f : folders) {
            if(f.getName() == "objects")
                assertEquals(expResult, f.exists());
            else if(f.getName() == "staging")
                assertEquals(expResult, f.exists());
        }
        
        // Test if only 2 folders are created.
        assertEquals(2, folders.length);
    }
    
}
