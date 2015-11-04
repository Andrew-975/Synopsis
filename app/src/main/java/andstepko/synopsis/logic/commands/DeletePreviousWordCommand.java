package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import java.util.ArrayList;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andrew on 28.09.15.
 */
public class DeletePreviousWordCommand implements Command {

    private static final ArrayList<Character> separationSymbols = new ArrayList<Character>();
    private static final ArrayList<Character> spaceSymbols = new ArrayList<Character>();

    static{
        separationSymbols.add(new Character('.'));
        separationSymbols.add(new Character(','));
        separationSymbols.add(new Character('-'));
        separationSymbols.add(new Character(';'));
        separationSymbols.add(new Character(':'));
        separationSymbols.add(new Character('?'));
        separationSymbols.add(new Character('!'));
        separationSymbols.add(new Character('('));
        separationSymbols.add(new Character(')'));
        separationSymbols.add(new Character('{'));
        separationSymbols.add(new Character('}'));
        separationSymbols.add(new Character('['));
        separationSymbols.add(new Character(']'));
        separationSymbols.add(new Character('\n'));
        //
        spaceSymbols.add(new Character(' '));
        spaceSymbols.add(new Character('\t'));
    }

    private String removedString = "";
    int removeFrom;

    @Override
    public boolean execute(SynopsisMainActivity synopsisMainActivity) {
        boolean result = deletePreviousWord(synopsisMainActivity.getTextField(),
                synopsisMainActivity.getTextField().getSelectionEnd());
        return result;
    }

    @Override
    public boolean unexecute(SynopsisMainActivity synopsisMainActivity) {

        EditText editText = synopsisMainActivity.getTextField();
        Editable editable = editText.getEditableText();
        int initialCursor = editText.getSelectionEnd();

        if(removedString.length() == 0){
            return false;
        }

        editable.insert(removeFrom, removedString);
        editText.setSelection(initialCursor + removedString.length());
        return true;
    }

    @Override
    public boolean isStuckable() {
        return true;
    }

    private boolean deletePreviousWord(EditText editText, int initialCursor){
        Editable editable = editText.getEditableText();
        int curCursor = initialCursor;

        if(curCursor == 0){
            return false;
        }

        //String secondPart = text.substring(cursorPosition, text.length());
        curCursor -= 1;

        // Delete leading spaces.
        while((curCursor >= 0) && (spaceSymbols.contains(editable.charAt(curCursor)))){
            // Delete spaces.
            curCursor--;
        }

        if(curCursor < 0) {
            // Got start of the text
            removedString = editable.subSequence(0, initialCursor).toString();
            removeFrom = 0;
            editable.delete(0, initialCursor);
            editText.setSelection(0);
            return true;
        }

        if(separationSymbols.contains(new Character(editable.charAt(curCursor)))){
            removedString = editable.subSequence(curCursor, initialCursor).toString();
            removeFrom = curCursor;
            editable.delete(curCursor, initialCursor);
            editText.setSelection(curCursor);
            return true;
        }

        // Before spaces is not a punctuation symbol.
        while((curCursor >= 0) && (!separationSymbols.contains(editable.charAt(curCursor)) &&
                                                !spaceSymbols.contains(editable.charAt(curCursor)))){
            curCursor--;
        }

        curCursor+=1;

        removedString = editable.subSequence(curCursor, initialCursor).toString();
        removeFrom = curCursor;
        editable.delete(curCursor, initialCursor);
        editText.setSelection(curCursor);
        return true;
    }
}
