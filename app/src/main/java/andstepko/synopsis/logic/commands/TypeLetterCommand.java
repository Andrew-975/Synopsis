package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 09.11.15.
 */
//public class TypeLetterCommand implements StorableCommand{
public class TypeLetterCommand extends TextInsertingCommand{

    private int keyCode;

    public TypeLetterCommand(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    protected boolean simpleExecution(SynopsisMainActivity synopsisMainActivity){
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
}
