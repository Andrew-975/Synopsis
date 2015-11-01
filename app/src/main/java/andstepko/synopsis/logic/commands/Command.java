package andstepko.synopsis.logic.commands;

/**
 * Created by andrew on 26.09.15.
 */
public abstract class Command {
    public abstract boolean execute();

    public abstract boolean unexecute();

    boolean isStuckable(){
        return false;
    }
}
