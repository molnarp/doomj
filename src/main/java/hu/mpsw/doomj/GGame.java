/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author Peter
 */
public class GGame {
 
  void gDeathMatchSpawnPlayer (int playernum) {
    
  }

  void gInitNew (Skill skill, int episode, int map) {    
  }

  // Can be called by the startup code or M_Responder.
  // A normal game starts at map 1,
  // but a warp test can start elsewhere
  void gDeferedInitNew (Skill skill, int episode, int map) {}

  void gDeferedPlayDemo (String demo) {}

  // Can be called by the startup code or M_Responder,
  // calls P_SetupLevel or W_EnterWorld.
  void gLoadGame (String name) {}

  void gDoLoadGame () {}

  // Called by M_Responder.
  void gSaveGame (int slot, String description) {}

  // Only called by startup code.
  void gRecordDemo (String name) {}

  void gBeginRecording () {}

  void gPlayDemo (String name) {}
  void gTimeDemo (String name) {}
  boolean gCheckDemoStatus () {
    return false;
  }

  void gExitLevel() {}
  void gSecretExitLevel () {}

  void gWorldDone () {}

  void gTicker() {}
  boolean gResponder(Event	ev) {
    return false;
  }
  
  void gScreenShot() {}
}
