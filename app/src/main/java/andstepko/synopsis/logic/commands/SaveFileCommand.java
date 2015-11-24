package andstepko.synopsis.logic.commands;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 10.11.15.
 */
public class SaveFileCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        Editable editable = synopsisMainActivity.getTextField().getEditableText();

        String fileName = synopsisMainActivity.getCurrentOpenedFile();
        if(fileName == null){
            return new SaveFileAsCommand().execute(synopsisMainActivity);
        }

        final File file = new File(fileName);
        if(file == null){
            return new SaveFileAsCommand().execute(synopsisMainActivity);
        }

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
