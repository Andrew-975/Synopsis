package andstepko.synopsis.logic.commands;

import android.text.Editable;

import andstepko.synopsis.MainActivity;
import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 02.11.15.
 */
public class TestCommand implements Command{
    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        //return false;

        Editable editable = synopsisMainActivity.getTextField().getEditableText();

        if(editable.length() <= 0){
            return false;
        }

        editable.insert(1, "HOORAY");
        return true;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        synopsisMainActivity.getTextField().getEditableText().delete(1, 7);
        return true;
    }

    @Override
    public boolean isUnexecutable() {
        return true;
    }
}