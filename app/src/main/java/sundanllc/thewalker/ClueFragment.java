package sundanllc.thewalker;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/17/2017
 *          Description -
 */

public class ClueFragment extends android.support.v4.app.Fragment{
    private static final int CLUE_AMT = 4;
    private TextView clueNum, clueDesc;
    private Clue clue;
    private int index;
    private ArrayList<Clue> clues = new ArrayList<>();


    public ClueFragment() {
        clues = new ArrayList<>();
        clues.add(new Clue(0,"blank"));
        clues.add(new Clue(1,"blank"));
        clues.add(new Clue(2,"blank"));
        clues.add(new Clue(3,"blank"));
    }

    public static ClueFragment newInstance(int index) {
        Bundle args = new Bundle();
        ClueFragment cf = new ClueFragment();
        cf.index = index;
        return cf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clues = new ArrayList<>();
        clues.add(new Clue(0,"blank"));
        clues.add(new Clue(1,"blank"));
        clues.add(new Clue(2,"blank"));
        clues.add(new Clue(3,"blank"));
        //Retreive Clue from db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clue_fragment,container, false);
        clueNum = (TextView) view.findViewById(R.id.cluenum);
        //clueNum.setText(clues.get(index).getNumber());
        clueNum.setText("1");
        clueDesc = (TextView) view.findViewById(R.id.cluedesc);
        //clueDesc.setText(clues.get(index).getDescription());
        clueDesc.setText("Hello");
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }


}