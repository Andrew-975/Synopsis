package andstepko.synopsis.logic;

import java.util.Collection;
import java.util.HashMap;

import andstepko.synopsis.logic.commands.Command;
import andstepko.synopsis.logic.commands.DeleteNextWordCommand;
import andstepko.synopsis.logic.commands.DeletePreviousWordCommand;
import andstepko.synopsis.logic.commands.TestCommand;

/**
 * Created by andrew on 13.09.15.
 */
public class ApplicationPreferences {

    private static HashMap<String, Command> commandHashMap;
    static {
        commandHashMap = new HashMap<String, Command>();
        commandHashMap.put(ShortcutNames.DELETE_NEXT_WORD, new DeleteNextWordCommand());
        commandHashMap.put(ShortcutNames.DELETE_PREVIOUS_WORD, new DeletePreviousWordCommand());
        commandHashMap.put(ShortcutNames.TEST, new TestCommand());
    }

    private static ApplicationPreferences ourInstance = new ApplicationPreferences();

    public static ApplicationPreferences getInstance(){
        return ourInstance;
    }

    // Non-static
    private Preferences preferences;
    private HashMap<KeyCombination, Command> shortcutHashMap = new HashMap<KeyCombination, Command>();

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

    public Command getCommand(KeyCombination keyCombination){
        Command command = shortcutHashMap.get(keyCombination);
        return command;
    }

    private int instantiateAppShortcuts(Collection<Shortcut> shortcuts){

        int count = 0;

        for(Shortcut shortcut : shortcuts){
            Command command = commandHashMap.get(shortcut.getName());
            if(command != null){
                // Such an appShortcut exists in the application.
                shortcutHashMap.put(shortcut.getKeyCombination(), command);
                count++;
            }
        }
        return count;
    }
}