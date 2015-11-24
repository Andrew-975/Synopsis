package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 24.11.15.
 */
public class FindCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        //TODO
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
