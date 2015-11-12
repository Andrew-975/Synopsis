package andstepko.synopsis.logic.commands;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import andstepko.synopsis.MainActivity;
import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 02.11.15.
 */
public class CommandManager {
    private static CommandManager ourInstance = new CommandManager(MainActivity.getInstance());

    public static CommandManager getInstance() {
        return ourInstance;
    }

    private List<Command> commandStack = new ArrayList<Command>();
    private int currentCommandIndex = 0;
    private SynopsisMainActivity synopsisMainActivity;

    private CommandManager(SynopsisMainActivity synopsisMainActivity) {
        this.synopsisMainActivity = synopsisMainActivity;
    }

    public boolean execute(Command command){

        boolean result = command.execute(synopsisMainActivity);

        if(command.isUnexecutable()) {
            // Must save the command in the stack somehow.

            // At first: remove all the following commands in stack.
            while(currentCommandIndex < commandStack.size()){
                // Have some commands to remove.
                commandStack.remove(currentCommandIndex);
            }

            if((currentCommandIndex > 0) &&
                        (commandStack.get(currentCommandIndex - 1) instanceof StorableCommand)){
                // Previous command is storable.
                StorableCommand previousStorableCommand =
                        (StorableCommand)commandStack.get(currentCommandIndex - 1);

                if(command instanceof StorableCommand){
                    // Executing command is storable.
                    StorableCommand executingStorableCommand = (StorableCommand) command;

                    if(executingStorableCommand.getType() == previousStorableCommand.getType()){
                        previousStorableCommand.store(executingStorableCommand);
                        return result;
                    }
                }
            }

            // No previous command or it's not storable || non-storable command is executing ||
            // previous storable command and executing storable command are different types ||
            commandStack.add(command);
            currentCommandIndex++;
        }
        return result;
    }

    public boolean stepBack(){
        if((commandStack.size() == 0) || (currentCommandIndex <= 0)){
            return false;
        }

        currentCommandIndex--;
        Command lastCommand = commandStack.get(currentCommandIndex);
        if(lastCommand instanceof EmptySeparatorCommand){
            commandStack.remove(currentCommandIndex);
            stepBack();
        }
        return lastCommand.unexecute(synopsisMainActivity);
    }

    public boolean stepForward(){
        if(commandStack.size() <= currentCommandIndex){
            // We are probably on the last command.
            return false;
        }

        // Have a command to execute it back.
        boolean result = commandStack.get(currentCommandIndex).execute(synopsisMainActivity);
        currentCommandIndex++;

        return result;
    }
}