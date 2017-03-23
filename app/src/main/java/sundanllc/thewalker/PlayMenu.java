package sundanllc.thewalker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.List;

/**
 * Created by Daniel on 2/6/2017.
 */

public class PlayMenu extends AppCompatActivity {

    ImageButton addButton, removeButton, searchButton, shareButton;
    RecyclerView playRecycler;
    PlayAdapter playAdapter;
    RecyclerView.LayoutManager playLM;
    GameHelper dbHelper;
    private boolean deleting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        playRecycler = (RecyclerView) findViewById(R.id.playRecycler);
        //playRecycler.setHasFixedSize(true);
        playLM = new LinearLayoutManager(this);
        playRecycler.setLayoutManager(playLM);

        dbHelper = new GameHelper(this);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        WalkerGame fakeGame = new WalkerGame("Around Boone", "Daniel Nance", "A placeholder game", bm, 4, 2);
        dbHelper.insertGame(fakeGame);
        ArrayList<WalkerGame> game = dbHelper.getGames();

        playAdapter = new PlayAdapter(game, 0);
        playRecycler.setAdapter(playAdapter);

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
                if (!deleting) {
                    deleting = true;
                    playAdapter.delete(true);
                    playAdapter.notifyDataSetChanged();
                }
                else
                {
                    deleting = false;
                    ArrayList<Integer> ids = playAdapter.getSelectedIds();
                    for (Integer a : ids)
                    {

                    }
                    playAdapter.delete(false);
                    playAdapter.notifyDataSetChanged();
                }

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

            }
        });
    }

    @Override
    protected void onDestroy()
    {
        //dbHelper.deleteAll();
        dbHelper.close();
        super.onDestroy();
    }
}
