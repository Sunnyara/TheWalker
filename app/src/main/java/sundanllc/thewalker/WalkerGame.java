package sundanllc.thewalker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sunnara on 2/27/2017.
 */

public class WalkerGame implements Serializable {
    private int id;
    private String title, author, description;
    private byte[] picture;
    private int eta, time_played;
    private boolean isSelected;
    private boolean isCreator;
    private ArrayList<Checkpoint> checkpoints;

    public WalkerGame(String title, String author, String description, Bitmap picture, int eta,
                      int time_played, boolean creator) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.picture = BlobFactory.getBytes(picture);
        this.eta = eta;
        this.time_played = time_played;
        this.isSelected = false;
        this.isCreator = creator;
    }

    public WalkerGame(String title, String author, String description, Bitmap picture, int eta,
                      int time_played) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.picture = BlobFactory.getBytes(picture);
        this.eta = eta;
        this.time_played = time_played;
        this.isSelected = false;
        this.isCreator = false;
    }

    public WalkerGame() {this.isSelected = false;}

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPicture() {
        return BlobFactory.getImage(picture);
    }

    public void setPicture(Bitmap picture) {
        this.picture = BlobFactory.getBytes(picture);
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public int getTime_played() {
        return time_played;
    }

    public void setTime_played(int time_played) {
        this.time_played = time_played;
    }

    public ArrayList<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(ArrayList<Checkpoint> checkpoints) {
        this.checkpoints = checkpoints;
    }
}
