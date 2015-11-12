package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 09.11.15.
 */
public abstract class DeletePreviousCommand implements StorableCommand {

    protected String removedText = "";
    protected int removedFrom;

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        if(removedText == ""){
            // It's the first execution, the command has just invoked.
            EditText editText = synopsisMainActivity.getTextField();
            int selectionStart = editText.getSelectionStart();
            int selectionEnd = editText.getSelectionEnd();

            if(selectionStart != selectionEnd){
                Editable editable = editText.getEditableText();
                removedFrom = Math.min(selectionStart, selectionEnd);
                int realSelectionEnd = Math.max(selectionStart, selectionEnd);

                removedText = editable.subSequence(removedFrom, realSelectionEnd).toString();
                editText.getEditableText().delete(removedFrom, realSelectionEnd);
                editText.setSelection(removedFrom);
                return true;
            }

            return simpleExecution(synopsisMainActivity);
        }
        // The command is executing back due to stepForward action.
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();
        editable.delete(removedFrom, removedFrom + removedText.length());
        editText.setSelection(removedFrom);
        return true;
    }

    protected abstract boolean simpleExecution(SynopsisMainActivity synopsisMainActivity);

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {

        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();
        int initialCursor = editText.getSelectionEnd();

        if(removedText.length() == 0){
            return false;
        }

        editable.insert(removedFrom, removedText);
        editText.setSelection(initialCursor + removedText.length());
        return true;
    }

    @Override
    public boolean isUnexecutable() {
        return true;
    }

    @Override
    public int getType() {
        return TYPE_DELETE_PREVIOUS;
    }

    @Override
    public boolean store(StorableCommand storableCommand) {
        DeletePreviousCommand deletePreviousCommand = (DeletePreviousCommand) storableCommand;

        if(deletePreviousCommand.removedText.length() == 0){
            return false;
        }

        removedText = deletePreviousCommand.removedText + this.removedText;
        removedFrom = deletePreviousCommand.removedFrom;
        return true;
    }
}