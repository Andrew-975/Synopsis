package andstepko.synopsis.logic;

/**
 * Created by andrew on 29.09.15.
 */
public class Project {
    private String text;
    private int cursor;

    //region get-set
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        if(cursor <= text.length()) {
            this.cursor = cursor;
        }
        else{
            this.cursor = text.length();
        }
    }

    //endregion get-set

    //region constructor

    public Project(String text, int cursor) {
        if(text == null){
            this.text = "";
            this.cursor = 0;
        }
        else{
            this.text = text;
            this.cursor = cursor;
        }
    }

    public Project(String text) {
        this(text, text.length());
    }

    public Project(){
        this("");
    }
    //endregion constructor
}
