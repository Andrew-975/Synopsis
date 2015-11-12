package andstepko.synopsis;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import andstepko.synopsis.logic.KeyCombination;
import andstepko.synopsis.logic.ApplicationPreferences;
import andstepko.synopsis.logic.commands.Command;
import andstepko.synopsis.logic.commands.CommandManager;
import andstepko.synopsis.logic.commands.NewFileCommand;
import andstepko.synopsis.logic.commands.OpenFileCommand;
import andstepko.synopsis.logic.commands.SaveFileCommand;

public class MainActivity extends Activity implements SynopsisMainActivity {

    private static MainActivity INSTANCE;

    public EditText mainEditText;
    private TextView tabsTextView;
    private TextView logsTextView;
    //
    Button testButton;
    Button testButton2;

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
        // FIXME removeme after debug
        testButton = (Button)findViewById(R.id.test_btn);
        testButton2 = (Button)findViewById(R.id.test_btn2);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandManager.getInstance().execute(new OpenFileCommand());
            }
        });

        testButton2.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == OPEN_FILE_REQUEST){
            if ((data != null) && (data.getData() != null)) {
                Uri fileUri = data.getData();


                String text = fileUri.toString();
                // remove "file://"
                text = text.subSequence("file://".length(), text.length()).toString();
                if(fileUri != null) {

                    try {
                        File f = new File(text);
                        FileInputStream is = new FileInputStream(f); // Fails on this line
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        text = new String(buffer);
                        //TODO

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
    public void logRecord(String message) {
        logsTextView.setText(message);
    }
}