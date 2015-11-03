package andstepko.synopsis.logic;

/**
 * Created by andrew on 14.09.15.
 */
public class Shortcut {

    private String name;
    private KeyCombination keyCombination;

    public String getName() {
        return name;
    }

    public KeyCombination getKeyCombination() {
        return keyCombination;
    }

    public void setKeyCombination(KeyCombination keyCombination) {
        this.keyCombination = keyCombination;
    }

    public Shortcut(){}

    public Shortcut(String name, KeyCombination keyCombination) {
        this.name = name;
        this.keyCombination = keyCombination;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        Shortcut otherShortcut = (Shortcut) o;
        return (this.getName().equals(otherShortcut.getName()))
                && (this.getKeyCombination().equals(otherShortcut.getKeyCombination()));
    }

    @Override
    public int hashCode() {

        //TODO
        return super.hashCode();
    }

    @Override
    public String toString() {
        return getName() + " " + keyCombination.toString();
    }
}
