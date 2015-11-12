package andstepko.synopsis.logic.commands;

import android.text.Editable;

import andstepko.synopsis.MainActivity;
import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andrew on 29.09.15.
 */
public class DeleteNextWordCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        return false;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        return false;
    }

    @Override
    public boolean isUnexecutable() {
        return false;
    }
}
