/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.EndianUtils;

/**
 *
 * @author Peter
 */
class WadInfo {
  static final int SIZEOF = 12;
  
  // Should be "IWAD" or "PWAD".
  String identification;
  int numlumps;
  int infotableofs;

  void read(InputStream in) throws IOException {      
    byte[] bytes = new byte[4];

    identification = new String(bytes, "US-ASCII");
    numlumps = EndianUtils.readSwappedInteger(in);
    infotableofs = EndianUtils.readSwappedInteger(in);
  }
}
