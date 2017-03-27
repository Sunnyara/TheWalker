package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/23/2017
 *          Description -
 */

public class CreateDialog extends Dialog {

    private Button create, cancel;
    private EditText title, author, description;

    public CreateDialog(@NonNull final Context context) {
        super(context);
        setContentView(R.layout.create_dialog);

        title = (EditText) findViewById(R.id.create_title);
        author = (EditText) findViewById(R.id.create_author);
        description = (EditText) findViewById(R.id.create_desc);

        create = (Button) findViewById(R.id.create_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context,"it worked!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        cancel = (Button) findViewById(R.id.cancel_create);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
}
