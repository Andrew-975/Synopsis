package andstepko.synopsis.logic.commands;

import andstepko.synopsis.logic.Shortcut;

/**
 * Created by andstepko on 01.11.15.
 */
public class CommandBank {
    private static CommandBank ourInstance = new CommandBank();

    public static CommandBank getInstance() {
        return ourInstance;
    }

    private CommandBank() {
    }

    public Command getCommand(Shortcut shortcut){
        // TODO
        return new DeletePreviousWordCommand();
        //return new DeleteForwardWordCommand();
    }
}
