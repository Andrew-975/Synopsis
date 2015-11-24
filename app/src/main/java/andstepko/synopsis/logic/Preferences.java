package andstepko.synopsis.logic;

import android.os.Environment;
import android.view.KeyEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import andstepko.synopsis.R;

/**
 * Created by andstepko on 02.11.15.
 */
public class Preferences {

    public static final File DEFAULT_FILES_DIRECTORY = new File(Environment
            .getExternalStorageDirectory().getPath() + "/Synopsis");

    private File defaultFilesDirectory;
    private List<Shortcut> shortcutList;

    public File getDefaultFilesDirectory() {
        return defaultFilesDirectory;
    }

    public List<Shortcut> getShortcutList() {
        return shortcutList;
    }

    public Preferences() {
        defaultFilesDirectory = DEFAULT_FILES_DIRECTORY;

        if (!defaultFilesDirectory.exists()) {
            defaultFilesDirectory.mkdir();
        }


        fillShortcutListWithDefaults();
    }

    private void fillShortcutListWithDefaults(){
        shortcutList = new ArrayList<Shortcut>();

        shortcutList.add(new Shortcut(ShortcutNames.DELETE_PREVIOUS_WORD,
                new KeyCombination(KeyEvent.KEYCODE_DEL, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.DELETE_PREVIOUS_LETTER,
                new KeyCombination(KeyEvent.KEYCODE_DEL, false, false, false)));

        shortcutList.add(new Shortcut(ShortcutNames.DELETE_NEXT_WORD,
                new KeyCombination(KeyEvent.KEYCODE_FORWARD_DEL, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.TEST,
                new KeyCombination(KeyEvent.KEYCODE_T, true, false, false)));

        shortcutList.add(new Shortcut(ShortcutNames.CANCEL,
                new KeyCombination(KeyEvent.KEYCODE_Z, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.UNCANCEL,
                new KeyCombination(KeyEvent.KEYCODE_Z, true, false, true)));

        shortcutList.add(new Shortcut(ShortcutNames.COPY,
                new KeyCombination(KeyEvent.KEYCODE_C, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.PASTE,
                new KeyCombination(KeyEvent.KEYCODE_V, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.CUT,
                new KeyCombination(KeyEvent.KEYCODE_X, true, false, false)));

        shortcutList.add(new Shortcut(ShortcutNames.SAVE_FILE,
                new KeyCombination(KeyEvent.KEYCODE_S, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.SAVE_FILE_AS,
                new KeyCombination(KeyEvent.KEYCODE_S, true, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.NEW_FILE,
                new KeyCombination(KeyEvent.KEYCODE_N, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.OPEN_FILE,
                new KeyCombination(KeyEvent.KEYCODE_O, true, false, false)));


        shortcutList.add(new Shortcut(ShortcutNames.SELECT_ALL,
                new KeyCombination(KeyEvent.KEYCODE_A, true, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_NEXT_LETTER,
                new KeyCombination(KeyEvent.KEYCODE_DPAD_RIGHT, false, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_NEXT_WORD,
                new KeyCombination(KeyEvent.KEYCODE_DPAD_RIGHT, true, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_PREVIOUS_LETTER,
                new KeyCombination(KeyEvent.KEYCODE_DPAD_LEFT, false, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_PREVIOUS_WORD,
                new KeyCombination(KeyEvent.KEYCODE_DPAD_LEFT, true, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_TO_LINE_START,
                new KeyCombination(KeyEvent.KEYCODE_MOVE_HOME, false, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_TO_LINE_END,
                new KeyCombination(KeyEvent.KEYCODE_MOVE_END, false, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_TO_START,
                new KeyCombination(KeyEvent.KEYCODE_MOVE_HOME, true, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_TO_END,
                new KeyCombination(KeyEvent.KEYCODE_MOVE_END, true, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_NEXT_PAGE,
                new KeyCombination(KeyEvent.KEYCODE_PAGE_DOWN, false, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SELECT_PREVIOUS_PAGE,
                new KeyCombination(KeyEvent.KEYCODE_PAGE_UP, false, false, true)));

        shortcutList.add(new Shortcut(ShortcutNames.NEW_LINE,
                new KeyCombination(KeyEvent.KEYCODE_ENTER, false, false, false)));
        shortcutList.add(new Shortcut(ShortcutNames.NEW_LINE_2,
                new KeyCombination(KeyEvent.KEYCODE_ENTER, false, false, true)));

        shortcutList.add(new Shortcut(ShortcutNames.TAB,
                new KeyCombination(KeyEvent.KEYCODE_TAB, false, false, false)));

        shortcutList.add(new Shortcut(ShortcutNames.BIGGER_TEXT,
                new KeyCombination(KeyEvent.KEYCODE_NUMPAD_ADD, true, false, true)));
        shortcutList.add(new Shortcut(ShortcutNames.SMALLER_TEXT,
                new KeyCombination(KeyEvent.KEYCODE_NUMPAD_SUBTRACT, true, false, true)));

        shortcutList.add(new Shortcut(ShortcutNames.FIND,
                new KeyCombination(KeyEvent.KEYCODE_F, true, false, false)));
    }
}
