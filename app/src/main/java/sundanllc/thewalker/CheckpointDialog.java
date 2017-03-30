package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/27/2017
 *          Description -
 */

public class CheckpointDialog extends Dialog {

    private GameHelper gh;
    private Checkpoint cp;
    private Button cancel, add;
    private TextView gpshere, mapopen, h1, h2, h3, h4;
    private EditText x, y;

    public Checkpoint getCp() {
        return cp;
    }

    public void setCp(Checkpoint cp) {
        this.cp = cp;
    }

    public CheckpointDialog(@NonNull final Context context, final CheckpointAdapter cpa, final long id) {
        super(context);
        setContentView(R.layout.checkpoint_dialog);

        x = (EditText) findViewById(R.id.x_input);
        y = (EditText) findViewById(R.id.y_input);
        h1 = (EditText) findViewById(R.id.hint1_input);
        h2 = (EditText) findViewById(R.id.hint2_input);
        h3 = (EditText) findViewById(R.id.hint3_input);
        h4 = (EditText) findViewById(R.id.hint4_input);



        gpshere = (TextView) findViewById(R.id.currloc);
        mapopen = (TextView) findViewById(R.id.open_map);

        gh = new GameHelper(context);

        add = (Button) findViewById(R.id.add_cp);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean empty = x.getText().toString().trim().isEmpty() ||
                        y.getText().toString().trim().isEmpty() ||
                        h1.getText().toString().trim().isEmpty() ||
                        h2.getText().toString().trim().isEmpty() ||
                        h3.getText().toString().trim().isEmpty() ||
                        h4.getText().toString().trim().isEmpty();
                if(empty) {
                    Toast t = Toast.makeText(context,"Missing inputs.", Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                cp = new Checkpoint();
                cp.setId(id);
                cp.setX(Float.parseFloat(x.getText().toString()));
                cp.setY(Float.parseFloat(y.getText().toString()));
                cp.setHint1(h1.getText().toString());
                cp.setHint2(h2.getText().toString());
                cp.setHint3(h3.getText().toString());
                cp.setHint4(h4.getText().toString());
                gh.insertCheckpoint(cp);
                cancel();

            }
        });

        cancel = (Button) findViewById(R.id.cancel_cp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        gh = new GameHelper(context);

    }
}
