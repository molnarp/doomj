/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author mp
 */
public class WiStuff {

    //
    // Data needed to add patches to full screen intermission pics.
    // Patches are statistics messages, and animations.
    // Loads of by-pixel layout and placement, offsets etc.
    //


    //
    // Different vetween registered DOOM (1994) and
    //  Ultimate DOOM - Final edition (retail, 1995?).
    // This is supposedly ignored for commercial
    //  release (aka DOOM II), which had 34 maps
    //  in one episode. So there.
    public static final int NUMEPISODES = 4;
    public static final int NUMMAPS = 9;


    // in tics
    //U public static final int PAUSELEN		(TICRATE*2) 
    //U public static final int SCORESTEP		100
    //U public static final int ANIMPERIOD		32
    // pixel distance from "(YOU)" to "PLAYER N"
    //U public static final int STARDIST		10 
    //U public static final int WK 1


    // GLOBAL LOCATIONS
    public static final int WI_TITLEY = 2;
    public static final int WI_SPACINGY = 33;

    // SINGPLE-PLAYER STUFF
    public static final int SP_STATSX = 50;
    public static final int SP_STATSY = 50;

    public static final int SP_TIMEX = 16;
    public static final int SP_TIMEY = DoomDef.SCREENHEIGHT - 32;


    // NET GAME STUFF
    public static final int NG_STATSY = 50;
    //public static final int NG_STATSX = (32 + SHORT(star.width)/2 + 32*!dofrags);

    public static final int NG_SPACINGX = 64;


    // DEATHMATCH STUFF
    public static final int DM_MATRIXX = 42;
    public static final int DM_MATRIXY = 68;

    public static final int DM_SPACINGX = 40;

    public static final int DM_TOTALSX = 269;

    public static final int DM_KILLERSX = 10;
    public static final int DM_KILLERSY = 100;
    public static final int DM_VICTIMSX = 5;
    public static final int DM_VICTIMSY = 50;
    
    public static final Point[][] lnodes = new Point[][] {
        // Episode 0 World Map
        new Point[] {
            new Point(185, 164),	// location of level 0 (CJ)
            new Point(148, 143) ,	// location of level 1 (CJ)
            new Point(69, 122),	// location of level 2 (CJ)
            new Point(209, 102),	// location of level 3 (CJ)
            new Point(116, 89),	// location of level 4 (CJ)
            new Point(166, 55),	// location of level 5 (CJ)
            new Point(71, 56),	// location of level 6 (CJ)
            new Point(135, 29),	// location of level 7 (CJ)
            new Point(71, 24)	// location of level 8 (CJ)
        },

        // Episode 1 World Map should go here
        new Point[] {
            new Point(254, 25),	// location of level 0 (CJ)
            new Point(97, 50),	// location of level 1 (CJ)
            new Point(188, 64),	// location of level 2 (CJ)
            new Point(128, 78 ),	// location of level 3 (CJ)
            new Point(214, 92),	// location of level 4 (CJ)
            new Point(133, 130),	// location of level 5 (CJ)
            new Point(208, 136),	// location of level 6 (CJ)
            new Point(148, 140),	// location of level 7 (CJ)
            new Point(235, 158)	// location of level 8 (CJ)
        },

        // Episode 2 World Map should go here
        new Point[] {
            new Point(156, 168),	// location of level 0 (CJ)
            new Point(48, 154),	// location of level 1 (CJ)
            new Point(174, 95),	// location of level 2 (CJ)
            new Point(265, 75),	// location of level 3 (CJ)
            new Point(130, 48),	// location of level 4 (CJ)
            new Point(279, 23),	// location of level 5 (CJ)
            new Point(198, 48),	// location of level 6 (CJ)
            new Point(140, 25),	// location of level 7 (CJ)
            new Point(281, 136)	// location of level 8 (CJ)
        }

    };
    

    //
    // Animation locations for episode 0 (1).
    // Using patches saves a lot of space,
    //  as they replace 320x200 full screen frames.
    //
    public static final Anim[] epsd0animinfo = {
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(224, 104)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(184, 160)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(112, 136)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(72, 112)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(88, 96)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(64, 48)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(192, 40)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(136, 16)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(80, 16)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(64, 24))
    };

    public static final Anim[] epsd1animinfo = {
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 1),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 2),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 3),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 4),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 5),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 6),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 7),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 3, new Point(192, 144), 8),
        new Anim(AnimType.AnimLevel, DoomDef.TICRATE/3, 1, new Point(128, 136), 8)
    };

    public static final Anim[] epsd2animinfo = {
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(104, 168)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(40, 136)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(160, 96)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(104, 80)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/3, 3, new Point(120, 32)),
        new Anim(AnimType.AnimAlways, DoomDef.TICRATE/4, 3, new Point(40, 0))
    };    
}
