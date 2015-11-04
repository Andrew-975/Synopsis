package andstepko.synopsis.logic;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;

import andstepko.synopsis.logic.commands.CancelCommand;
import andstepko.synopsis.logic.commands.Command;
import andstepko.synopsis.logic.commands.CopyCommand;
import andstepko.synopsis.logic.commands.CutCommand;
import andstepko.synopsis.logic.commands.DeleteNextWordCommand;
import andstepko.synopsis.logic.commands.DeletePreviousWordCommand;
import andstepko.synopsis.logic.commands.PasteCommand;
import andstepko.synopsis.logic.commands.TestCommand;
import andstepko.synopsis.logic.commands.UncancelCommand;

/**
 * Created by andrew on 13.09.15.
 */
public class ApplicationPreferences {

    private static HashMap<String, Class> commandHashMap;
    static {
        commandHashMap = new HashMap<String, Class>();
        commandHashMap.put(ShortcutNames.DELETE_NEXT_WORD, DeleteNextWordCommand.class);
        commandHashMap.put(ShortcutNames.DELETE_PREVIOUS_WORD, DeletePreviousWordCommand.class);

        //commandHashMap.put(ShortcutNames.TEST, TestCommand.class);
        commandHashMap.put(ShortcutNames.TEST, DeletePreviousWordCommand.class);

        commandHashMap.put(ShortcutNames.CANCEL, CancelCommand.class);
        commandHashMap.put(ShortcutNames.UNCANCEL, UncancelCommand.class);

        commandHashMap.put(ShortcutNames.COPY, CopyCommand.class);
        commandHashMap.put(ShortcutNames.PASTE, PasteCommand.class);
        commandHashMap.put(ShortcutNames.CUT, CutCommand.class);
    }

    private static ApplicationPreferences ourInstance = new ApplicationPreferences();

    public static ApplicationPreferences getInstance(){
        return ourInstance;
    }

    // Non-static
    private Preferences preferences;
    private HashMap<KeyCombination, Class> shortcutHashMap = new HashMap<KeyCombination, Class>();

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
        instantiateAppShortcuts(preferences.getShortcutList());
    }

    // Methods
    public ApplicationPreferences(Preferences preferences){
        setPreferences(preferences);
    }

    public ApplicationPreferences(){
        this(new Preferences());
    }

    public Command getCommand(KeyCombination keyCombination) {

        Class clazz = shortcutHashMap.get(keyCombination);

        if(clazz == null){
            return null;
        }

        Command command = null;
        try {
            command = (Command)clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return command;
    }

    private int instantiateAppShortcuts(Collection<Shortcut> shortcuts){

        int count = 0;

        for(Shortcut shortcut : shortcuts){
            Class commandClass = commandHashMap.get(shortcut.getName());
            if(commandClass != null){
                // Such an appShortcut exists in the application.
                shortcutHashMap.put(shortcut.getKeyCombination(), commandClass);
                count++;
            }
        }
        return count;
    }
}