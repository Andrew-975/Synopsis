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
            .getExternalStorageDirectory().getPath() + R.string.app_name);

    private File DefaultFilesDirectory;
    private List<Shortcut> shortcutList;

    public List<Shortcut> getShortcutList() {
        return shortcutList;
    }

    public Preferences() {
        DefaultFilesDirectory = DEFAULT_FILES_DIRECTORY;
        shortcutList = new ArrayList<Shortcut>();
        shortcutList.add(new Shortcut(ShortcutNames.DELETE_PREVIOUS_WORD,
                new KeyCombination(KeyEvent.KEYCODE_DEL, true, false, false)));
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
    }
}
