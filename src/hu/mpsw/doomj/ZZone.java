package hu.mpsw.doomj;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ListIterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @todo Used but purgeable memory must be addressed.
 * @author mp
 */
public class ZZone {

    public static final int PU_STATIC = 1;	// static entire execution time
    public static final int PU_SOUND = 2;	// static while playing
    public static final int PU_MUSIC = 3;	// static while playing
    public static final int PU_DAVE = 4;	// anything else Dave wants static
    public static final int PU_LEVEL = 50;	// static until level exited
    public static final int PU_LEVSPEC = 51;      // a special thinker in a level
    // Tags >= 100 are purgable whenever needed.
    public static final int PU_PURGELEVEL = 100;
    public static final int PU_CACHE = 101;
    
    public static final int MINFRAGMENT = 64;
    public static final int ZONEID = 0x1d4a11;
    
    static MemZone mainzone;
    
    
    static MemBlock zWhichBlock(byte[] p) {
        for (MemBlock block : mainzone.memblocks) {
            if (block.user == p) {
                return block;
            }
        }
        
        return null;
    }
    
    static void zChangeTag(MemBlock p, int t) {
        if (p.id != 0x1d4a11)  {
            ISystem.iError("Z_CT!");
        }
        
        zChangeTag2(p,t);
    }

    
    static void zInit() {
        mainzone = new MemZone();
    }

    static byte[] zMalloc(int size, int tag, byte[] user) {
        // scan through the block list,
        // throwing out any purgable blocks along the way.
        ListIterator<MemBlock> it = mainzone.memblocks.listIterator();
        MemBlock rover;
        while (it.hasNext()) {
            rover = it.next();
            if (rover.user != null) {
                if (rover.tag >= PU_PURGELEVEL) {
                    // free the rover block (adding the size to base)
                    it.remove();
                }
            }
        }


        // there will be a free fragment after the allocated block
        MemBlock base = new MemBlock();
        base.size = size;
        base.user = new byte[size];	
        base.tag = tag;
        base.id = ZONEID;
        
        mainzone.memblocks.add(base);
        
        return base.user;
    }
    
    static void zFree(MemBlock block) {
        if (block.id != ZONEID) {
            ISystem.iError("Z_Free: freed a pointer without ZONEID");
        }

        // mark as free
        block.user = null;	
        block.tag = 0;
        block.id = 0;
        
        mainzone.memblocks.remove(block);
    }
    
    static void zFreeTags(int lowtag, int hightag) {
        MemBlock block;

        ListIterator<MemBlock> it = mainzone.memblocks.listIterator();
        while (it.hasNext()) {
            block = it.next();
            if (block.tag >= lowtag && block.tag <= hightag) {
                
                if (block.id != ZONEID) {
                    ISystem.iError("Z_Free: freed a pointer without ZONEID");
                }
                
                // mark as free
                block.user = null;
                block.tag = 0;
                block.id = 0;
                
                it.remove();
            }
        }
    }

    static void zDumpHeap(int lowtag, int hightag) {
        System.out.printf("zone size: %i  location: -\n", mainzone.size);
        System.out.printf ("tag range: %i to %i\n", lowtag, hightag);
        
        for (MemBlock block : mainzone.memblocks) {
            if (block.tag >= lowtag && block.tag <= hightag) {
                System.out.printf ("block:%p    size:%7i    user:%p    tag:%3i\n", 
                        block, block.size, block.user, block.tag);
            }
        }        
    }

    static void zFileDumpHeap(PrintStream fout) throws FileNotFoundException {
        fout.printf("zone size: %i  location: -\n", mainzone.size);
        
        for (MemBlock block : mainzone.memblocks) {
            System.out.printf ("block:%p    size:%7i    user:%p    tag:%3i\n", 
                    block, block.size, block.user, block.tag);
        }	
    }
    
    static void zCheckHeap() {
        // nothing to do here
    }

    static void zChangeTag2(MemBlock block, int tag) {
        if (block.id != ZONEID) {
            ISystem.iError("Z_ChangeTag: freed a pointer without ZONEID");
        }

        if (tag >= PU_PURGELEVEL && block.user == null) {
            ISystem.iError ("Z_ChangeTag: an owner is required for purgable blocks");
        }

        block.tag = tag;    
    }
    
    static int zFreeMemory() {
        int free = 0;
        for (MemBlock block : mainzone.memblocks) {
            if (block.user == null || block.tag >= PU_PURGELEVEL) {
                free += block.size;
            }
        }
        
        return free;
    }
}
