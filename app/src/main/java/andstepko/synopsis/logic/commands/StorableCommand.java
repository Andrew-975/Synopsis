package andstepko.synopsis.logic.commands;

/**
 * Created by andstepko on 09.11.15.
 */
public interface StorableCommand extends Command {

    int TYPE_EMPTY_SEPARATOR = 10;

    int TYPE_LETTER = 101;
    int TYPE_TAB = 104;
    int TYPE_DELETE_PREVIOUS = 105;
    int TYPE_DELETE_NEXT = 106;

    int getType();

    boolean store(StorableCommand sCommand);
}
