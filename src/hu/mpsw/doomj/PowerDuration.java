/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author Peter
 */
enum PowerDuration {
  INVULNTICS(30 * DoomDef.TICRATE),
  INVISTICS(60 * DoomDef.TICRATE),
  INFRATICS(120 * DoomDef.TICRATE),
  IRONTICS(60 * DoomDef.TICRATE);

  private final int length;

  PowerDuration(int length) {
    this.length = length;
  }

  public int getDuration() {
    return length;
  }
}
