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
public class PasteCommand implements Command {

    private int pastedStart;
    private int pastedEnd;
    private CharSequence removedText = "";

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        Context context = (Context)synopsisMainActivity;
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();

        if(clipData == null){
            return false;
        }

        ClipData.Item item = clipData.getItemAt(0);
        String text = item.getText().toString();

        if((text == null) || (text.length() == 0)){
            return false;
        }

        // Have smth to paste.
        int tempStart = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        int start = Math.min(tempStart, end);
        end = Math.max(tempStart, end);

        // At first, delete selected insertedText.
        if((start >= 0) && (start != end)){
            removedText = editable.subSequence(start, end);
            editable.delete(start, end);
        }

        editable.insert(start, text);

        pastedStart = start;
        pastedEnd = start + text.length();
        return true;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        if(editable.length() < pastedEnd){
            return false;
        }

        editable.delete(pastedStart, pastedEnd);
        editable.insert(pastedStart, removedText);
        editText.setSelection(pastedStart, pastedStart + removedText.length());
        return true;
    }

    @Override
    public boolean isUnexecutable() {
        return true;
    }
}
