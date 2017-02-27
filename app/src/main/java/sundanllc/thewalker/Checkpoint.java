package sundanllc.thewalker;

/**
 * Created by Sunnara on 2/8/2017.
 */

public class Checkpoint {
    private int id;
    private String mAddress;
    private String mX;
    private String mY;
    private String hint1, hint2, hint3, hint4;

    public Checkpoint(int id, String address, String x, String y,
                      String hint1, String hint2, String hint3, String hint4) {
        setId(id);
        setAddress(address);
        setX(x);
        setY(y);
        setHint1(hint1);
        setHint2(hint2);
        setHint3(hint3);
        setHint4(hint4);
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

    public String getHint3() {
        return hint3;
    }

    public void setHint3(String hint3) {
        this.hint3 = hint3;
    }

    public String getHint4() {
        return hint4;
    }

    public void setHint4(String hint4) {
        this.hint4 = hint4;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }
}
