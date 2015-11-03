package andstepko.synopsis;

import android.app.Activity;
import android.widget.EditText;

import andstepko.synopsis.logic.commands.CommandManager;

/**
 * Created by andstepko on 03.11.15.
 */
public interface SynopsisMainActivity {

    EditText getTextField();

    CommandManager getCommandManager();

}
