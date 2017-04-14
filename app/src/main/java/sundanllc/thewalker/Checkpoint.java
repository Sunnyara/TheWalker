package sundanllc.thewalker;

import java.io.Serializable;

/**
 * Created by Sunnara on 2/8/2017.
 */

public class Checkpoint implements Serializable {
    private long id;
    private String mAddress;
    private float mX;
    private float mY;
    private String hint1, hint2, hint3, hint4;
    private int type;

    public Checkpoint(long id, float x, float y, String address,
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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Checkpoint() {}

    public long getId() {
        return id;
    }

    public float getX() {
        return mX;
    }

    public void setX(float mX) {
        this.mX = mX;
    }


    public String getAddress() {
        return mAddress;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public float getY() {
        return mY;
    }

    public void setY(float mY) {
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
