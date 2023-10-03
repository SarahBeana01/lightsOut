package com.zybooks.lightsout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//  This class is used to pass data between activities and save the state using key value pairs (dictionary)

import android.view.View;
// This class represents the basic building block for user interface components. A view occupies a rectangular area on the screen and is responsible for drawing and event handling.

import android.widget.Button;
// This class is used to trigger an action from the interaction with the button

import android.widget.GridLayout;
// This class is used to make the ability to display elements in a grid

import android.widget.Toast;
// This class is used to display a pop-up text for a temporary amount of time

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
// This class makes the app more compatible and consistent across different versions on Android

import androidx.core.content.ContextCompat;
// This class makes resources more easy to access, and backwards compatibility

public class MainActivity extends AppCompatActivity {
// Creates public class that is compatible across different versions on Android

    private int mLightOnColorId;

    private final String GAME_STATE = "gameState";

    private lightsOutGame mGame;
    // Creates a private variable using the lightsOutGame class called mGame

    private GridLayout mLightGrid;
    // Creates a private variable using the GridLayout widget called mLightGrid

    private int mLightOnColor;
    // Creates private integer called mLightOnColor

    private int mLightOffColor;
    // Creates private integer called mLightOffColor

    @Override
    // Lets you override a method in the superclass

    protected void onCreate(Bundle savedInstanceState) {
        // Calls and (re)creates an activity with data from savedInstanceState

        mLightOnColorId = R.color.yellow;

        super.onCreate(savedInstanceState);
        // Runs the savedInstanceState code

        setContentView(R.layout.activity_main);
        // Sets the main layout of app

        mLightGrid = findViewById(R.id.light_grid);
        // Creates variable with id light_grid

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            // For loop that includes all squares in the mLightGrid
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            // Creates a button for each square in the mLightGrid
            gridButton.setOnClickListener(this::onLightButtonClick);
            // Lets each button in grid listen to the activity onLightButtonClick when clicked
        }

        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        // Creates variable that can change color of context to yellow

        mLightOffColor = ContextCompat.getColor(this, R.color.black);
        // Creates variable that can change color of context to black

        mGame = new lightsOutGame();
        // Creates a new class of lightsOutGame from lightsOutGame.java called mGame
        if (savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setButtonColors();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }
        // Calls function startGame

    private void startGame() {
    // Creates a private function called startGame
        mGame.newGame();
        // Creates a new game called mGame
        setButtonColors();
        // Calls function setButtonColors
    }

    private void onLightButtonClick(View view) {
    // Creates private function called onLightButtonClick

        // Find the button's row and col
        int buttonIndex = mLightGrid.indexOfChild(view);
        // Creates integer called buttonIndex that is a button square in the mLightGrid grid
        int row = buttonIndex / lightsOutGame.GRID_SIZE;
        // Creates integer called row that holds the said button above's row placement by dividing the index of button by the grid size of the lightsOutGame
        int col = buttonIndex % lightsOutGame.GRID_SIZE;
        // Creates integer called col that holds the said button above's column placement by taking the remainder of the index of the button divided by the grid size of the lightsOutGame

        mGame.selectLight(row, col);
        // Calls function selectLight from lightsOutGame.java and uses the parameters row and col for mGame
        setButtonColors();
        // Calls function setButtonColors

        if (mGame.isGameOver()) {
        // if statement checking to see if the game mGame meets the requirements of the isGameOver function
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
            // Calls toast to display a short pop-up text "Congratulations" to user
        }
    }

    private void setButtonColors() {
        // Creates private function called setButtonColors

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
        // For loop to run through every square in the mLightGrid grid starting at index 0 and ending when the buttonIndex is equal to the total number of squares in the mLightGrid grid
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            // Creates a button for each square in the mLightGrid grid

            // Find the button's row and col
            int row = buttonIndex / lightsOutGame.GRID_SIZE;
            // Creates integer called row that holds the said button above's row placement by dividing the index of button by the grid size of the lightsOutGame
            int col = buttonIndex % lightsOutGame.GRID_SIZE;
            // Creates integer called col that holds the said button above's column placement by taking the remainder of the index of the button divided by the grid size of the lightsOutGame

            if (mGame.isLightOn(row, col)) {
            // if statement that will check the square of the row and col parameters to function isLightOn and will run if the light is "on" (yellow)
                gridButton.setBackgroundColor(mLightOnColor);
                // Activates the setBackgroundColor function for the gridButton called and sets it equal to variable mLightOnColor (yellow)
            } else {
            // else statement that will run if the previous if statement above was not called, and will not run if the previous if statement above was called
                gridButton.setBackgroundColor(mLightOffColor);
                // Activates the setBackgroundColor function for the gridButton called and sets it equal to variable mLightOffColor (black)
            }
        }
    }

    public void onNewGameClick(View view) {
    // Creates public function called onNewGameClick
        startGame();
        // Calls function startGame
    }

    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void onChangeColorClick(View view) {
        // Send the current color ID to ColorActivity
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.EXTRA_COLOR, mLightOnColorId);
        mColorResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Create the "on" button color from the chosen color ID from ColorActivity
                            mLightOnColorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.yellow);
                            mLightOnColor = ContextCompat.getColor(MainActivity.this, mLightOnColorId);
                            setButtonColors();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int colorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.yellow);
                            mLightOnColor = ContextCompat.getColor(MainActivity.this, colorId);
                            setButtonColors();
                        }
                    }
                }
            });
}
