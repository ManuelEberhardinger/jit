/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author manueleberhardinger
 */
public class FileUtilsTest {

    /**
     * Test of deleteFolder method, of class FileUtils.
     */
    @Test
    public void testDeleteFolder() throws Exception {
        File path = new File("./testDeleteFolder");
        File morePaths = new File(path.getCanonicalPath() + "/test1/test2/test3");
        File createFile = new File(path.getCanonicalPath() + "/test1/test.txt");
        
        boolean expResult = true;
        
        path.mkdir();
        morePaths.mkdirs();
        createFile.createNewFile();
        
        boolean result = FileUtils.deleteFolder(path);
        
        assertEquals(expResult, result);
        assertFalse(path.exists());
    }
    
}
