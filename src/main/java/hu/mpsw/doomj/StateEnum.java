/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.mpsw.doomj;

/**
 *
 * @author mp
 */
public enum StateEnum {
    NoState(-1),
    StatCount(1),
    ShowNextLoc(2);
    
    private final int value;

    private StateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
