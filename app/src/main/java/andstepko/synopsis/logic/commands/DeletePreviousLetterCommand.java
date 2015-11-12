package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 09.11.15.
 */
public class DeletePreviousLetterCommand extends DeletePreviousCommand {

    @Override
    protected boolean simpleExecution(SynopsisMainActivity synopsisMainActivity) {
        boolean result = deletePreviousLetter(synopsisMainActivity.getTextField(),
                synopsisMainActivity.getTextField().getSelectionEnd());
        return result;
    }

    private boolean deletePreviousLetter(EditText editText, int cursor){
        Editable editable = editText.getEditableText();
        int curCursor = cursor;

        if(curCursor == 0){
            return false;
        }

        curCursor--;

        removedText = editable.subSequence(curCursor, cursor).toString();
        removedFrom = curCursor;
        editable.delete(curCursor, cursor);
        editText.setSelection(curCursor);
        return true;
    }

}
