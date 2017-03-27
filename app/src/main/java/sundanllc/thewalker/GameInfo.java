package sundanllc.thewalker;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Daniel on 2/15/2017.
 */

public class GameInfo extends AppCompatActivity
{
    private TextView titleView, authorView, etaView, checkpointsView, distanceView, descView;
    private ImageView picView;
    private Button playButton;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_info_activity);
        int id = 1;
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        GameHelper infoHelper = new GameHelper(this);
        final WalkerGame game = infoHelper.getGame(id);
        setupViews();
        titleView.setText(game.getTitle());
        authorView.setText(game.getAuthor());
        etaView.setText(Integer.toString(game.getEta()));
        checkpointsView.setText(Integer.toString(infoHelper.getCheckpoints(id).size()));
        distanceView.setText("4");
        descView.setText(game.getDescription());
        picView.setImageBitmap(game.getPicture());
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent currentGame = new Intent(view.getContext(), CurrentGame.class);
                currentGame.putExtra("id", game.getId());
                startActivity(currentGame);
            }
        });
    }

    private void setupViews()
    {
        titleView = (TextView) findViewById(R.id.titleView);
        authorView = (TextView) findViewById(R.id.authorView);
        etaView = (TextView) findViewById(R.id.etaView);
        checkpointsView = (TextView) findViewById(R.id.checkView);
        distanceView = (TextView) findViewById(R.id.disView);
        descView = (TextView) findViewById(R.id.descView);
        picView = (ImageView) findViewById(R.id.picView);
        playButton = (Button) findViewById(R.id.playButton);
    }
}
