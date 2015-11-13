package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andstepko on 13.11.15.
 */
public class NewLineCommand extends TextInsertingCommand {

    //TODO remove and use from AppPreferences
    public static char SPACE = ' ';
    public static char TAB = '\t';
    public static char EOL = '\n';
    public static int SPACES_FOR_TAB = 3;

    @Override
    protected boolean simpleExecution(SynopsisMainActivity synopsisMainActivity) {
        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();

        int tempStart = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if(end > tempStart){
            isSelectionDirect = true;
        }
        int start = Math.min(tempStart, end);
        end = Math.max(tempStart, end);

        // At first, delete selected text.
        if((start >= 0) && (start != end)){
            this.removedText = editable.subSequence(start, end);
            editable.delete(start, end);
        }



        int curIndex = start - 1;

        while ((curIndex >= 0) && (editable.charAt(curIndex) != EOL)){
            curIndex--;
        }
        curIndex++;

        int emptyLineStartLength = 0;
        while((curIndex < editable.length()) &&
                ((editable.charAt(curIndex) == TAB) || (editable.charAt(curIndex) == SPACE))){
            if(editable.charAt(curIndex) == TAB){
                emptyLineStartLength += SPACES_FOR_TAB;
            }
            else{
                // space
                emptyLineStartLength += 1;
            }
            curIndex++;
        }

        String emptyString = buildEmptyString(emptyLineStartLength);
        editable.insert(start, EOL + emptyString);
        insertionCount += (EOL + emptyString).length();
        insertFrom = start;

        return true;
    }

    private String buildEmptyString(int emptySpacesNumber){
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int tabsNumber = emptySpacesNumber / SPACES_FOR_TAB;
        int spacesNumber = emptySpacesNumber - (tabsNumber * SPACES_FOR_TAB);

        for(; i < tabsNumber; i++){
            sb.append(TAB);
        }
        for(; i < spacesNumber; i++){
            sb.append(SPACE);
        }

        return sb.toString();
    }
}
