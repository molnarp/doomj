/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author mp
 */
public class Anim {
    AnimType type;

    // period in tics between animations
    int period;

    // number of animation frames
    int nanims;

    // location of animation
    Point loc;

    // ALWAYS: n/a,
    // RANDOM: period deviation (<256),
    // LEVEL: level
    int data1;

    // ALWAYS: n/a,
    // RANDOM: random base period,
    // LEVEL: n/a
    int data2; 

    // actual graphics for frames of animations
    Patch[] p; 

    // following must be initialized to zero before use!

    // next value of bcnt (used in conjunction with period)
    int nexttic;

    // last drawn animation frame
    int lastdrawn;

    // next frame number to animate
    int ctr;
    
    // used by RANDOM and LEVEL when animating
    int state;

    public Anim() {
    }

    public Anim(AnimType type, int period, int nanims, Point loc) {
        this.type = type;
        this.period = period;
        this.nanims = nanims;
        this.loc = loc;
    }

    public Anim(AnimType type, int period, int nanims, Point loc, int data1) {
        this.type = type;
        this.period = period;
        this.nanims = nanims;
        this.loc = loc;
        this.data1 = data1;
    }
}
