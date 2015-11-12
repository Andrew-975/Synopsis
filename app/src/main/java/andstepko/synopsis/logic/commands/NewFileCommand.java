package andstepko.synopsis.logic.commands;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 10.11.15.
 */
public class NewFileCommand implements Command {

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {



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
