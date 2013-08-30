/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author Peter
 */
public class DoomDef {
  // If rangecheck is undefined,
  // most parameter validation debugging code will not be compiled
  static final boolean RANGECHECK = true;

  // Do or do not use external soundserver.
  // The sndserver binary to be run separately
  //  has been introduced by Dave Taylor.
  // The integrated sound support is experimental,
  //  and unfinished. Default is synchronous.
  // Experimental asynchronous timer based is
  //  handled by SNDINTR. 
  static final int SNDSERV = 1;
  //static final int SNDINTR  1

  // This one switches between MIT SHM (no proper mouse)
  // and XFree86 DGA (mickey sampling). The original
  // linuxdoom used SHM, which is default.
  //static final int X11_DGA		1

  //
  // For resize of screen, at start of game.
  // It will not work dynamically, see visplanes.
  //
  static final int	BASE_WIDTH = 320;

  // It is educational but futile to change this
  //  scaling e.g. to 2. Drawing of status bar,
  //  menues etc. is tied to the scale implied
  //  by the graphics.
  static final int SCREEN_MUL	= 1;
  static final double INV_ASPECT_RATIO = 0.625;

  // Defines suck. C sucks.
  // C++ might sucks for OOP, but it sure is a better C.
  // So there.
  static final int SCREENWIDTH = 320;
  //SCREEN_MUL*BASE_WIDTH //320
  static final int SCREENHEIGHT = 200;
  //(int)(SCREEN_MUL*BASE_WIDTH*INV_ASPECT_RATIO) //200




  // The maximum number of players, multiplayer/networking.
  static final int MAXPLAYERS = 4;

  // State updates, number of tics / second.
  static final int TICRATE = 35;
  
  // Skill flags.
  static final int MTF_EASY	= 1;
  static final int MTF_NORMAL = 2;
  static final int MTF_HARD = 4;

  // Deaf monsters/do not react to sound.
  static final int MTF_AMBUSH = 8;

  static final int KEY_RIGHTARROW =	0xae;
  static final int KEY_LEFTARROW = 0xac;
  static final int KEY_UPARROW = 0xad;
  static final int KEY_DOWNARROW = 0xaf;
  static final int KEY_ESCAPE = 27;
  static final int KEY_ENTER = 13;
  static final int KEY_TAB = 9;
  static final int KEY_F1 = (0x80+0x3b);
  static final int KEY_F2 = (0x80+0x3c);
  static final int KEY_F3 = (0x80+0x3d);
  static final int KEY_F4 = (0x80+0x3e);
  static final int KEY_F5 = (0x80+0x3f);
  static final int KEY_F6 = (0x80+0x40);
  static final int KEY_F7 = (0x80+0x41);
  static final int KEY_F8 = (0x80+0x42);
  static final int KEY_F9 = (0x80+0x43);
  static final int KEY_F10 = (0x80+0x44);
  static final int KEY_F11 = (0x80+0x57);
  static final int KEY_F12 = (0x80+0x58);

  static final int KEY_BACKSPACE = 127;
  static final int KEY_PAUSE = 0xff;

  static final int KEY_EQUALS = 0x3d;
  static final int KEY_MINUS = 0x2d;

  static final int KEY_RSHIFT = (0x80+0x36);
  static final int KEY_RCTRL = (0x80+0x1d);
  static final int KEY_RALT = (0x80+0x38);
  static final int KEY_LALT	= DoomDef.KEY_RALT;
}
