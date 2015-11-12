package andstepko.synopsis.logic.commands;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 04.11.15.
 */
public class CutCommand implements Command {

    private boolean isSelectionDirect;
    private int removedFrom;
    private String removedText = "";

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {

        if(removedText == "") {
            // First execution.
            return simpleExecution(synopsisMainActivity);
        }
        else{
            //Not a first execution.
            EditText editText = synopsisMainActivity.getTextField();
            Editable editable = editText.getEditableText();

            editable.delete(removedFrom, removedFrom + removedText.length());
            editText.setSelection(removedFrom);
            return true;
        }
    }

    private boolean simpleExecution(SynopsisMainActivity synopsisMainActivity){

        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        int tempStart = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        if ((tempStart == end) || (tempStart < 0) || (end < 0)) {
            return true;
        }

        if (tempStart < end) {
            isSelectionDirect = true;
        }

        // Exchange, if they are vice versa.
        int start = Math.min(tempStart, end);
        end = Math.max(tempStart, end);

        removedFrom = start;
        removedText = editable.subSequence(start, end).toString();

        Context context = (Context) synopsisMainActivity;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text_label", removedText);
        clipboardManager.setPrimaryClip(clipData);

        editable.delete(start, end);
        editText.setSelection(start);
        return true;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        if((removedText == null) || (removedText.length() == 0)){
            return false;
        }

        // Have smth to paste.
        editable.insert(removedFrom, removedText);
        if(isSelectionDirect){
            editText.setSelection(removedFrom, removedFrom + removedText.length());
        }
        else {
            editText.setSelection(removedFrom + removedText.length(), removedFrom);
        }
        return true;

    }

    @Override
    public boolean isUnexecutable() {
        return true;
    }
}
