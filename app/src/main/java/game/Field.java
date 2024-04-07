package game;

public class Field {
    private int position;
    private boolean rabbit;
    private boolean mole;
    private boolean moleHole;
    private boolean moleHoleOpen;

    public Field(int position, boolean rabbit, boolean mole, boolean moleHole, boolean moleHoleOpen) {
        this.position = position;
        this.rabbit = rabbit;
        this.mole = mole;
        this.moleHole = moleHole;
        this.moleHoleOpen = false;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRabbit() {
        return rabbit;
    }

    public void setRabbit(boolean rabbit) {
        this.rabbit = rabbit;
    }

    public boolean isMole() {
        return mole;
    }

    public void setMole(boolean mole) {
        this.mole = mole;
    }

    public boolean isMoleHole() {
        return moleHole;
    }

    public void setMoleHole(boolean moleHole) {
        this.moleHole = moleHole;
    }

    public boolean isMoleHoleOpen() {
        return moleHoleOpen;
    }

    public void setMoleHoleOpen(boolean moleHoleOpen) {
        this.moleHoleOpen = moleHoleOpen;
    }
}
