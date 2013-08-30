/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author mp
 */
public class MemBlock {
    int size;	// including the header and possibly tiny fragments
    byte[] user;	// NULL if a free block
    int tag;	// purgelevel
    int id;	// should be ZONEID
    boolean owned;
    
    public static final int SIZEOF = 24;
}
