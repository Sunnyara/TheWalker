package sundanllc.thewalker;


//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sunnara on 2/27/2017.
 */


/**
public class CurrentGame extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }5

    @Override
    public void onPause() {
        super.onPause();
    }
}

 */

public class CurrentGame extends FragmentActivity {

    private static final int CLUE_AMT = 4;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TextView clueNum, clueDesc;
    private ArrayList<Clue> clues;
    private Clue clue;


    public CurrentGame() {
        /**
        clues = new ArrayList<>();
        clues.add(new Clue(0,"blank"));
        clues.add(new Clue(1,"blank"));
        clues.add(new Clue(2,"blank"));
        clues.add(new Clue(3,"blank"));
         **/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_game_layout);
        pager = (ViewPager) findViewById(R.id.cluepager);
        pagerAdapter = new ClueSlideAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        /**
        clueNum = (TextView) findViewById(R.id.cluenum);
        clueDesc = (TextView) findViewById(R.id.cluedesc);
        clueNum.setText("Clue " + clues.get(0).getNumber() + "");
        clueDesc.setText(clues.get(0).getDescription());
        **/
    }




    private class ClueSlideAdapter extends FragmentStatePagerAdapter {

        public ClueSlideAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new ClueFragment();
        }



        @Override
        public int getCount() {
            return CLUE_AMT;
        }
    }
}

