/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

import java.io.File;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class WWadTest {
    
    public WWadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

    /**
     * Test of strupr method, of class WWad.
     */
    @Test
    public void testStrupr() {
        StringBuilder s = new StringBuilder();
        s.append("abc123.+/*");
        WWad.strupr(s);
        
        Assert.assertEquals("ABC123.+/*", s.toString());
        
    }

    /**
     * Test of filelength method, of class WWad.
     */
    @Test
    public void testFilelength() {
        File f = new File("src/test/resources/test.txt");
        long expResult = 5L;
        long result = WWad.filelength(f);
        assertEquals(expResult, result);
    }

    /**
     * Test of extractFileBase method, of class WWad.
     */
    @Test
    public void testExtractFileBase() {
        System.out.println("extractFileBase");
        assertEquals("some.file", WWad.extractFileBase("/a/b/some.file.txt", ""));
        assertEquals("some.file", WWad.extractFileBase("c:\\a\\b\\some.file.txt", ""));
    }

    /**
     * Test of wAddFile method, of class WWad.
     */
    @Test
    public void testWAddFile() throws Exception {
        System.out.println("wAddFile");
        String filename = "";
        WWad.wAddFile(filename);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wReload method, of class WWad.
     */
    @Test
    public void testWReload() throws Exception {
        System.out.println("wReload");
        WWad instance = new WWad();
        instance.wReload();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wInitMultipleFiles method, of class WWad.
     */
    @Test
    public void testWInitMultipleFiles() throws Exception {
        System.out.println("wInitMultipleFiles");
        String[] filenames = null;
        WWad instance = new WWad();
        instance.wInitMultipleFiles(filenames);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of W_InitFile method, of class WWad.
     */
    @Test
    public void testW_InitFile() throws Exception {
        System.out.println("W_InitFile");
        String filename = "";
        WWad instance = new WWad();
        instance.W_InitFile(filename);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wNumLumps method, of class WWad.
     */
    @Test
    public void testWNumLumps() {
        System.out.println("wNumLumps");
        WWad instance = new WWad();
        int expResult = 0;
        int result = instance.wNumLumps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wCheckNumForName method, of class WWad.
     */
    @Test
    public void testWCheckNumForName() {
        System.out.println("wCheckNumForName");
        String name = "";
        WWad instance = new WWad();
        int expResult = 0;
        int result = instance.wCheckNumForName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wGetNumForName method, of class WWad.
     */
    @Test
    public void testWGetNumForName() {
        System.out.println("wGetNumForName");
        String name = "";
        WWad instance = new WWad();
        int expResult = 0;
        int result = instance.wGetNumForName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wLumpLength method, of class WWad.
     */
    @Test
    public void testWLumpLength() {
        System.out.println("wLumpLength");
        int lump = 0;
        WWad instance = new WWad();
        int expResult = 0;
        int result = instance.wLumpLength(lump);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wReadLump method, of class WWad.
     */
    @Test
    public void testWReadLump() throws Exception {
        System.out.println("wReadLump");
        int lump = 0;
        byte[] dest = null;
        WWad instance = new WWad();
        instance.wReadLump(lump, dest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wCacheLumpNum method, of class WWad.
     */
    @Test
    public void testWCacheLumpNum() throws Exception {
        System.out.println("wCacheLumpNum");
        int lump = 0;
        int tag = 0;
        WWad instance = new WWad();
        byte[] expResult = null;
        byte[] result = instance.wCacheLumpNum(lump, tag);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of wCacheLumpName method, of class WWad.
     */
    @Test
    public void testWCacheLumpName() throws Exception {
        System.out.println("wCacheLumpName");
        String name = "";
        int tag = 0;
        WWad instance = new WWad();
        byte[] expResult = null;
        byte[] result = instance.wCacheLumpName(name, tag);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of W_Profile method, of class WWad.
     */
    @Test
    public void testW_Profile() throws Exception {
        System.out.println("W_Profile");
        WWad instance = new WWad();
        instance.W_Profile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}