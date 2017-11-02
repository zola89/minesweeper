package minesweeper;

import java.util.Observable;

/**
 * Model for Minesweeper
 *
 * @author jsaalfeld
 *
 */
public class Model extends Observable {

    //No one frome the outside should manipulate this
    private Field[][] field;
    private int height;
    private int width;
    private int bombs;
    private int bombs_left;
    private String state;
    private int revealed;
    /**
     * Constructor
     *
     * @param width	width of the game area
     * @param height	heigth of the game area
     * @param bombs	number of Bombs
     */
    public Model(int width, int height, int bombs) {

        //Creates the Game field as an Array
        this.field = new Field[height][width];
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.bombs_left = bombs;
        this.revealed = 0;
        this.state = "running";

        buildGameBoard();

    }

    /**
     * Creates the Gameboads
     */
    public void buildGameBoard() {
        //set all the fields
        setAllFields();

        //"random" selection of Bombs
        for (int i = 0; i < bombs; i++) {
            int width, height;

            do {

                width = (int) (Math.random() * (this.width));
                height = (int) (Math.random() * (this.height));

            } while (this.field[height][width].getField_id() == 9);

            this.field[height][width].setBomb();
            bombcounter(getField(height, width));
        }
    }

    /**
     *
     * @param y height
     * @param x width
     * @return count of bombs around
     */
    public int nearbombs(int y, int x) {

        int result = 0;
        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = y + i;
                ax = x + j;
                if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
                    if (this.field[ay][ax].getField_id() == 9) {
                        result++;
                    }
                }

            }
        }
        return result;

    }

    /**
     * Add 1 to all fields around
     *
     * @param field
     */
    public void bombcounter(Field field) {
        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = field.getPosy() + i;
                ax = field.getPosx() + j;

                if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
                    if (this.field[ay][ax].getField_id() != 9) {
                        this.field[ay][ax].add1();
                    }
                }

            }
        }
    }

    /**
     * Opens all zeros and reveals them
     *
     * @param field
     */
    public void revealZeros(Field field) {
        int x = field.getPosx();
        int y = field.getPosy();

        int ax;
        int ay;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                ay = y + i;
                ax = x + j;

                if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
                        this.field[ay][ax].reveal();
                }

            }
        }

    }

    /**
     * Prints the field to console (for Debugging Stuff)
     */
    public void printArray() {

        System.out.println();
        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                System.out.print(field[i][j].getField_id() + " ");
            }

            System.out.println();

        }
    }

    /**
     * Sets the State
     *
     * @param state Status
     */
    public void setState(String state) {
        this.state = state;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Sets all the feilds
     */
    private void setAllFields() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.field[i][j] = new Field(this, j, i, 0);
            }
        }
    }

    /**
     * Resets all fields to 0
     */
    private void resetAllFields() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.field[i][j].Init(this, j, i, 0);
            }
        }
    }

    /**
     * Adds one to the number of revelead bombs and changes the state to won
     * if it was won.
     */
    public void addToRevealed() {
        this.revealed++;
        if (this.revealed >= ((this.width * this.height) - bombs)) {
            setState("won");

        }
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * 
     * @return Heigth of the Game
     */
    public int getHeight() {
        return this.height;
    }

    /**
     *
     * @return Width of The Game
     */
    public int getWidth() {
        return this.width;
    }

    /**
     *
     * @return State of the Game
     */
    public String getState() {
        return this.state;
    }

    /**
     *
     * @param height
     * @param width
     * @return Field at this position
     */
    public Field getField(int height, int width) {
        return this.field[height][width];
    }

    /**
     * 
     * @return the field as an Array
     */
    public Field[][] getFields() {
        return this.field;
    }

    /**
     *
     * @return number of Bombs
     */
    public int getBombs() {
        return this.bombs;
    }
}
