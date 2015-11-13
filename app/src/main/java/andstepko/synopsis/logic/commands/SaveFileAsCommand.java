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
 * Created by andstepko on 14.11.15.
 */
public class SaveFileAsCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        Editable editable = synopsisMainActivity.getTextField().getEditableText();
        File defaultDir = synopsisMainActivity.getDefaultFilesDirectory();

        //FIXME
        String fileName = "newFile2";

        final File file = new File(defaultDir, fileName + ".synopsis");

        synopsisMainActivity.logRecord(file.getAbsolutePath());

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fileOutputStream);
            pw.print(editable.toString());
            pw.flush();
            pw.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        synopsisMainActivity.setCurrentFile(file);

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
