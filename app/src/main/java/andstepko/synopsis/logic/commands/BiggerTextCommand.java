package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 13.11.15.
 */
public class BiggerTextCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        synopsisMainActivity.logRecord(Float.toString(synopsisMainActivity.getTextField().getTextSize()));
        synopsisMainActivity.getTextField().setTextSize(synopsisMainActivity.getTextField().getTextSize() * 2);
        return true;
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
