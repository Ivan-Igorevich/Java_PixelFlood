package pixel;
/*
 * Created by ivanovcinnikov on 04.01.17.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PixelFlood extends JFrame {
    private static GameField gameField;
    private static GameOver gameOver;
    private PixelFlood game;
    private static Field gaming_field;
    private static final int WINDOW_SIZE = 400;
    private static int CELLS_AMNT = 5;
    private static int LEVEL = 1;
    private static int MAX_MOVES = 30;
    private int numOfMoves = 0;

    private static Color[] used_colors = {
            Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN,
            Color.CYAN, Color.MAGENTA, Color.BLACK, Color.GRAY
    };

    private boolean check(int i, int j, Field f, byte newColor, byte oldColor){
        if((i >= 0) &&
                (j >= 0) &&
                (i < f.getHeight()) &&
                (j < f.getWidth()) &&
                (newColor != oldColor)
                )
        {
            if (f.getCellColor(i, j) == oldColor) {
                f.setCell(i, j, newColor);
                check(i, j + 1, f, newColor, oldColor);
                check(i + 1, j, f, newColor, oldColor);
                check(i, j - 1, f, newColor, oldColor);
                check(i - 1, j, f, newColor, oldColor);
            }
        }
        return false;
    }

    private static boolean checkWin(Field f){
        for (int i = 0; i < f.getHeight(); i++) {
            for (int j = 0; j < f.getWidth(); j++) {
                if(f.getCellColor(i, j) != f.getCellColor(0,0))
                    return false;
            }
        }
        return true;
    }

    private void flood(Field f, byte c){
        byte oldColor = f.getCellColor(0, 0);
        check(0, 0, f, c, oldColor);
    }

    private void start(){
        game = new PixelFlood();
        gaming_field = new Field(CELLS_AMNT, CELLS_AMNT);
        gaming_field.init(LEVEL);
        gameField = new GameField();
        gameOver = new GameOver();

        gameField.setSize(WINDOW_SIZE,WINDOW_SIZE);
        gameField.setBackground(Color.WHITE);
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!gameOver.getGameOver()) {
                    numOfMoves++;
                    if(numOfMoves <= MAX_MOVES) {
                        game.setTitle("Pixel Flood. Moves: " + numOfMoves + " of " + MAX_MOVES + " (size: " + CELLS_AMNT + ", level: " + LEVEL + ")");
                        int x = e.getX();
                        int y = e.getY();
                        int i = x / (WINDOW_SIZE / CELLS_AMNT);
                        int j = y / (WINDOW_SIZE / CELLS_AMNT);
                        flood(gaming_field, gaming_field.getCellColor(i, j));
                        gameField.repaint();
                    } else {
                        gameOver.setGameOver(true);
                        game.setTitle("Pixel Flood. Out of moves, try again!");
                        //TODO out of moves
                    }
                }
                else
                {
                    gameOver.setGameOver(false);
                    numOfMoves = 0;
                    game.dispose();
                    game.start();
                }
            }
        });

        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setLocation(200,200);
        game.setSize(WINDOW_SIZE, WINDOW_SIZE + 20);
        game.add(BorderLayout.CENTER, gameField);
        game.setResizable(false);
        game.setVisible(true);
        game.setTitle("Pixel Flood. Moves: " + numOfMoves + " of " + MAX_MOVES + " (size: " + CELLS_AMNT + ", level: " + LEVEL + ")");
        gameField.repaint();

    }

    private class GameField extends JPanel {
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            try {
                for (int i = 0; i < gaming_field.getHeight(); i++) {
                    for (int j = 0; j < gaming_field.getWidth(); j++) {
                        g.setColor(used_colors[gaming_field.getField()[i][j]]);
                        g.fillRect(Math.round(i * (WINDOW_SIZE / CELLS_AMNT)),
                                Math.round(j * (WINDOW_SIZE / CELLS_AMNT)),
                                Math.round(WINDOW_SIZE / CELLS_AMNT),
                                Math.round(WINDOW_SIZE / CELLS_AMNT));
                    }
                }

                if(checkWin(gaming_field)){
                    //TODO round won!
                    gameOver.setGameOver(true);
                    if(CELLS_AMNT < 20)
                        CELLS_AMNT++;
                    else {
                        CELLS_AMNT = 5;
                        if(LEVEL < 7)
                            LEVEL++;
                        else
                            MAX_MOVES--;
                    }
                }

            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PixelFlood().start();
    }
}
