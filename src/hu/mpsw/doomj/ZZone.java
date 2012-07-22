package hu.mpsw.doomj;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
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
}
