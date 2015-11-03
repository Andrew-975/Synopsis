package andstepko.synopsis.logic.commands;

import android.text.Editable;

import andstepko.synopsis.MainActivity;

/**
 * Created by andstepko on 02.11.15.
 */
public class TestCommand extends Command{
    @Override
    public boolean execute() {
        //return false;

        Editable editable = MainActivity.getInstance().mainEditText.getEditableText();

        editable.insert(1, "HOORAY");
        return true;
    }

    @Override
    public boolean unexecute() {
        return false;
    }
}
