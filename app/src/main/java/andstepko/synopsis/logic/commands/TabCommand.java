package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;
import andstepko.synopsis.logic.ApplicationPreferences;

/**
 * Created by andstepko on 13.11.15.
 */
public class TabCommand extends TextInsertingCommand {

    public static String TAB_STRING = ApplicationPreferences.TAB_STRING;

    @Override
    protected boolean simpleExecution(SynopsisMainActivity synopsisMainActivity){
        //TODO move following part of code into abstract calss!
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

        editable.insert(start, TAB_STRING);

        insertionCount++;
        insertFrom = start;        
        return true;
    }
}
