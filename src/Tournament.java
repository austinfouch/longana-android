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

import java.util.List;
import java.util.Map;

public class Tournament extends AppCompatActivity {
    // member variables
    private int tourneyScore;
    private int roundNum;
    private int humanScore;
    private int computerScore;
    // default cstor
    public Tournament() {
        this.tourneyScore = 0;
        this.roundNum = 0;
        this.humanScore = 0;
        this.computerScore = 0;
    }
    // copy cstor
    public Tournament(int tourneyScore, int roundNum, int humanScore, int computerScore) {
        this.tourneyScore = tourneyScore;
        this.roundNum = roundNum;
        this.humanScore = humanScore;
        this.computerScore = computerScore;
    }
    /**************************************************************************
    Function Name: getHumanScore
    Purpose: 
        Return the value of the member variable humanScore
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getHumanScore() {
        return humanScore;
    }
   /**************************************************************************
    Function Name: setHumanScore
    Purpose: 
        To set the value of the member variable humanScore
    Paramaters:
        humanScore, integer to set member variable humanScore to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setHumanScore(int humanScore) {
        this.humanScore = humanScore;
    }
    /**************************************************************************
    Function Name: getComputerScore
    Purpose: 
        Return the value of the member variable ComptuerScore
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getComputerScore() {
        return computerScore;
    }
   /**************************************************************************
    Function Name: setComputerScore
    Purpose: 
        To set the value of the member variable computerScore
    Paramaters:
        computerScore, integer to set member variable computerScore to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }
    /**************************************************************************
    Function Name: getTourneyScore
    Purpose: 
        Return the value of the member variable tourneyScore
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getTourneyScore() {
        return tourneyScore;
    }
   /**************************************************************************
    Function Name: setTourneyScore
    Purpose: 
        To set the value of the member variable tourneyScore
    Paramaters:
        tourney, integer to set member variable tourneyScore to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setTourneyScore(int tourneyScore) {
        this.tourneyScore = tourneyScore;
    }
    /**************************************************************************
    Function Name: getRoundNum
    Purpose: 
        Return the value of the member variable roundNum
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getRoundNum() {
        return roundNum;
    }
   /**************************************************************************
    Function Name: setRoundNum
    Purpose: 
        To set the value of the member variable roundNum
    Paramaters:
        roundNum, integer to set member variable roundNum to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }
   /**************************************************************************
    Function Name: updateTourney
    Purpose: 
        To gather Extras passed by Intent to this activity and set the TextView
        fields appropriately. When returning from the Round activity to this
        activity, the scores of each player is checked and a winner is
        announced via AlertDialog if there is one.
    Paramaters: None
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void updateTourney() {
        String temp;
        TextView editText;
        // grab tourney score from newGame activity
        temp = getIntent().getStringExtra("tourneyScore");
        setTourneyScore(Integer.parseInt(temp));
        editText = (TextView) findViewById(R.id.view_tourneyScore);
        editText.setText(temp, TextView.BufferType.EDITABLE);

        temp = getIntent().getStringExtra("computerScore");
        setComputerScore(Integer.parseInt(temp));
        editText = (TextView) findViewById(R.id.view_computerScore);
        editText.setText(temp, TextView.BufferType.EDITABLE);

        temp = getIntent().getStringExtra("humanScore");
        setHumanScore(Integer.parseInt(temp));
        editText = (TextView) findViewById(R.id.view_humanScore);
        editText.setText(temp, TextView.BufferType.EDITABLE);

        temp = getIntent().getStringExtra("roundNum");
        setRoundNum(Integer.parseInt(temp));
        editText = (TextView) findViewById(R.id.view_roundNum);
        editText.setText(temp, TextView.BufferType.EDITABLE);

        AlertDialog alertDialog = new AlertDialog.Builder(Tournament.this).create();
        alertDialog.setTitle("Winner!");
        if(tourneyScore <= humanScore) {
            alertDialog.setMessage("Human has won the tournament with a score of " + humanScore);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        if(tourneyScore <= computerScore) {
            alertDialog.setMessage("Computer has won the tournament with a score of " + computerScore);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        updateTourney();
    }
    /**************************************************************************
    Function Name: newRound
    Purpose: 
        Pass member data to a Round activity
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void newRound(View view) {
        Intent activity = new Intent(Tournament.this, Round.class);
        activity.putExtra("tourneyScore", getTourneyScore());
        activity.putExtra("roundNum", getRoundNum());
        activity.putExtra("computerScore", getComputerScore());
        activity.putExtra("humanScore", getHumanScore());
        startActivity(activity);
        finish();
    }
}
