package andstepko.synopsis.logic.commands;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 10.11.15.
 */
public class OpenFileCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {

        Editable editable = synopsisMainActivity.getTextField().getEditableText();
        File defaultDir = synopsisMainActivity.getDefaultFilesDirectory();

        final File file = new File(defaultDir, "newFile" + ".synopsis");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        ((Activity)synopsisMainActivity).startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                synopsisMainActivity.OPEN_FILE_REQUEST);


        return true;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {
        return false;
    }

    @Override
    public boolean isUnexecutable() {
        return false;
    }
}
