package game;

public class Gameboard {
    private Field [] gameboard = new Field[25];
    private boolean brueckeHochgezogen = false;
    private boolean maulwurfLochUntenOffen = true;
    private boolean maulwurfLockObenOffen = true;
    private int anzahlDrehungen = 0;

    private String aktivierungsModus = "Maulwurf";
    private String aktuelleAktivierung = "Maulwurf";

    public Gameboard() {
        for (int i = 0; i < gameboard.length; i++) {
            gameboard[i] = new Field(i+1, false, false, false, false);
            gameboard[3].setMoleHole(true);
            gameboard[6].setMoleHole(true);
            gameboard[9].setMoleHole(true);
            gameboard[12].setMoleHole(true);
            gameboard[15].setMoleHole(true);
            gameboard[18].setMoleHole(true);
            gameboard[20].setMoleHole(true);
            gameboard[22].setMoleHole(true);
            gameboard[24].setMoleHole(true);

        }
    }

    public void moveRabbit(int anzahlFelder, int rabbit) {
        //TODO
    }



}
