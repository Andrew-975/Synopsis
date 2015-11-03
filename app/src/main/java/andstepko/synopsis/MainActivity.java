package andstepko.synopsis;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import andstepko.synopsis.logic.KeyCombination;
import andstepko.synopsis.logic.ApplicationPreferences;
import andstepko.synopsis.logic.Project;
import andstepko.synopsis.logic.commands.Command;
import andstepko.synopsis.logic.commands.CommandManager;

public class MainActivity extends Activity implements SynopsisMainActivity {

    private static MainActivity INSTANCE;

    public EditText mainEditText;
    private TextView tabsTextView;
    private TextView logsTextView;
    //
    Button testButton;
    private Project project = new Project("");

    //region get-set

    //FIXME removeme
//    public Project getProjectClone() {
//        // TODO fix crutch
//        if(project == null){
//            return new Project();
//        }
//
//        return new Project(mainEditText.getText().toString(), mainEditText.getSelectionEnd());
//    }
//
//    public void setProject(Project project){
//        this.project = project;
//
//        if(project == null){
//            return;
//        }
//
//        String text = project.getText();
//        if(text == null){
//            return;
//        }
//
//        mainEditText.setText(text);
//        mainEditText.setSelection(project.getCursor());
//    }

    public static MainActivity getInstance(){
        return INSTANCE;
    }

    public static Context getContext(){
        return (Context)INSTANCE;
    }
    //endregion get-set

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

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                KeyCombination keyCombination = new KeyCombination(
//                        KeyEvent.KEYCODE_T, true, false, false);
//                logRecord(keyCombination.toString());
//
//                Command command = ApplicationPreferences.getInstance().getCommand(keyCombination);
//                if (command == null) {
//                    return;
//                }
//                CommandManager.getInstance().execute(command);

                CommandManager.getInstance().stepForward();
            }
        });
        //

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

                CommandManager.getInstance().execute(command);
                return true;
            }
        });
    }

    @Override
    public EditText getTextField() {
        return mainEditText;
    }

    @Override
    public CommandManager getCommandManager() {
        return CommandManager.getInstance();
    }

    public void logRecord(String message) {
        logsTextView.setText(message);
    }
}