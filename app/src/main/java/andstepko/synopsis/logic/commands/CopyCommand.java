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
public class CopyCommand implements Command {
    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        int tempStart = editText.getSelectionStart();
        int end = editText.getSelectionEnd();

        if((tempStart == end) || (tempStart < 0) || (end < 0)){
            return false;
        }

        // Exchange, if they are vice versa.
        int start = Math.min(tempStart, end);
        end = Math.max(tempStart, end);

        editable.subSequence(start, end);

        Context context = (Context)synopsisMainActivity;
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text_label", editable.subSequence(start, end));
        clipboardManager.setPrimaryClip(clipData);
        return true;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        return false;
    }

    @Override
    public boolean isStuckable() {
        return false;
    }
}
