/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author Peter
 */
public class ISystem {
    
    public static final int MB_USED = 6;

    static void iError(String... error) {
    }
    
    static int toInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                    String.format("Long variable is too large to convert to int: %1$d", l));
        }
        
        return (int) l;
    }
}
