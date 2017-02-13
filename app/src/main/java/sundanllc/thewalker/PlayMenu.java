package sundanllc.thewalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 2/6/2017.
 */

public class PlayMenu extends AppCompatActivity {

    ImageButton addButton, removeButton, searchButton, shareButton;
    RecyclerView playRecycler;
    RecyclerView.Adapter playAdapter;
    RecyclerView.LayoutManager playLM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we'll do stuff eventually
            }
        });
        removeButton = (ImageButton) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //one of these days
            }
        });
        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This one will be cool
            }
        });
        shareButton = (ImageButton) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //And this one will be complicated
            }
        });

        playRecycler = (RecyclerView) findViewById(R.id.playRecycler);
        //playRecycler.setHasFixedSize(true);

        playLM = new LinearLayoutManager(this);
        playRecycler.setLayoutManager(playLM);
        ArrayList<String[]> testCard = new ArrayList<String[]>();
        for (int i = 0; i < 100; i++)
        {
            String[] s = {"Around Boone", "Daniel Nance", i + " hours"};
            testCard.add(s);
        }
        playAdapter = new PlayAdapter(testCard);
        playRecycler.setAdapter(playAdapter);
    }
}
