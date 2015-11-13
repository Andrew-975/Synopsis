package andstepko.synopsis;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.SortedMap;

import andstepko.synopsis.logic.KeyCombination;
import andstepko.synopsis.logic.ApplicationPreferences;
import andstepko.synopsis.logic.commands.BiggerTextCommand;
import andstepko.synopsis.logic.commands.CancelCommand;
import andstepko.synopsis.logic.commands.Command;
import andstepko.synopsis.logic.commands.CommandManager;
import andstepko.synopsis.logic.commands.DeletePreviousWordCommand;
import andstepko.synopsis.logic.commands.NewFileCommand;
import andstepko.synopsis.logic.commands.NewLineCommand;
import andstepko.synopsis.logic.commands.OpenFileCommand;
import andstepko.synopsis.logic.commands.SaveFileAsCommand;
import andstepko.synopsis.logic.commands.SaveFileCommand;
import andstepko.synopsis.logic.commands.SmallerTextCommand;

public class MainActivity extends Activity implements SynopsisMainActivity {

    private static MainActivity INSTANCE;

    public EditText mainEditText;
    private TextView tabsTextView;
    private TextView logsTextView;
    //
    Button saveButton;
    Button testButton;
    Button testButton2;
    Button testButton3;


    private File currentFile;

    public static MainActivity getInstance(){
        return INSTANCE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        INSTANCE = this;

        tabsTextView = (TextView)findViewById(R.id.tabsTextView);
        mainEditText = (EditText)findViewById(R.id.mainEditText);
        logsTextView = (TextView)findViewById(R.id.logsTextView);
        saveButton = (Button)findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandManager.getInstance().execute(new SaveFileCommand());
            }
        });
        // FIXME removeme after debug
        testButton = (Button)findViewById(R.id.test_btn);
        testButton2 = (Button)findViewById(R.id.test_btn2);
        testButton3 = (Button)findViewById(R.id.test_btn3);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CommandManager.getInstance().execute(new DeletePreviousWordCommand());
                getTextField().setText(Html.fromHtml("<b>" + "bold" + "</b>" + "<br />" +
                        "<small>" + "small" + "</small>" + "<br />"));
            }
        });

        testButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandManager.getInstance().execute(new SaveFileAsCommand());
            }
        });

        testButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandManager.getInstance().execute(new OpenFileCommand());
            }
        });



        mainEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;
                }

                KeyCombination keyCombination = new KeyCombination(keyCode, event);
                logRecord(keyCombination.toString());

                Command command = ApplicationPreferences.getInstance().getCommand(keyCombination);
                if (command == null) {
                    return false;
                }

                return CommandManager.getInstance().execute(command);
            }
        });

        // Open files
        Intent intent = getIntent();
        String action = intent.getAction();

        if (action.compareTo(Intent.ACTION_VIEW) == 0) {
            String scheme = intent.getScheme();

            if (scheme.compareTo(ContentResolver.SCHEME_FILE) == 0) {
                openFile(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == OPEN_FILE_REQUEST){
            openFile(data);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean openFile(Intent data){
        if ((data != null) && (data.getData() != null)) {

            Uri fileUri = data.getData();
            String fullFileName = new String(fileUri.toString().getBytes(), Charset.forName("UTF-8"));

            String text;
            File file;

            // remove "file://"
            fullFileName = fullFileName.subSequence("file://".length(), fullFileName.length()).toString();
            if(fileUri != null) {

                try {
                    file = new File(fullFileName);
                    FileInputStream is = new FileInputStream(file);
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    text = new String(buffer);
                    //TODO

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error opening file!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(text == null){
                    Toast.makeText(this, "Error opening file!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                getCommandManager().clearHistory();
                mainEditText.setText(text);
                logRecord("opened file: " + fullFileName);

                currentFile = file;
                tabsTextView.setText(file.getName());

                return true;
            }
        }

        return false;
    }

    @Override
    public EditText getTextField() {
        return mainEditText;
    }

    @Override
    public CommandManager getCommandManager() {
        return CommandManager.getInstance();
    }

    @Override
    public File getDefaultFilesDirectory() {
        return ApplicationPreferences.getInstance().getPreferences().getDefaultFilesDirectory();
    }

    @Override
    public File getCurrentFile() {
        return currentFile;
    }

    @Override
    public void setCurrentFile(File file) {
        currentFile = file;
    }

    @Override
    public void logRecord(String message) {
        logsTextView.setText(message);
    }
}