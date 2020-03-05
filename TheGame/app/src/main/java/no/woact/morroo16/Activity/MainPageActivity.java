package no.woact.morroo16.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import no.woact.morroo16.Messager;
import no.woact.morroo16.tictactoe.R;



/* By Roosbeh Morandi*/




public class MainPageActivity extends AppCompatActivity {

    private Button btnReady1, btnReady2, btnLetsPlay, btnHigSc;
    private EditText edtP1, edtP2;

    private boolean player1IsReady = false;
    private boolean player2IsReady = false;

    public static final String NAME_P1 = "p1Name";
    public static final String NAME_P2 = "p2Name";

    private Messager msg = new Messager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findView();
        onClick();
    }


    private void findView() {
        btnHigSc = (Button) findViewById(R.id.btnHighScId);
        btnReady1 = (Button) findViewById(R.id.btnOk1P);
        btnReady2 = (Button) findViewById(R.id.btnOk2P);
        btnLetsPlay = (Button) findViewById(R.id.btnPlayId);
        edtP1 = (EditText) findViewById(R.id.edt1P);
        edtP2 = (EditText) findViewById(R.id.edt2P);
    }


    private void onClick() {
        btnReady1.setOnClickListener((View v) -> {

            if (getPlayerName(edtP1).isEmpty()) {
                msg.toastMessage(this, "Namefield cannot be empty");
            } else if (getPlayerName(edtP1).equals("TTTbot")) {
                msg.toastMessage(this, "'TTTbot' is too Sexy name! Choose something else");
            } else if (getPlayerName(edtP1).length() > 10) {
                msg.toastMessage(this, "Too long name (max 10 letters)");
            } else if (getPlayerName(edtP1).equals(edtP2.getText().toString())) {
                msg.toastMessage(this, "Names cannot be same as player2");
            } else if (!getPlayerName(edtP1).isEmpty()) {
                player1IsReady = true;
                disableEdtAndChangeBtnColor(edtP1, btnReady1, btnLetsPlay);
            }

        });
        btnReady2.setOnClickListener((View v) -> {

            if (getPlayerName(edtP2).isEmpty()) {
                msg.toastMessage(this, "Namefield cannot be empty");
            } else if (getPlayerName(edtP2).equals("TTTbot")) {
                msg.toastMessage(this, "'TTTbot' is too Sexy name! Choose something else");
            } else if (getPlayerName(edtP2).length() > 10) {
                msg.toastMessage(this, "Too long  name (max 10 letters)");
            } else if (getPlayerName(edtP2).equals(edtP1.getText().toString())) {
                msg.toastMessage(this, "Names cannot be same as player1");
            } else if (!getPlayerName(edtP2).isEmpty()) {
                disableEdtAndChangeBtnColor(edtP2, btnReady2, btnLetsPlay);
                player2IsReady = true;
              }
        });
        btnLetsPlay.setOnClickListener((View v) -> {

            if (!player1IsReady && !player2IsReady) {
                msg.toastMessage(this, "One of the nameFields must be added..");
            } else if (player1IsReady && player2IsReady) {
                startGame();

            } else if (player1IsReady && !player2IsReady) {
                if (!edtP2.getText().toString().isEmpty()) {
                    msg.toastMessage(this, "P2 must press ready..");
                } else {
                    startGame();
                }
            } else if (!player1IsReady && player2IsReady) {
                if (!edtP1.getText().toString().isEmpty()) {
                    msg.toastMessage(this, "P1 must press ready..");
                } else {
                    startGame();
                }
            }
        });
        btnHigSc.setOnClickListener((View v) -> {
            goToHighScorePage();
        });
    }

    public void disableEdtAndChangeBtnColor(EditText edt, Button btnReady, Button btnPlay) {
        edt.setEnabled(false);
        btnReady.setBackgroundColor(Color.RED);
        btnPlay.setBackgroundColor(Color.RED);
    }


    public void goToHighScorePage() {
        Intent intent = new Intent(MainPageActivity.this, HighscoresActivity.class);
        startActivity(intent);
    }


    private void startGame() {
        Intent intent = new Intent(MainPageActivity.this, InGameActivity.class);
        intent.putExtra(NAME_P1, getPlayerName(edtP1));
        intent.putExtra(NAME_P2, getPlayerName(edtP2));
        startActivity(intent);
    }

    private String getPlayerName(EditText edtPlayer) {
        return edtPlayer.getText().toString();
    }


}

