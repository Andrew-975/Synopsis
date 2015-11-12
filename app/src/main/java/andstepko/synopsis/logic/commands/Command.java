package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andrew on 26.09.15.
 */
public interface Command {
    boolean execute(SynopsisMainActivity synopsisMainActivity);

    boolean unexecute(SynopsisMainActivity synopsisMainActivity);

    boolean isUnexecutable ();
}
