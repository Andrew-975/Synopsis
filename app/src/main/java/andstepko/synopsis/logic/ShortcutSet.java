package andstepko.synopsis.logic;

import java.util.ArrayList;
import java.util.Collection;

import andstepko.synopsis.MainActivity;

/**
 * Created by andrew on 14.09.15.
 */
public class ShortcutSet {

    private Collection<Shortcut> shortcuts;

    {
        shortcuts = new ArrayList<Shortcut>();
    }

    public boolean contains(Shortcut shortcut){
        return shortcuts.contains(shortcut);
    }

    public Shortcut getShortcut(String shortcutName){
        for(Shortcut shortcut : shortcuts){
            if(shortcutName.equals(shortcut.getName())){
                return shortcut;
            }
        }
        return null;
    }

    public Shortcut getShortcut(KeyCombination keyCombination){
        for(Shortcut shortcut : shortcuts){
            if(keyCombination.equals(shortcut.getKeyCombination())){
                return shortcut;
            }
        }
        return null;
    }

    public boolean addShortcut(Shortcut shortcut){
        if((getShortcut(shortcut.getName()) != null)
                || (getShortcut(shortcut.getKeyCombination()) != null)){
            return false;
        }

        shortcuts.add(shortcut);
        return true;
    }

    public boolean removeShortcut(Shortcut shortcut){
        return shortcuts.remove(shortcut);
    }
}