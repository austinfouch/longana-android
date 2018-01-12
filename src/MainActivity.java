 package edu.ramapo.afouch.longana;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**************************************************************************
    Function Name: newGame
    Purpose: 
        To enter the NewGame Activity
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void newGame(View view) {
        Intent activity = new Intent(MainActivity.this, NewGame.class);
        startActivity(activity);
    }
}
