package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Daniel on 3/30/2017.
 */

public class SearchDialog extends Dialog
{
    private EditText text;
    private CheckBox title, author, description;
    private Button search;
    private DialogListener dl;

    public SearchDialog(Context context)
    {
        super(context);
        setContentView(R.layout.search_dialog);

        title = (CheckBox) findViewById(R.id.titlecheck);
        author = (CheckBox) findViewById(R.id.authorcheck);
        description = (CheckBox) findViewById(R.id.desccheck);
        text = (EditText) findViewById(R.id.search_text);
        search = (Button) findViewById(R.id.searchbutton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.Search(title.isChecked(), author.isChecked(), description.isChecked(), text.getText().toString());
                cancel();
            }
        });
    }

    public void setDialogListener(DialogListener listener)
    {
        this.dl = listener;
    }

    public DialogListener getDialogListener()
    {
        return dl;
    }

    public static interface DialogListener
    {
        public void Search(boolean title, boolean author, boolean desc, String search);
    }
}
