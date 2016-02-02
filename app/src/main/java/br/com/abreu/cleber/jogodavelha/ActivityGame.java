package br.com.abreu.cleber.jogodavelha;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;


/**
 * Created by Cleber on 30/12/2015.
 *
 */

public class ActivityGame extends AppCompatActivity {
    private Button btnA[][] = new Button[3][3];
    private TextView txtPlayer1;
    private TextView txtPlayer2;
    private String charPlayer = "X";
    private int textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnA[0][0] = (Button) findViewById(R.id.btnA00);
        btnA[0][1] = (Button) findViewById(R.id.btnA01);
        btnA[0][2] = (Button) findViewById(R.id.btnA02);
        btnA[1][0] = (Button) findViewById(R.id.btnA10);
        btnA[1][1] = (Button) findViewById(R.id.btnA11);
        btnA[1][2] = (Button) findViewById(R.id.btnA12);
        btnA[2][0] = (Button) findViewById(R.id.btnA20);
        btnA[2][1] = (Button) findViewById(R.id.btnA21);
        btnA[2][2] = (Button) findViewById(R.id.btnA22);

        textColor = R.color.player1;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        txtPlayer1 = (TextView) findViewById(R.id.txtPlayer1);
        txtPlayer2 = (TextView) findViewById(R.id.txtPlayer2);

        txtPlayer1.setText(sharedPrefs.getString("pref_play1Name", getString(R.string.player1)));

        if (GameController.jogadorAi) {
            String[] levels = getResources().getStringArray(R.array.pref_level_list);
            int level = Integer.parseInt(sharedPrefs.getString("pref_level", "-1"));
            txtPlayer2.setText(levels[level]);
            PlayerAi.nivel = level;
        } else {
            txtPlayer2.setText(sharedPrefs.getString("pref_play2Name", getString(R.string.player2)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (v.getId() == btnA[i][j].getId()) {
                    if (GameController.realizaJogada(i, j)) {
                        realizaJogada(i, j);
                        Log.v("jogada", "X: " + i + "," + j);
                        seVenceu();
                        return;
                    }
                }
            }
        }
    }

    private void createAlertOption(String title, String message) {
        AlertDialog alertOption = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        novaPartida();
                    }
                })
                .setNegativeButton("Voltar ao menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();
        alertOption.show();
    }

    // Verifica se jogador
    @SuppressLint("SetTextI18n")
    private void seVenceu() {
        int[][] jogada = GameController.jogadaVencedora();

        if (jogada[0][0] > -1) {
            for (int i = 0; i < 3; i++) {
                btnA[jogada[0][i]][jogada[1][i]].setBackgroundResource(R.color.winner);
            }

            GameController.placar[GameController.vezDoJogador - 1]++;
            TextView txtPlayerPoint;
            if (GameController.vezDoJogador == 1) {
                txtPlayerPoint = (TextView) findViewById(R.id.txtPlayer1Point);
            } else {
                txtPlayerPoint = (TextView) findViewById(R.id.txtPlayer2Point);
            }

            txtPlayerPoint.setText(Integer.toString(
                    GameController.placar[GameController.vezDoJogador - 1]));
            createAlertOption("Jogador " + GameController.vezDoJogador + " venceu!", "Nova partida?");

        } else if (GameController.tabuleiroCheio()) {
            createAlertOption("Deu velha!", "Nova partida?");
        } else {
            GameController.vezDoJogador = (GameController.vezDoJogador == 1) ? 2 : 1;
            alternaJogador();
        }

    }

    private void realizaJogada(int i, int j) {
        btnA[i][j].setBackgroundResource(R.color.buttonClicked);
        btnA[i][j].setTextColor(getResources().getColor(textColor));
        btnA[i][j].setText(charPlayer);
    }

    private void alternaJogador() {
        if (GameController.vezDoJogador == 1) {
            charPlayer = "X";
            txtPlayer1.setBackgroundResource(R.color.player1);
            txtPlayer2.setBackgroundResource(R.color.player2Score);
            textColor = R.color.player1;
        } else {
            charPlayer = "O";
            txtPlayer1.setBackgroundResource(R.color.player1Score);
            txtPlayer2.setBackgroundResource(R.color.player2);
            textColor = R.color.player2;

            if (GameController.jogadorAi) {
                PlayerAi.realizaJogada();
                realizaJogada(PlayerAi.getX(), PlayerAi.getY());
                seVenceu();
            }
        }
    }

    public void novaPartida() {
        GameController.novaPartida(GameController.jogadorAi,
                GameController.placar[0],
                GameController.placar[1],
                (GameController.vezDoJogador == 1) ? 2 : 1);

        // limpa o tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btnA[i][j].setText(" ");
                btnA[i][j].setBackgroundResource(R.color.button);
            }
        }
        alternaJogador();
    }
}
