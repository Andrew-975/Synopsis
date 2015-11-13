package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 13.11.15.
 */
public abstract class TextInsertingCommand implements StorableCommand {

    protected String insertedText = "";
    protected int insertFrom;
    protected int insertionCount = 0;

    protected CharSequence removedText = "";
    protected boolean isSelectionDirect;

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        if(insertedText == ""){
            // It's the first execution, the command has just invoked.
            return simpleExecution(synopsisMainActivity);
        }
        // The command is executing back due to stepForward action.
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        int tempStart = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        int start = Math.min(tempStart, end);
        end = Math.max(tempStart, end);

        // Delete selected text.
        if((start >= 0) && (start != end)){
            removedText = editable.subSequence(start, end);
            editable.delete(start, end);
        }

        editable.insert(insertFrom, insertedText);
        editText.setSelection(insertFrom + insertedText.length());
        return true;
    }

    protected abstract boolean simpleExecution(SynopsisMainActivity synopsisMainActivity);

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {

        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        if(insertionCount == 0){
            return false;
        }

        // At first: remove typed text.
        insertedText = editable.subSequence(insertFrom, insertFrom + insertionCount).toString();
        editable.delete(insertFrom, insertFrom + insertionCount);

        // Put removed (due to selection) text back.
        editable.insert(insertFrom, removedText);
        if(isSelectionDirect) {
            editText.setSelection(insertFrom, insertFrom + removedText.length());
        }
        else{
            editText.setSelection(insertFrom + removedText.length(), insertFrom);
        }
        return true;
    }

    @Override
    public boolean isUnexecutable() {
        return true;
    }

    @Override
    public int getType() {
        return TYPE_INSERT_TEXT;
    }

    @Override
    public boolean store(StorableCommand storableCommand) {
        TextInsertingCommand typeLetterCommand = (TextInsertingCommand) storableCommand;

        //this.insertedText += typeLetterCommand.insertedText;
        insertionCount += typeLetterCommand.insertionCount;
        return true;
    }

}