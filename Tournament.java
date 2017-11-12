package edu.ramapo.afouch.longana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Tournament extends AppCompatActivity {

    private int tourneyScore;
    private int roundNumber;
    // private Player human;
    // private Player computer;

    private void setTourneyScore(int val) {
        this.tourneyScore = val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        // grab tourney score from newGame activity
        String temp = getIntent().getStringExtra("tourneyScore");
        setTourneyScore(Integer.parseInt(temp));

        TextView editText = (TextView) findViewById(R.id.view_tourneyScore);
        editText.setText(temp, TextView.BufferType.EDITABLE);
    }

    public void newRound(View view) {
        Intent activity = new Intent(Tournament.this, Round.class);
        activity.putExtra("tourneyScore", tourneyScore);
        startActivity(activity);
    }
}
