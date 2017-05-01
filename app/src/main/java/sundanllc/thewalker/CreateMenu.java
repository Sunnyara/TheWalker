package sundanllc.thewalker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Sunnara on 2/6/2017.
 */

public class CreateMenu extends AppCompatActivity {

    private Button cAdd, cRemove;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private GameHelper gh;
    private PlayAdapter pa;
    boolean delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);


        delete = false;


        cAdd = (Button) findViewById(R.id.create_add);
        cAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new CreateDialog(v.getContext(), pa);
                d.setTitle("Input Data");
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                d.show();
                d.getWindow().setLayout((6 * width)/7, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });




        ArrayList<WalkerGame> data = new ArrayList<>();

        gh = new GameHelper(this);
        data = gh.getGamesAsCreator();

        cRemove = (Button) findViewById(R.id.create_remove);
        cRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!delete) {
                    delete = true;
                    pa.delete(true);
                    pa.notifyDataSetChanged();
                    Drawable d = v.getResources().getDrawable(R.drawable.ic_delete);
                    cRemove.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
                }
                else
                {
                    delete = false;
                    ArrayList<Integer> ids = pa.getSelectedIds();
                    for(Integer a : ids)
                    {
                        gh.deleteGame(a);
                    }
                    pa.updateDataset(gh.getGamesAsCreator());
                    pa.delete(false);
                    Drawable d = v.getResources().getDrawable(R.drawable.ic_remove);
                    cRemove.setCompoundDrawablesWithIntrinsicBounds(null,d,null,null);
                }
            }
        });
        pa = new PlayAdapter(data, 1);
        lm = new LinearLayoutManager(this);
        rv = (RecyclerView) findViewById(R.id.create_list);
        rv.setAdapter(pa);
        rv.setLayoutManager(lm);
        rv.hasFixedSize();


    }

}
