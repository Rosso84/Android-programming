package no.woact.morroo16.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Collections;

import no.woact.morroo16.Dbhandler.DBsoure;
import no.woact.morroo16.Messager;
import no.woact.morroo16.tictactoe.R;

/* By Roosbeh Morandi*/



public class InGameActivity extends AppCompatActivity {

    private Firebase mFirebase;

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnExit, btnRetry, btnSave, btnQuitP1, btnQuitP2;

    private TextView tvPlayer1, tvPlayer2, tvPlayerTurn;
    private Chronometer timer;
    private String player1, player2, playerTurn, winner;
    private String bot = "TTTbot";
    private double winInTime;
    private int countWins;
    private boolean gameOver;

    private ArrayList<Button> gameButtonList = new ArrayList<>();
    private Messager msg = new Messager();

    private DBsoure mDBsoure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        Firebase.setAndroidContext(this);
        mFirebase = new Firebase("https://tictactoe-96dff.firebaseio.com/WinnerList");

        findView();
        onClick();
        initPlayers();
        newMatch();
        mDBsoure = new DBsoure(this);
        mDBsoure.open();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBsoure.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDBsoure.close();
    }

    @Override
    protected void onDestroy() {
        mDBsoure.close();
        super.onDestroy();
    }

    private void findView() {
        tvPlayer1 = (TextView) findViewById(R.id.txtVLeft);
        tvPlayer2 = (TextView) findViewById(R.id.txtVRight);
        tvPlayerTurn = (TextView) findViewById(R.id.tvPlayerTurn);
        timer = (Chronometer) findViewById(R.id.chrMetId);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnQuitP1 = (Button) findViewById(R.id.btnQuitP1);
        btnQuitP2 = (Button) findViewById(R.id.btnQuitP2);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
    }

    private void initPlayers() {
        String chosenNameP1 = getIntent().getExtras().getString(MainPageActivity.NAME_P1);
        tvPlayer1.setText(chosenNameP1);
        String chosenNameP2 = getIntent().getExtras().getString(MainPageActivity.NAME_P2);
        tvPlayer2.setText(chosenNameP2);

        player1 = chosenNameP1;
        player2 = chosenNameP2;

        if (chosenNameP1.isEmpty()) {
            player1 = bot;
            tvPlayer1.setText(bot);
            disableQuitBtns();

        } else if (chosenNameP2.isEmpty()) {
            player2 = bot;
            tvPlayer2.setText(bot);
            disableQuitBtns();
        }
    }

    private void onClick() {
        btn1.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn1);
            setRandomOnclick();
        });
        btn2.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn2);
            setRandomOnclick();
        });
        btn3.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn3);
            setRandomOnclick();
        });
        btn4.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn4);
            setRandomOnclick();
        });
        btn5.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn5);
            setRandomOnclick();
        });
        btn6.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn6);
            setRandomOnclick();
        });
        btn7.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn7);
            setRandomOnclick();
        });
        btn8.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn8);
            setRandomOnclick();
        });
        btn9.setOnClickListener((View view) -> {
            humanPlayerSetButtonXorO(btn9);
            setRandomOnclick();
        });
        btnExit.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        });
        btnQuitP1.setOnClickListener((View view) -> {
            if (playerTurn.equals(player1)) {
                msg.showMessageDialog(this, "...", player1+" has left the game");
                player1 = bot;
                tvPlayer1.setText(player1);
                setPlayerTurn(player1);
                switchPlayerToBot(player1, player2, "X");
            } else {
                msg.showMessageDialog(this, "...", player1+" has left the game");
                player1 = bot;
                tvPlayer1.setText(player1);
                switchPlayerToBot(player1, player2, "X");
            }
            disableQuitBtns();
        });

        btnQuitP2.setOnClickListener((View view) -> {
            if (playerTurn.equals(player2)) {
                msg.showMessageDialog(this, "...", player2+" has left the game");
                player2 = bot;
                tvPlayer2.setText(player2);
                setPlayerTurn(player2);
                setRandomOnclick();
            } else {
                msg.showMessageDialog(this, "...", player1+" has left the game");
                player2 = bot;
                tvPlayer2.setText(player2);
                if (player2.equals(bot)) {
                    switchPlayerToBot(player2, player1, "O");
                }
            }
            disableQuitBtns();
            disableBtnSave();
        });
        btnRetry.setOnClickListener((View view) -> {
            newMatch();
            btnRetry.getBackground().clearColorFilter();//resetter btnlayout til default
            btnExit.getBackground().clearColorFilter();// linjen lånt fra:  https://stackoverflow.com/questions/14802354/how-to-reset-a-buttons-background-color-to-default
            disableBtnSave();
        });
        btnSave.setOnClickListener((View view) -> {
            String time = String.valueOf(winInTime);
            countWins = 1;

            if (mDBsoure.nameExists(winner)) {
                mDBsoure.incrementWins(winner, countWins);
                countWins++;
                Firebase WinnerName = mFirebase.child(winner);
                WinnerName.setValue(countWins + " wins");
                msg.toastMessage(this, "saving state..");
            } else {
                mDBsoure.addNewWinner(winner, time, countWins);
                Firebase WinnerName = mFirebase.child(winner);
                WinnerName.setValue(countWins + " wins");
                msg.toastMessage(this, "new player saved..");
            }
            disableBtnSave();
        });
    }

    private void newMatch() {
        gameOver = false;
        setPlayerTurn(player1);
        resetGameButtons();
        resetTimer();
        btnSave.setEnabled(false);
        timer.start();
        enableGameButtons(true);
        if (player1.equals(bot)) {
            setRandomOnclick();
        } else if (player2.equals(bot)) {
            if (playerTurn.equals(player2)) {
                if (player2.equals(bot)) { //ville ikke uten en sjekk til av en eller annen grunn.
                    switchPlayerToBot(player2, player1, "O");
                }
            }
        }
    }

    private void resetTimer() {
        timer.setBase(SystemClock.elapsedRealtime());
    }

    private void setWinner(String player) {
        winner = player;
        gameOver = true;
        timer.stop();
        tvPlayerTurn.setText("Game over!  Winner: " + winner);

        double millSec = SystemClock.elapsedRealtime() - timer.getBase();
        double sec = millSec / 1000.0;
        winInTime = sec;

        Messager msg = new Messager();
        if (!player.equals(bot)) {
            msg.showMessageDialog(this, "Congrats " + player + "!", "You won in: \n" + sec + " sec");
        } else {
            msg.showMessageDialog(this, ":(  You Lost", player + " won in: \n" + sec + " sec");
        }

        enableGameButtons(false);
        btnRetry.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        btnExit.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        enableBtnSave();
    }


    private void humanPlayerSetButtonXorO(Button btn) {
        String btnText = btn.getText().toString();
        if (playerTurn.equals(player1) && btnText.isEmpty() && !player1.equals(bot)) {
            setXorOLayout(btn, "X");
            setPlayerTurn(player2);
            matchController();
        } else if (playerTurn.equals(player2) && btnText.isEmpty() && !player2.equals(bot)) {
            setXorOLayout(btn, "O");
            setPlayerTurn(player1);
            matchController();
        }
    }


    private void setXorOLayout(Button btn, String XorO) {
        btn.setText(XorO);
        if (XorO.equals("X")) {
            btn.setTextColor(Color.RED);//layout
        } else if (XorO.equals("O")) {
            btn.setTextColor(Color.BLUE);//layout
        }
        btn.setTextSize(80);//layout
        btn.setEnabled(false);
    }

    private void disableQuitBtns() {
        btnQuitP1.setEnabled(false);
        btnQuitP2.setEnabled(false);
    }

    private void disableBtnSave() {
        btnSave.getBackground().clearColorFilter();
        btnSave.setEnabled(false);
    }
    private void enableBtnSave() {
        btnSave.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        btnSave.setEnabled(true);
    }



    /*super easy mode*/
    private void setRandomOnclick() {
        ArrayList<Button> availableButtons = new ArrayList<>();
        if (player1.equals(bot) || player2.equals(bot)) {
            if (!gameOver) {
                addButtonsToList();
                for (int i = 0; i < gameButtonList.size(); i++) {
                    if (gameButtonList.get(i).getText().toString().isEmpty()) {
                        availableButtons.add(gameButtonList.get(i));
                    }
                }
                Collections.shuffle(availableButtons);
                Button randomButton = availableButtons.get(0);
                if (playerTurn.equals(player1)) {
                    setXorOLayout(randomButton, "X");
                    setPlayerTurn(player2);
                } else if (playerTurn.equals(player2)) {
                    setXorOLayout(randomButton, "O");
                    setPlayerTurn(player1);
                }
                availableButtons.clear();
                matchController();
            }
        }
    }



    private void switchPlayerToBot(String player, String PlayerTurn, String randomXorO) {

        ArrayList<Button> ListOfAvailableButtons = new ArrayList<>();
        if (!gameOver) {
            addButtonsToList();
            for (int i = 0; i < gameButtonList.size(); i++) {
                if (gameButtonList.get(i).getText().toString().isEmpty()) {
                    ListOfAvailableButtons.add(gameButtonList.get(i));
                }
            }
            //Randomize bot select ( super easy mode)
            Collections.shuffle(ListOfAvailableButtons);
            Button randomButton = ListOfAvailableButtons.get(0);
            if (playerTurn.equals(player)) {
                setXorOLayout(randomButton, randomXorO);
                setPlayerTurn(PlayerTurn);
                ListOfAvailableButtons.clear();
                matchController();
            }
        }
    }

    private void matchController() {

        /*player1 controller */
        if (isButtonSelected(btn2, "X") && isButtonSelected(btn3, "X") && isButtonSelected(btn1, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn4, "X") && isButtonSelected(btn5, "X") && isButtonSelected(btn6, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn7, "X") && isButtonSelected(btn8, "X") && isButtonSelected(btn9, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn7, "X") && isButtonSelected(btn5, "X") && isButtonSelected(btn3, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn1, "X") && isButtonSelected(btn5, "X") && isButtonSelected(btn9, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn1, "X") && isButtonSelected(btn4, "X") && isButtonSelected(btn7, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn2, "X") && isButtonSelected(btn5, "X") && isButtonSelected(btn8, "X")) {
            setWinner(player1);
        } else if (isButtonSelected(btn3, "X") && isButtonSelected(btn6, "X") && isButtonSelected(btn9, "X")) {
            setWinner(player1);
        }

        /*player2 controller*/
        else if (isButtonSelected(btn1, "O") && isButtonSelected(btn2, "O") && isButtonSelected(btn3, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn4, "O") && isButtonSelected(btn5, "O") && isButtonSelected(btn6, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn7, "O") && isButtonSelected(btn8, "O") && isButtonSelected(btn9, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn7, "O") && isButtonSelected(btn5, "O") && isButtonSelected(btn3, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn1, "O") && isButtonSelected(btn5, "O") && isButtonSelected(btn9, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn1, "O") && isButtonSelected(btn4, "O") && isButtonSelected(btn7, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn2, "O") && isButtonSelected(btn5, "O") && isButtonSelected(btn8, "O")) {
            setWinner(player2);
        } else if (isButtonSelected(btn3, "O") && isButtonSelected(btn6, "O") && isButtonSelected(btn9, "O")) {
            setWinner(player2);
        } else {
            drawGame();
        }
    }

    private void drawGame() {
        String textbtn1 = btn1.getText().toString();
        String textbtn2 = btn2.getText().toString();
        String textbtn3 = btn3.getText().toString();
        String textbtn4 = btn4.getText().toString();
        String textbtn5 = btn5.getText().toString();
        String textbtn6 = btn6.getText().toString();
        String textbtn7 = btn7.getText().toString();
        String textbtn8 = btn8.getText().toString();
        String textbtn9 = btn9.getText().toString();

        if (!textbtn1.isEmpty() && !textbtn2.isEmpty() && !textbtn3.isEmpty() &&
                !textbtn4.isEmpty() && !textbtn5.isEmpty() && !textbtn6.isEmpty() &&
                !textbtn7.isEmpty() && !textbtn8.isEmpty() && !textbtn9.isEmpty()) {
            gameOver = true;
            timer.stop();
            enableGameButtons(false);
            msg.showMessageDialog(this, "Draw game!", "Try again?");

            btnRetry.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
            // linjen lånt fra:
            // https://stackoverflow.com/questions/14802354/how-to-reset-a-buttons-background-color-to-default
            btnExit.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
    }

    private boolean isButtonSelected(Button btn, String XorO) {
        if (btn.getText().toString().equals(XorO)) {
            return true;
        }
        return false;
    }

    private void setPlayerTurn(String player) {
        playerTurn = player;
        tvPlayerTurn.setText("player turn: " + player);
    }

    private void enableGameButtons(boolean trueOrFalse) {
        addButtonsToList();
        for (Button button : gameButtonList) {
            button.setEnabled(trueOrFalse);
        }
    }

    private void resetGameButtons() {
        addButtonsToList();
        for (Button btn : gameButtonList) {
            btn.setText("");
        }
    }

    private void addButtonsToList() {
        gameButtonList.clear();
        gameButtonList.add(btn1);
        gameButtonList.add(btn2);
        gameButtonList.add(btn3);
        gameButtonList.add(btn4);
        gameButtonList.add(btn5);
        gameButtonList.add(btn6);
        gameButtonList.add(btn7);
        gameButtonList.add(btn8);
        gameButtonList.add(btn9);
    }


}

