package sundanllc.thewalker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
    ArrayList<WalkerGame> game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        playRecycler = (RecyclerView) findViewById(R.id.playRecycler);
        //playRecycler.setHasFixedSize(true);
        playLM = new LinearLayoutManager(this);
        playRecycler.setLayoutManager(playLM);

        dbHelper = new GameHelper(this);
        /*Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        WalkerGame fakeGame = new WalkerGame("Around Boone", "Daniel Nance", "A placeholder game", bm, 4, 2);
        dbHelper.insertGame(fakeGame);*/
        game = dbHelper.getGames();

        playAdapter = new PlayAdapter(game, 0);
        playRecycler.setAdapter(playAdapter);

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                WalkerGame fakeGame = new WalkerGame("Around Boone", "Daniel Nance", "This isn't a real game, it is just here to take up space while we finish the part of the app that actually does this part.", bm, 4, 2);
                long id = dbHelper.insertGame(fakeGame);
                Checkpoint a = new Checkpoint(id, "1555 w King st E Boone nc 28607", (float) 81.6746, (float) 32.2168, "it's my house", "also known as the sketchy grey apartments beside high country condos", "Sunny went there once", "doesn't even remember it how inconsiderate");
                dbHelper.insertCheckpoint(a);
                game = dbHelper.getGames();
                playAdapter.updateDataset(game);
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
                    removeButton.setImageResource(R.drawable.ic_delete);
                }
                else
                {
                    deleting = false;
                    ArrayList<Integer> ids = playAdapter.getSelectedIds();
                    for (Integer a : ids)
                    {
                        dbHelper.deleteGame(a);
                    }
                    playAdapter.updateDataset(dbHelper.getGames());
                    playAdapter.delete(false);
                    removeButton.setImageResource(R.drawable.ic_remove);
                }

            }
        });
        searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        shareButton = (ImageButton) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nfc(dbHelper.getGames());
            }
        });
    }

    private void nfc(ArrayList<WalkerGame> games)
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(PlayMenu.this);
            File transFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "nfc");
            transFile.deleteOnExit();
            if (transFile.mkdir())
            {

            }
        }
    }



    @Override
    protected void onDestroy()
    {
        dbHelper.deleteAll();
        dbHelper.close();
        super.onDestroy();
    }
}
