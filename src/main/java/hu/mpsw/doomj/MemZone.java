/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author mp
 */
public class MemZone {
    static final int SIZEOF = 12;
    // total bytes malloced, including header
    int size;

    MemBlock rover;
    List<MemBlock> memblocks = new LinkedList<>();
    ListIterator<MemBlock> zoneIterator = memblocks.listIterator();
    
}
