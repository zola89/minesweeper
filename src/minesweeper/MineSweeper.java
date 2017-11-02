package minesweeper;

import java.io.IOException;

import javax.swing.JFrame;

public class MineSweeper {

    /**
     * Starts your Minesweeper Game
     * @param argv You can either pass your Level as parameter of 3 parameters for
     *             width, heigth and bombs
     * @throws IOException
     */
    public static void main(String[] argv) throws IOException {
        int width = 9;
        int height = 9;
        int bombs = 10;

        Model model = new Model(width, height, bombs);
        
        //Information about the game
        System.out.println("\n\nCreating Minesweeper:");
        System.out.println("Heigth: " + height);
        System.out.println("Width: " + width);
        System.out.println("Bombs: " + bombs);

        //Creating the View
        View view = new View(model);

        JFrame frame = new JFrame("Minesweeper");

        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

    }
}
