package sundanllc.thewalker;

/**
 * @author Sunnara
 * @version 1.0
 *          This was created on 3/17/2017
 *          Description -
 */

public class Clue {
    private String description;
    private int number;

    public Clue(int number, String description) {
        setNumber(number);
        setDescription(description);
    }

    public Clue() {
        description ="";
        number = 1;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
