package andstepko.synopsis.logic.commands;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 03.11.15.
 */
public class UncancelCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        CommandManager commandManager = synopsisMainActivity.getCommandManager();
        return commandManager.stepForward();
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
