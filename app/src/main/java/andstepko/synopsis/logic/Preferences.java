package andstepko.synopsis.logic;

import android.content.res.Resources;
import android.os.Environment;
import android.view.KeyEvent;

import java.io.File;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import andstepko.synopsis.MainActivity;
import andstepko.synopsis.R;

/**
 * Created by andrew on 13.09.15.
 */
public class Preferences {

    public static Preferences INSTANCE;

    public static final File DEFAULT_FILES_DIRECTORY = new File(Environment
            .getExternalStorageDirectory().getPath() + R.string.app_name);

    static {
        INSTANCE = new Preferences();
    }

    // Non-static
    public File DefaultFilesDirectory;

    public ShortcutSet shortcuts;

    // Methods
    public Preferences(){
        DefaultFilesDirectory = DEFAULT_FILES_DIRECTORY;
        shortcuts = new ShortcutSet();
        shortcuts.addShortcut(new Shortcut(getString(R.string.delete_word),
                new KeyCombination(KeyEvent.KEYCODE_DEL, true, false, false)));
        shortcuts.addShortcut(new Shortcut(getString(R.string.delete_forward_word),
                new KeyCombination(KeyEvent.KEYCODE_FORWARD_DEL, true, false, false)));
    }

    private String getString(int id){
        return MainActivity.getContext().getString(id);
    }
}