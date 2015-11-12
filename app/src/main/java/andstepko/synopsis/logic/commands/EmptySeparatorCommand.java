package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 10.11.15.
 */
public class EmptySeparatorCommand implements StorableCommand {

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
        return true;
    }

    @Override
    public int getType() {
        return TYPE_EMPTY_SEPARATOR;
    }

    @Override
    public boolean store(StorableCommand sCommand) {
        return false;
    }
}
