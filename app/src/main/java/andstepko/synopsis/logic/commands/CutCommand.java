package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 04.11.15.
 */
public class CutCommand implements Command {
    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        return false;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        return false;
    }

    @Override
    public boolean isStuckable() {
        return false;
    }
}
