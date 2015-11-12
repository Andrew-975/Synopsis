package andstepko.synopsis;

import android.app.Activity;
import android.widget.EditText;

import java.io.File;

import andstepko.synopsis.logic.commands.CommandManager;

/**
 * Created by andstepko on 03.11.15.
 */
public interface SynopsisMainActivity {

    int OPEN_FILE_REQUEST = 100;

    EditText getTextField();

    CommandManager getCommandManager();

    File getDefaultFilesDirectory();

    void logRecord(String message);
}
