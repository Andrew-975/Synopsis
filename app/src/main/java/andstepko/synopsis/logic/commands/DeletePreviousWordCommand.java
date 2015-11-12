package andstepko.synopsis.logic.commands;

import android.text.Editable;
import android.widget.EditText;

import java.util.ArrayList;

import andstepko.synopsis.SynopsisMainActivity;

/**
 * Created by andrew on 28.09.15.
 */
public class DeletePreviousWordCommand extends DeletePreviousCommand {

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

    @Override
    protected boolean simpleExecution(SynopsisMainActivity synopsisMainActivity){
        boolean result = deletePreviousWord(synopsisMainActivity.getTextField(),
                synopsisMainActivity.getTextField().getSelectionEnd());
        return result;
    }

    private boolean deletePreviousWord(EditText editText, int initialCursor){
        Editable editable = editText.getEditableText();
        int curCursor = initialCursor;

        if(curCursor == 0){
            return false;
        }

        curCursor -= 1;

        // Delete leading spaces.
        while((curCursor >= 0) && (spaceSymbols.contains(editable.charAt(curCursor)))){
            // Delete spaces.
            curCursor--;
        }

        if(curCursor < 0) {
            // Got start of the insertedText
            removedText = editable.subSequence(0, initialCursor).toString();
            removedFrom = 0;
            editable.delete(0, initialCursor);
            editText.setSelection(0);
            return true;
        }

        if(separationSymbols.contains(new Character(editable.charAt(curCursor)))){
            removedText = editable.subSequence(curCursor, initialCursor).toString();
            removedFrom = curCursor;
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

        removedText = editable.subSequence(curCursor, initialCursor).toString();
        removedFrom = curCursor;
        editable.delete(curCursor, initialCursor);
        editText.setSelection(curCursor);
        return true;
    }
}