package sundanllc.thewalker;

/**
 * Created by Sunnara on 2/8/2017.
 */

public class Checkpoint {
    private int id;
    private String mAddress;
    private String mX;
    private String mY;

    public Checkpoint(int id, String address, String x, String y) {
        setId(id);
        setAddress(address);
        setX(x);
        setY(y);

    }

    public int getId() {
        return id;
    }

    public String getX() {
        return mX;
    }

    public void setX(String mX) {
        this.mX = mX;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getY() {
        return mY;
    }

    public void setY(String mY) {
        this.mY = mY;
    }


}
