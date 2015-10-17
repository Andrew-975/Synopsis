package andstepko.synopsis.logic;

import android.view.KeyEvent;

/**
 * Created by andrew on 13.09.15.
 */
public class KeyCombination {

    private int keyCode;
    private boolean control;
    private boolean alt;
    private boolean shift;

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isControl() {
        return control;
    }

    public boolean isAlt() {
        return alt;
    }

    public boolean isShift() {
        return shift;
    }

    public KeyCombination(int keyCode, boolean control, boolean alt, boolean shift) {
        this.keyCode = keyCode;
        this.control = control;
        this.alt = alt;
        this.shift = shift;
    }

    public KeyCombination(int keyCode, KeyEvent keyEvent){
        this(keyCode, keyEvent.isCtrlPressed(), keyEvent.isAltPressed(), keyEvent.isShiftPressed());
    }

//    public boolean isValid(){
//        return control || alt || shift;
//    }
//
//    public static boolean isValid(KeyEvent keyEvent){
//        return keyEvent.isCtrlPressed() || keyEvent.isAltPressed() || keyEvent.isShiftPressed();
//    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        KeyCombination otherKC = (KeyCombination) o;
        return (this.keyCode == otherKC.keyCode) && (this.control == otherKC.control) && (this.alt == otherKC.alt)
                && (this.shift == otherKC.shift);
    }

    @Override
    public int hashCode() {
        int result = keyCode;
        if(control)
            result += 1000;
        if(alt)
            result += 3000;
        if(shift)
            result += 5000;

        return result;
    }

    @Override
    public String toString() {
        String result = new String();

        if(control){
            result += "Ctrl+";
        }
        if(alt){
            result += "Alt+";
        }
        if(shift){
            result += "Shift+";
        }
        result += KeyEvent.keyCodeToString(keyCode);
        return result;
    }
}