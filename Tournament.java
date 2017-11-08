package edu.ramapo.afouch.longana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Tournament extends AppCompatActivity {

    // object player 1
    // object player 2
    // integer tournament score
    // integer round number
    // object round

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        // grab tourney score from newGame activity
        String tourneyScore = getIntent().getStringExtra("tourneyScore");
        // this.tourneryScore = tourneyScore;
        // change textfield to show this.tourneyScore
        TextView editText = (TextView) findViewById(R.id.view_tourneyScore);
        editText.setText(tourneyScore, TextView.BufferType.EDITABLE);
    }
}
