package andstepko.synopsis.logic;

import java.util.ArrayList;

import andstepko.synopsis.MainActivity;

/**
 * Created by andrew on 14.09.15.
 */
public class ShortcutSet {

    private ArrayList<Shortcut> shortcuts;

    {
        shortcuts = new ArrayList<Shortcut>();
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

    public int getShortcutIndex(String shortcutName){
        Shortcut shortcut = getShortcut(shortcutName);
        if(shortcut == null){
            return -1;
        }
        return shortcuts.indexOf(shortcut);
    }

    public int getShortcutIndex(KeyCombination keyCombination){
        Shortcut shortcut = getShortcut(keyCombination);
        if(shortcut == null){
            return -1;
        }
        return shortcuts.indexOf(shortcut);
    }

    public String getShortcutName(KeyCombination keyCombination){
        Shortcut shortcut = getShortcut(keyCombination);
        if(shortcut == null){
            return null;
        }
        return shortcut.getName();
    }

    public KeyCombination getShortcutCombination(String shortcutName){
        Shortcut shortcut = getShortcut(shortcutName);
        if(shortcut == null){
            return null;
        }
        return shortcut.getKeyCombination();
    }

    public boolean addShortcut(Shortcut shortcut){
        if((getShortcut(shortcut.getName()) != null)
                || (getShortcut(shortcut.getKeyCombination()) != null)){
            return false;
        }

        shortcuts.add(shortcut);
        return true;
    }
    public boolean addShortcut(String name, KeyCombination keyCombination){
        return addShortcut(new Shortcut(name, keyCombination));
    }
    public boolean addShortcut(KeyCombination keyCombination, String name){
        return addShortcut(name, keyCombination);
    }

    public boolean removeShortcut(Shortcut shortcut){
        return shortcuts.remove(shortcut);
    }
}