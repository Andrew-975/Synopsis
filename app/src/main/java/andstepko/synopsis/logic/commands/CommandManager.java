package andstepko.synopsis.logic.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andstepko on 02.11.15.
 */
public class CommandManager {
    private static CommandManager ourInstance = new CommandManager();

    public static CommandManager getInstance() {
        return ourInstance;
    }

    private List<Command> commandStack = new ArrayList<Command>();
    private int currentIndex = 0;

    private CommandManager() {
    }

    public boolean execute(Command command){

        if(command.isStuckable()) {
            // Remove all the following commands in stack.
            while(currentIndex < commandStack.size()){
                // Have some commands to remove.
                commandStack.remove(currentIndex);
            }

            commandStack.add(command);
            currentIndex++;
        }
        return command.execute();
    }

    public boolean stepBack(){
        if((commandStack.size() == 0) || (currentIndex <= 0)){
            return false;
        }

        currentIndex--;
        Command lastCommand = commandStack.get(currentIndex);
        return lastCommand.unexecute();
    }

    public boolean stepForward(){
        if(commandStack.size() <= currentIndex){
            // We are probably on the last command.
            return false;
        }

        // Have a command to execute it back.
        boolean result = commandStack.get(currentIndex).execute();
        currentIndex++;

        return result;
    }
}