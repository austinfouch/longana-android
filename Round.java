package edu.ramapo.afouch.longana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Round extends AppCompatActivity {

    // takes in player vector
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        // grab tourney score from newGame activity
        int tourneyScore = getIntent().getIntExtra("tourneyScore", 0);
        TextView editText = (TextView) findViewById(R.id.view_tourneyScore);
        editText.setText(Integer.toString(tourneyScore), TextView.BufferType.EDITABLE);
    }
}
