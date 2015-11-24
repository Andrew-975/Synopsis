package andstepko.synopsis.logic;

import java.util.Collection;
import java.util.HashMap;

import andstepko.synopsis.logic.commands.BiggerTextCommand;
import andstepko.synopsis.logic.commands.CancelCommand;
import andstepko.synopsis.logic.commands.Command;
import andstepko.synopsis.logic.commands.CopyCommand;
import andstepko.synopsis.logic.commands.CutCommand;
import andstepko.synopsis.logic.commands.DeleteNextWordCommand;
import andstepko.synopsis.logic.commands.DeletePreviousLetterCommand;
import andstepko.synopsis.logic.commands.DeletePreviousWordCommand;
import andstepko.synopsis.logic.commands.EmptySeparatorCommand;
import andstepko.synopsis.logic.commands.FindCommand;
import andstepko.synopsis.logic.commands.NewFileCommand;
import andstepko.synopsis.logic.commands.NewLineCommand;
import andstepko.synopsis.logic.commands.OpenFileCommand;
import andstepko.synopsis.logic.commands.PasteCommand;
import andstepko.synopsis.logic.commands.SaveFileAsCommand;
import andstepko.synopsis.logic.commands.SaveFileCommand;
import andstepko.synopsis.logic.commands.SmallerTextCommand;
import andstepko.synopsis.logic.commands.TabCommand;
import andstepko.synopsis.logic.commands.TypeLetterCommand;
import andstepko.synopsis.logic.commands.UncancelCommand;

/**
 * Created by andrew on 13.09.15.
 */
public class ApplicationPreferences {

    public static char SPACE = ' ';
    public static char TAB = '\t';
    public static String TAB_STRING = "\t";
    public static char EOL = '\n';
    public static int SPACES_FOR_TAB = 3;

    private static HashMap<String, Class> commandHashMap;
    private static int[] letterCodes;
    static {
        commandHashMap = new HashMap<String, Class>();

        commandHashMap.put(ShortcutNames.EMPTY_SEPARATOR, EmptySeparatorCommand.class);

        commandHashMap.put(ShortcutNames.DELETE_NEXT_WORD, DeleteNextWordCommand.class);
        commandHashMap.put(ShortcutNames.DELETE_PREVIOUS_WORD, DeletePreviousWordCommand.class);
        commandHashMap.put(ShortcutNames.DELETE_PREVIOUS_LETTER, DeletePreviousLetterCommand.class);

        //commandHashMap.put(ShortcutNames.TEST, TestCommand.class);
        //commandHashMap.put(ShortcutNames.TEST, DeletePreviousWordCommand.class);

        commandHashMap.put(ShortcutNames.CANCEL, CancelCommand.class);
        commandHashMap.put(ShortcutNames.UNCANCEL, UncancelCommand.class);

        commandHashMap.put(ShortcutNames.COPY, CopyCommand.class);
        commandHashMap.put(ShortcutNames.PASTE, PasteCommand.class);
        commandHashMap.put(ShortcutNames.CUT, CutCommand.class);

        commandHashMap.put(ShortcutNames.SAVE_FILE, SaveFileCommand.class);
        commandHashMap.put(ShortcutNames.SAVE_FILE_AS, SaveFileAsCommand.class);
        commandHashMap.put(ShortcutNames.NEW_FILE, NewFileCommand.class);
        commandHashMap.put(ShortcutNames.OPEN_FILE, OpenFileCommand.class);

        commandHashMap.put(ShortcutNames.SELECT_ALL, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_NEXT_LETTER, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_NEXT_WORD, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_PREVIOUS_LETTER, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_PREVIOUS_WORD, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_TO_LINE_START, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_TO_LINE_END, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_TO_START, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_TO_END, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_NEXT_PAGE, EmptySeparatorCommand.class);
        commandHashMap.put(ShortcutNames.SELECT_PREVIOUS_PAGE, EmptySeparatorCommand.class);

        commandHashMap.put(ShortcutNames.NEW_LINE, NewLineCommand.class);
        commandHashMap.put(ShortcutNames.NEW_LINE_2, NewLineCommand.class);

        commandHashMap.put(ShortcutNames.TAB, TabCommand.class);

        commandHashMap.put(ShortcutNames.BIGGER_TEXT, BiggerTextCommand.class);
        commandHashMap.put(ShortcutNames.SMALLER_TEXT, SmallerTextCommand.class);

        commandHashMap.put(ShortcutNames.FIND, FindCommand.class);

        // Letters
        letterCodes = new int[] {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
                62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 142,
                144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159};
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
            if(!(keyCombination.isControl() | keyCombination.isAlt() | keyCombination.isShift())){
                // No special button pressed.
                int keyCode = keyCombination.getKeyCode();
                for (int i = 0; i < letterCodes.length; i++) {
                    if (letterCodes[i] == keyCode) {
                        return new TypeLetterCommand(keyCode);
                    }
                }
                return null;
            }
            else{
                // At least 1 special button pressed(ctrl, alt, shift).
                return null;
            }
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