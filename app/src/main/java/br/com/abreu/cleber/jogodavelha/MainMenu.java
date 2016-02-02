package br.com.abreu.cleber.jogodavelha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSinglePlayer:
                GameController.novaPartida(true, 0, 0, 1);
                startActivity(new Intent(MainMenu.this, ActivityGame.class));
                break;
            case R.id.btnMultiplayer:
                GameController.novaPartida(false, 0, 0, 1);
                startActivity(new Intent(MainMenu.this, ActivityGame.class));
                break;
            case R.id.btnOption:
                startActivity(new Intent(MainMenu.this, ActivitySettings.class));
                break;
            case R.id.btnAbout:
                startActivity(new Intent(MainMenu.this, ActivityAbout.class));
                break;
            case R.id.btnExit:
                finish();
                break;
        }
    }
}
