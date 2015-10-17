package andstepko.synopsis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import andstepko.synopsis.logic.KeyCombination;
import andstepko.synopsis.logic.Preferences;
import andstepko.synopsis.logic.Project;
import andstepko.synopsis.logic.actions.Action;
import andstepko.synopsis.logic.actions.DeleteForwardWordAction;
import andstepko.synopsis.logic.actions.DeletePreviousWordAction;

public class MainActivity extends Activity {

    private static MainActivity INSTANCE;

    private TextView helloWorld;
    private EditText mainEditText;
    private EditText logEditText;

    private boolean insertMode;
    private Project project = new Project("");
    private Action methods[] = new Action[]{
        new DeletePreviousWordAction(),
            new DeleteForwardWordAction()
    };

    //region get-set
    public Project getProjectClone() {
        // TODO fix crutch
        if(project == null){
            return new Project();
        }

        return new Project(mainEditText.getText().toString(), mainEditText.getSelectionEnd());
    }

    public void setProject(Project project){
        this.project = project;

        if(project == null){
            return;
        }

        String text = project.getText();
        int cursor = project.getCursor();
        if(text == null){
            return;
        }

        mainEditText.setText(text);
        mainEditText.setSelection(project.getCursor());
    }

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

        mainEditText = (EditText)findViewById(R.id.mainEditText);
        logEditText = (EditText)findViewById(R.id.logEditText);


        mainEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                KeyCombination keyCombination = new KeyCombination(keyCode, event);
                //log("keyCombination: " + keyCombination.toString());

                int actionIndex = Preferences.INSTANCE.shortcuts.getShortcutIndex(keyCombination);
                return performAction(actionIndex);
            }
        });
    }

    private boolean performActionOne(){
        helloWorld.setBackgroundColor(Color.RED);
        return true;
    }

    private boolean performActionTwo(){
        helloWorld.setBackgroundColor(Color.GREEN);
        return true;
    }

    private boolean performAction(int index){
        if((index >= methods.length) || (index < 0)){
            return false;
        }

        methods[index].perform();
        return true;
    }

    public void log(String message){
        logEditText.setText(logEditText.getText().toString() + " " + message);
    }
}