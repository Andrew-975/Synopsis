package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andrew on 26.09.15.
 */
public interface Command {
    public abstract boolean execute(SynopsisMainActivity synopsisMainActivity);

    public abstract boolean unexecute(SynopsisMainActivity synopsisMainActivity);

    boolean isStuckable();
}
