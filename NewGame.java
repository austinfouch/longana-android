package edu.ramapo.afouch.longana;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    public void newTourney(View view) {
        Intent activity = new Intent(NewGame.this, Tournament.class);
        EditText tourneyScore =  (EditText)findViewById(R.id.input_tourneyScore);
        String text = tourneyScore.getText().toString();
        activity.putExtra("tourneyScore", text);
        startActivity(activity);
    }
}
