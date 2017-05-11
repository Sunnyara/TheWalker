package sundanllc.thewalker;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * Created by Daniel on 2/6/2017.
 */

public class PlayMenu extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 17;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 42;
    private ImageButton addButton, removeButton, searchButton, shareButton;
    private Button nfcButton, saveButton;
    private NfcAdapter nfcAdapter;
    private RecyclerView playRecycler;
    private PlayAdapter playAdapter;
    private RecyclerView.LayoutManager playLM;
    private GameHelper dbHelper;
    private boolean deleting;
    private ArrayList<WalkerGame> game;
    private List<String> fileList = new ArrayList<String>();
    private ListView dirView;
    private File[] files;
    private Button upButton;
    private TextView folderView;
    private File root, currFolder, parentPath;
    private Context context;
    private Intent recIntent;
    private Handler timeHandle = new Handler();
    private long millis;
    private SystemClock sc;
    public Runnable nfcCheck = new Runnable() {
        @Override
        public void run() {
            File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File[] nfcFiles = downloads.listFiles();
            for (File a : nfcFiles)
            {
                if (a.getName().contains(".wg"))
                {
                    ObjectInputStream ois;
                    try {
                        ois = new ObjectInputStream(new FileInputStream(a));
                        WalkerGame gameObject = (WalkerGame) ois.readObject();
                        long wid = dbHelper.insertGame(gameObject);
                        for (Checkpoint b : gameObject.getCheckpoints())
                        {
                            b.setId(wid);
                            dbHelper.insertCheckpoint(b);
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
                    a.delete();
                }
            }
            if (millis + 30000 < SystemClock.uptimeMillis()) timeHandle.removeCallbacks(nfcCheck);
            else timeHandle.postDelayed(nfcCheck, 1);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        context = this;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.NFC}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        playRecycler = (RecyclerView) findViewById(R.id.playRecycler);
        playLM = new LinearLayoutManager(this);
        playRecycler.setLayoutManager(playLM);

        dbHelper = new GameHelper(this);
        game = dbHelper.getGames();

        playAdapter = new PlayAdapter(game, 0);
        playRecycler.setAdapter(playAdapter);

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        currFolder = new File(root.getAbsolutePath()+"/thewalker");

        addButton = (ImageButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog share = new Dialog((v.getContext()));
                share.setContentView(R.layout.nfc_dialog);
                share.setCancelable(true);
                share.setCanceledOnTouchOutside(true);
                TextView shareView = (TextView) share.findViewById(R.id.shareView);
                shareView.setText("Choose how to recieve your game");
                saveButton = (Button) share.findViewById(R.id.save);
                saveButton.setText("File Explorer");
                saveButton.setOnClickListener(new View.OnClickListener() {
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
                        makeList(currFolder);
                        add.show();
                    }
                });
                nfcButton = (Button) share.findViewById(R.id.nfc);
                nfcButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        millis = SystemClock.uptimeMillis();
                        timeHandle.postDelayed(nfcCheck, 20000);
                    }
                });
                share.show();
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
                    deleting = false;
                    playAdapter.delete(false);
                    playAdapter.updateDataset(dbHelper.getGames());
                    playAdapter.notifyDataSetChanged();
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
                                    docs = new File(Environment.getExternalStorageDirectory() + "/thewalker/");
                                    if (!docs.exists()) {
                                        docs.mkdirs();
                                    }
                                    gameFile = new File(Environment.getExternalStorageDirectory() + "/thewalker/" + sGame.getTitle() + ".wg");
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
                            try {
                                deleting = false;
                                PackageManager pm = context.getPackageManager();
                                // Check whether NFC is available on device
                                if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
                                    // NFC is not available on the device.
                                    Toast.makeText(context, "The device does not has NFC hardware.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                // Check whether device is running Android 4.1 or higher
                                else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    // Android Beam feature is not supported.
                                    Toast.makeText(context, "Android Beam is not supported.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    // NFC and Android Beam file transfer is supported.
                                    Toast.makeText(context, "Android Beam is supported on your device.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                nfcAdapter = NfcAdapter.getDefaultAdapter(context);
                                if(!nfcAdapter.isNdefPushEnabled()) Toast.makeText(context, "Android Beam is not enabled.", Toast.LENGTH_SHORT).show();
                                ArrayList<Integer> ids = playAdapter.getSelectedIds();
                                String root = Environment.getRootDirectory().toString();
                                File docs, gameFile;
                                FileOutputStream fos;
                                ObjectOutputStream oos;
                                WalkerGame sGame;

                                for (Integer a : ids) {
                                    sGame = dbHelper.getGame(a);
                                    sGame.setCheckpoints(dbHelper.getCheckpoints(a));
                                    docs = new File(Environment.getExternalStorageDirectory() + "/thewalker/nfc");
                                    if (!docs.exists()) {
                                        docs.mkdirs();
                                    }
                                    gameFile = new File(Environment.getExternalStorageDirectory() + "/thewalker/nfc/" + sGame.getTitle() + ".wg");
                                    fos = new FileOutputStream(gameFile);
                                    oos = new ObjectOutputStream(fos);

                                    oos.writeObject(sGame);
                                    oos.close();
                                    fos.close();

                                    gameFile.setReadable(true, false);
                                    Uri uri = Uri.fromFile(gameFile);
                                    nfcAdapter.setBeamPushUris(new Uri[]{uri}, (Activity) context);
                                    nfcAdapter.invokeBeam((Activity) context);
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

    @Override
    public void onResume()
    {
        super.onResume();
        playAdapter.updateDataset(dbHelper.getGames());
    }
}
