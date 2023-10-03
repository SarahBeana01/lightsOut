package com.zybooks.lightsout;

import android.util.Log;

import java.util.Random;
// This class is used to generate random numbers

public class lightsOutGame {
// Creates public class called lightsOutGame
    public static final int GRID_SIZE = 3;
    // Creates a public static integer called GRID_SIZE that holds a value of 3

    // Lights that make up the grid
    private final boolean[][] mLightsGrid;
    // Creates private boolean variable called mLightsGrid

    public lightsOutGame() {
    // Creates public function called lightsOutGame
        mLightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
        // Creates new boolean grid variable called mLightsGrid that consists of a 3x3 grid
    }

    public void newGame() {
    // Creates public function called newGame
        Random randomNumGenerator = new Random();
        // Creates a new random variable called randomNumGenerator
        for (int row = 0; row < GRID_SIZE; row++) {
        // for loop that starts at row=0, increases row by 1, and ends when the row is equal to the GRID_SIZE variable. This will include all rows in the grid.
            for (int col = 0; col < GRID_SIZE; col++) {
                // for loop that starts at col=0, increases col by 1, and ends when the col is equal to the GRID_SIZE variable. This will include all columns in the grid.
                mLightsGrid[row][col] = randomNumGenerator.nextBoolean();
                // Changes the boolean grid variable mLightsGrid to randomize the boolean variables for each square
            }
        }
    }

    public boolean isLightOn(int row, int col) {
    // Creates public boolean function called isLightOn, using the parameters integers row and col
        return mLightsGrid[row][col];
        // returns the variable mLightsGrid with said row and col parameters
    }

    public void selectLight(int row, int col) {
    // Creates public function called selectLight with parameters integers row and col
        mLightsGrid[row][col] = !mLightsGrid[row][col];
        // Sets the indicated square button of mLightsGrid to be the opposite boolean value
        if (row > 0) {
        // if statement that will run if the row variable is greater than 0
            mLightsGrid[row - 1][col] = !mLightsGrid[row - 1][col];
            // Sets the indicated square button of mLightsGrid to be the opposite boolean value
        }
        if (row < GRID_SIZE - 1) {
        // if statement that will run if the row variable is less than the GRID_SIZE variable minus 1
            mLightsGrid[row + 1][col] = !mLightsGrid[row + 1][col];
            // Sets the indicated square button of mLightsGrid to be the opposite boolean value
        }
        if (col > 0) {
        // if statement that will run if the col variable is greater than 0
            mLightsGrid[row][col - 1] = !mLightsGrid[row][col - 1];
            // Sets the indicated square button of mLightsGrid to be the opposite boolean value
        }
        if (col < GRID_SIZE - 1) {
        // if statement that will run if the col variable is less than the GRID_SIZE variable minus 1
            mLightsGrid[row][col + 1] = !mLightsGrid[row][col + 1];
            // Sets the indicated square button of mLightsGrid to be the opposite boolean value
        }
    }

    public boolean isGameOver() {
    // Creates public boolean function called isGameOver
        for (int row = 0; row < GRID_SIZE; row++) {
        // for loop that starts at integer row=0, increases row by 1 every run, and stops when row is equal to the variable GRID_SIZE
            for (int col = 0; col < GRID_SIZE; col++) {
            // for loop that starts at integer col=0, increases col by 1 every run, and stops when col is equal to the variable GRID_SIZE
                if (mLightsGrid[row][col]) {
                // if statement checking if the square button on the mLightGrid grid at the indicated row and column is false (black)
                    return false;
                    // Makes the boolean false, then exits the function
                }
            }
        }
        return true;
        // Makes the boolean true, then exits the function
    }
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                char value = mLightsGrid[row][col] ? 'T' : 'F';
                boardString.append(value);
            }
        }
        Log.d("LightsOutGame","State: " + boardString.toString());

        return boardString.toString();
    }

    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = gameState.charAt(index) == 'T';
                index++;
            }
        }
    }
}