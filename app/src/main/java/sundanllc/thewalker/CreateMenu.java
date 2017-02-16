package sundanllc.thewalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by Sunnara on 2/6/2017.
 */

public class CreateMenu extends AppCompatActivity {

    private Button cAdd;
    private RecyclerView rv;
    private RecyclerView.LayoutManager lm;
    private PlayAdapter pa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);



        cAdd = (Button) findViewById(R.id.create_add);
        cAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateMenu.this,CreateAddOption.class));
            }
        });

        ArrayList<String[]> data = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            String [] s = {"Title" + i ,"SunDan LLC","" + i};
            data.add(s);
        }
        pa = new PlayAdapter(data);
        lm = new LinearLayoutManager(this);
        rv = (RecyclerView) findViewById(R.id.create_list);
        rv.setAdapter(pa);
        rv.setLayoutManager(lm);
        rv.hasFixedSize();

    }
}
