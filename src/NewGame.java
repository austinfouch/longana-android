package edu.ramapo.afouch.longana;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }
    /**************************************************************************
    Function Name: newTourney
    Purpose: 
        To gather input data from the NewGame activity, create an Intent to a
        Tournament activity, and pass the inputted tournament score. If the
        value entered is not a valid integer, an AlertDialog informs the user
        so.
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void newTourney(View view) {
        Intent activity = new Intent(NewGame.this, Tournament.class);
        EditText tourneyScoreView =  (EditText)findViewById(R.id.input_tourneyScore);
        String tourneyScore = tourneyScoreView.getText().toString();
        try {
            int num = Integer.parseInt(tourneyScore);
            activity.putExtra("tourneyScore", tourneyScore);
            activity.putExtra("roundNum", "1");
            activity.putExtra("computerScore", "0");
            activity.putExtra("humanScore", "0");
            startActivity(activity);
        } catch (NumberFormatException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(NewGame.this).create();
            alertDialog.setTitle("Invalid Score");
            alertDialog.setMessage("Tournament Score needs to be a valid integer");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
