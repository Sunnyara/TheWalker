package sundanllc.thewalker;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.JsonWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by Daniel on 2/6/2017.
 */

public class PlayMenu extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 17;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 42;
    private ImageButton addButton, removeButton, searchButton, shareButton;
    private Button nfcButton, saveButton;
    private RecyclerView playRecycler;
    private PlayAdapter playAdapter;
    private RecyclerView.LayoutManager playLM;
    private GameHelper dbHelper;
    private boolean deleting;
    private ArrayList<WalkerGame> game;
    private List<String> fileList = new ArrayList<String>();
    private ListView dirView;
    private File[] files;
    protected Button upButton;
    protected TextView folderView;
    protected File root;
    protected File currFolder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

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

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        currFolder = root;

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog add = new Dialog(v.getContext());
                add.setContentView(R.layout.picker_dialog);
                add.setTitle("Pick a File");
                add.setCancelable(true);
                add.setCanceledOnTouchOutside(true);

                folderView = (TextView) add.findViewById(R.id.folder);
                upButton = (Button) add.findViewById(R.id.up);
                upButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeList(currFolder.getParentFile());
                    }
                });

                dirView = (ListView) add.findViewById(R.id.dialogList);
                dirView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = files[position];
                        if(selected.isDirectory())
                        {
                            makeList(selected);
                        }
                        else
                        {
                            ObjectInputStream ois;
                            try {
                                ois = new ObjectInputStream(new FileInputStream(selected));
                                WalkerGame gameObject = (WalkerGame) ois.readObject();
                                long wid = dbHelper.insertGame(gameObject);
                                for (Checkpoint a : gameObject.getCheckpoints())
                                {
                                    a.setId(wid);
                                    dbHelper.insertCheckpoint(a);
                                }
                                game = dbHelper.getGames();
                                playAdapter.updateDataset(game);
                                ois.close();
                            }
                            catch (Exception e)
                            {
                                String mess = e.getMessage();
                                e.printStackTrace();
                            }
                        }
                    }
                });
                makeList(root);
                add.show();
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
                SearchDialog d = new SearchDialog(v.getContext());
                d.setTitle("Input Data");
                d.setDialogListener(new SearchDialog.DialogListener() {
                    @Override
                    public void Search(boolean title, boolean author, boolean desc, String search) {
                        playAdapter.updateDataset(dbHelper.searchGames(title, author, desc, search));
                    }
                });
                d.show();
            }
        });
        shareButton = (ImageButton) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!deleting) {
                    deleting = true;
                    playAdapter.delete(true);
                    playAdapter.notifyDataSetChanged();
                }
                else
                {
                    Dialog share = new Dialog((v.getContext()));
                    share.setContentView(R.layout.nfc_dialog);
                    share.setCancelable(true);
                    share.setCanceledOnTouchOutside(true);
                    saveButton = (Button) share.findViewById(R.id.save);
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                deleting = false;
                                ArrayList<Integer> ids = playAdapter.getSelectedIds();
                                String root = Environment.getRootDirectory().toString();
                                File docs, gameFile;
                                FileOutputStream fos;
                                ObjectOutputStream oos;
                                WalkerGame sGame;

                                for (Integer a : ids) {
                                    sGame = dbHelper.getGame(a);
                                    sGame.setCheckpoints(dbHelper.getCheckpoints(a));
                                    docs = new File(Environment.getExternalStorageDirectory() + "/theWalker/");
                                    if (!docs.exists()) {
                                        docs.mkdirs();
                                    }
                                    gameFile = new File(Environment.getExternalStorageDirectory() + "/theWalker/" + sGame.getTitle() + ".wg");
                                    fos = new FileOutputStream(gameFile);
                                    oos = new ObjectOutputStream(fos);

                                    oos.writeObject(sGame);
                                    oos.close();
                                    fos.close();
                                }

                                playAdapter.delete(false);
                                playAdapter.notifyDataSetChanged();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                    nfcButton = (Button) share.findViewById(R.id.nfc);
                    nfcButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    share.show();
                }
            }
        });
    }

    void makeList(File f)
    {
        if (f.equals(root))
        {
            upButton.setEnabled(false);
        }
        else
        {
            upButton.setEnabled(true);
        }

        currFolder = f;
        folderView.setText(f.getPath());

        files = f.listFiles();
        fileList.clear();

        for(File a : files)
        {
            fileList.add(a.getName());
        }

        ArrayAdapter<String> dirList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        dirView.setAdapter(dirList);
    }

    @Override
    protected void onDestroy()
    {
        //dbHelper.deleteAll();
        dbHelper.close();
        super.onDestroy();
    }
}
