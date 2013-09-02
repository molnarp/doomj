/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Peter Molnar <molnar.peter@sztaki.mta.hu>
 */
public class WadInfoTest {
    
    /**
     * Test of read method, of class WadInfo.
     */
    @Test
    public void testRead() throws Exception {
        WadInfo instance = new WadInfo();
        try (InputStream in = new BufferedInputStream(new FileInputStream("src/test/resources/test.wad"))) {
            instance.read(in);
        }
        
        Assert.assertEquals("Identification string match", "PWAD", instance.identification);
        Assert.assertEquals("Number of lumps", 2, instance.numlumps);
        Assert.assertEquals("Beginning of the directory", 0, instance.infotableofs);
        
    }
}