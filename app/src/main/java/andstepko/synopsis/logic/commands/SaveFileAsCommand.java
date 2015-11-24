package andstepko.synopsis.logic.commands;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import andstepko.synopsis.R;
import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 14.11.15.
 */
public class SaveFileAsCommand implements Command {

    private static final String FILE_TYPE_TXT = ".txt";
    private static final String FILE_TYPE_SYNOPSIS = ".synopsis";

    private SynopsisMainActivity synopsisMainActivity;

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_CREATE_DOCUMENT);
//        intent.setType("file/*");
//        ((Activity)synopsisMainActivity).startActivityForResult(
//                Intent.createChooser(intent, "Select File"),
//                synopsisMainActivity.OPEN_FILE_REQUEST);


//        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT)
//                .addCategory(Intent.CATEGORY_OPENABLE)
//                //.setType("file/*");
//                //.putExtra(Intent.EXTRA_TITLE, attachment.getName());
//        ((Activity)synopsisMainActivity).startActivityForResult(
//                Intent.createChooser(intent, "Save file as"),
//                synopsisMainActivity.SAVE_FILE_AS_REQUEST);


        this.synopsisMainActivity = synopsisMainActivity;
        File mPath = synopsisMainActivity.getDefaultFilesDirectory();
        final String[] mFileList;

        try {
            mPath.mkdirs();
        }
        catch(SecurityException e) {
            Log.e("andstepko", "unable to write on the sd card " + e.toString());
        }
        if(mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    return filename.contains(FILE_TYPE_TXT) || filename.contains(FILE_TYPE_SYNOPSIS)
                            || sel.isDirectory();
                }

            };
            mFileList = mPath.list(filter);
        }
        else {
            mFileList= new String[0];
        }

        // Dialog
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)synopsisMainActivity);

        builder.setTitle("Save file as");
        if(mFileList == null) {
            Log.e("andstepko", "Showing file picker before loading the file list");
            dialog = builder.create();
            return true;
        }
        builder.setItems(mFileList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        giveFileNameToActivity(mFileList[which]);
                    }
                });
        builder.setPositiveButton("New file", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showNewFileDialog();
            }
        });
                dialog = builder.show();
        return true;
    }

    private void giveFileNameToActivity(String fileName){
        fileName = synopsisMainActivity.getDefaultFilesDirectory().toString() + "/" + fileName;
        synopsisMainActivity.setOpenedFileFullPath(fileName);
        synopsisMainActivity.getCommandManager().execute(new SaveFileCommand());
    }

    private void showNewFileDialog(){
        LayoutInflater li = LayoutInflater.from((Context)synopsisMainActivity);
        View promptsView = li.inflate(R.layout.dialog_new_file, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((Context)synopsisMainActivity);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_new_file_name);

        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String fileName = userInput.getText().toString();
                                if ((fileName != null) && (fileName.length() > 0)) {
                                    giveFileNameToActivity(fileName);
                                }
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        alertDialog.show();

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
