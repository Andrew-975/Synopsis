package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 09.11.15.
 */
public class TypeLetterCommand implements StorableCommand{

    private int keyCode;

    private String insertedText = "";
    private int insertFrom;
    private int insertionCount = 0;

    private CharSequence removedText = "";
    private boolean isSelectionDirect;

    public TypeLetterCommand(int keyCode) {
        this.keyCode = keyCode;
    }

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

    private boolean simpleExecution(SynopsisMainActivity synopsisMainActivity){
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        int tempStart = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if(end > tempStart){
            isSelectionDirect = true;
        }
        int start = Math.min(tempStart, end);
        end = Math.max(tempStart, end);

        // At first, delete selected text.
        if((start >= 0) && (start != end)){
            removedText = editable.subSequence(start, end);
            editable.delete(start, end);
        }

        // We can't understand what char to insert, knowing only keyCode =(
        insertionCount++;
        insertFrom = start;
        return false;
    }

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
        return TYPE_LETTER;
    }

    @Override
    public boolean store(StorableCommand storableCommand) {
        TypeLetterCommand typeLetterCommand = (TypeLetterCommand) storableCommand;

        //this.insertedText += typeLetterCommand.insertedText;
        insertionCount++;
        return true;
    }

}
