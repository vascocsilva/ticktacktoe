package com.example.vascosilva.fourinline;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int currentPlayer = 1;
    // 0 means unplayed
    int[] gameStatus = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPositions = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };
    int winner;
    boolean hasWinner = false;
    int plays = 0;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int positionClicked = Integer.parseInt(view.getTag().toString());



        if (gameStatus[positionClicked] == 0 && !hasWinner) {
            plays++;
            Log.i("info", "position clicked " + positionClicked);
            gameStatus[positionClicked] = currentPlayer;

            counter.setTranslationY(-1000f);

            if (currentPlayer == 1) {
                counter.setImageResource(R.drawable.black);
                currentPlayer = 2;
            } else {
                counter.setImageResource(R.drawable.red);
                currentPlayer = 1;
            }

            counter.animate()
                    .translationYBy(1000f)
                    .setDuration(300);

            for (int[] winningPosition: winningPositions) {
                if (gameStatus[winningPosition[0]] == gameStatus[winningPosition[1]] &&
                        gameStatus[winningPosition[1]] == gameStatus[winningPosition[2]] &&
                        gameStatus[winningPosition[0]] != 0) {
                    if (currentPlayer == 1) {
                        winner = 2;
                    } else {
                        winner = 1;
                    }
                    TextView msg = findViewById(R.id.gameMessage);
                    msg.setText("Player " + winner + " Wins!");
                    showPlayAgainButton();
                    hasWinner = true;
                }
            }

            if (plays > 8 && hasWinner == false) {
                showPlayAgainButton();
            }
        }
    }

    public void showPlayAgainButton() {
        findViewById(R.id.playAgainButtton).animate().alpha(1).setDuration(1000);
    }

    public void resetGrid() {
        android.support.v7.widget.GridLayout gameB = (android.support.v7.widget.GridLayout) findViewById(R.id.gameBoard);

        for (int i = 0; i < gameB.getChildCount(); i++) {
            ImageView img = (ImageView) gameB.getChildAt(i);
            img.setImageResource(0);
        }
    }

    public void playAgain(View view) {
        for (int i = 0; i < gameStatus.length; i++) {
            gameStatus[i] = 0;
        }

        plays = 0;
        hasWinner = false;
        currentPlayer = 1;
        TextView msg = findViewById(R.id.gameMessage);
        msg.setText("");
        findViewById(R.id.playAgainButtton).animate().alpha(0).setDuration(1000);
        resetGrid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
