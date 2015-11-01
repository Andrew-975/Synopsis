package andstepko.synopsis.logic.commands;

import java.util.ArrayList;

import andstepko.synopsis.MainActivity;
import andstepko.synopsis.logic.Project;

/**
 * Created by andrew on 28.09.15.
 */
public class DeletePreviousWordCommand extends Command {

    private static final ArrayList<Character> punctuationSymbols = new ArrayList<Character>();

    static{
        punctuationSymbols.add(new Character('.'));
        punctuationSymbols.add(new Character(','));
        punctuationSymbols.add(new Character('-'));
        punctuationSymbols.add(new Character(';'));
        punctuationSymbols.add(new Character(':'));
        punctuationSymbols.add(new Character('?'));
        punctuationSymbols.add(new Character('!'));
    }

    @Override
    public boolean execute() {
        Project project = MainActivity.getInstance().getProjectClone();
        boolean result = deletePreviousWord(project);
        MainActivity.getInstance().setProject(project);
        return result;
    }

    @Override
    public boolean unexecute() {
        return false;
    }

    @Override
    boolean isStuckable() {
        return true;
    }

    private static boolean deletePreviousWord(Project project){
        String text = project.getText();
        int position = project.getCursor();

        if(position == 0){
            return false;
        }

        String secondPart = text.substring(position, text.length());
        position -= 1;

        while((position >= 0) && (text.charAt(position) == ' ')){
            // Delete spaces.
            position--;
        }

        if(position < 0) {
            project.setText(secondPart);
            project.setCursor(0);
            return true;
        }

        if(punctuationSymbols.contains(new Character(text.charAt(position)))){
            if(--position > 0) {
                project.setText(text.substring(0, position + 1) + secondPart);
                project.setCursor(position + 1);
                return true;
            }
            else{
                // Deleted straight to start of the text.
                project.setText(secondPart);
                project.setCursor(0);
                return true;
            }
        }

        while((position >= 0) && ((Character.isLetter(text.charAt(position))) ||
                                            (text.charAt(position) == '`'))){
            position--;
        }

        if(position < 0) {
            project.setText(secondPart);
        }
        project.setText(text.substring(0, position + 1) + secondPart);
        project.setCursor(position + 1);
        return true;
    }
}
