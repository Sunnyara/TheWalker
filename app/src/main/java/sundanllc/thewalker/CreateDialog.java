package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
    private GameHelper gh;
    private WalkerGame addGame;
    private PlayAdapter pa;


    public PlayAdapter getPa() {
        return pa;
    }

    public void setPa(PlayAdapter pa) {
        this.pa = pa;
    }

    public CreateDialog(@NonNull final Context context, final PlayAdapter pa) {
        super(context);
        setContentView(R.layout.create_dialog);

        gh = new GameHelper(context);

        title = (EditText) findViewById(R.id.create_title);
        author = (EditText) findViewById(R.id.create_author);
        description = (EditText) findViewById(R.id.create_desc);

        create = (Button) findViewById(R.id.create_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean empty = title.getText().toString().trim().isEmpty() ||
                        author.getText().toString().trim().isEmpty();
                if(empty) {
                    Toast t = Toast.makeText(context,"Author or Title required.",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                addGame = new WalkerGame();
                addGame.setPicture(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                addGame.setCreator(true);
                addGame.setAuthor(author.getText().toString());
                addGame.setTitle(title.getText().toString());
                addGame.setDescription(description.getText().toString());
                addGame.setEta(0);
                addGame.setTime_played(0);
                long id = gh.insertGame(addGame);

                pa.updateDataset(gh.getGamesAsCreator());
                pa.notifyDataSetChanged();


                Intent i = new Intent(v.getContext(),CreateAddOption.class);
                i.putExtra("id", (int) id);


                v.getContext().startActivity(i);
                cancel();
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
