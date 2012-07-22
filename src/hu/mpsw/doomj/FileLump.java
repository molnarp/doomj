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
class FileLump {
  static final int SIZEOF = 12;
  
  int filepos;
  int size;
  String name;  
  
  void read(InputStream in) throws IOException {      
    filepos = EndianUtils.readSwappedInteger(in);
    size = EndianUtils.readSwappedInteger(in);
    
    byte[] bytes = new byte[8];
    in.read(bytes);
    name = new String(bytes, "US-ASCII");
  }
}
