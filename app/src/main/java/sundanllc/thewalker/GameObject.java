package sundanllc.thewalker;

import android.graphics.Bitmap;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/23/2017
 *          Description -
 */

public class GameObject {
    private String title, author, description;
    private Bitmap thumbnail;
    private long eta, time_played;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getEta() {
        return eta;
    }

    public void setEta(long eta) {
        this.eta = eta;
    }

    public long getTime_played() {
        return time_played;
    }

    public void setTime_played(long time_played) {
        this.time_played = time_played;
    }
}
