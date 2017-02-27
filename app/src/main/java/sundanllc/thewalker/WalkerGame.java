package sundanllc.thewalker;

import android.graphics.Bitmap;

/**
 * Created by Sunnara on 2/27/2017.
 */

public class WalkerGame {
    private int id;
    private String title, author, description;
    private Bitmap picture;
    private int eta, time_played;

    public WalkerGame(String title, String author, String description, Bitmap picture, int eta,
                      int time_played) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.picture = picture;
        this.eta = eta;
        this.time_played = time_played;
    }

    public WalkerGame() {}

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
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
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
}
