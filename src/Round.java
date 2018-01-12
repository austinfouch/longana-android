package edu.ramapo.afouch.longana;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Round extends AppCompatActivity {
    // constants
    private final int MAX_PIPS = 6;
    private final int MIN_PIPS = 0;
    private final int MAX_HAND_SIZE = 8;
    // member variables
    private List<Player> players;
    private HandView computerHandView;
    private HandView humanHandView;
    private BoneyardView boneyardView;
    private BoardView boardView;
    private int currPlayer;
    private Boneyard boneyard;
    private Board board;
    private Turn lastTurn;
    private List<Integer> playerScores;
    public boolean completedTurn;
    // default cstor
    public Round() {
        this.players = new ArrayList<>();
        this.currPlayer = 0;
        this.boneyard = new Boneyard();
        this.board = new Board();
        this.playerScores = new ArrayList<>();
        this.computerHandView = new HandView();
        this.humanHandView = new HandView();
        this.boneyardView = new BoneyardView();
        this.boardView = new BoardView();
        this.lastTurn = new Turn();
    }
    // copy cstor
    public Round(List<Player> players, int currPlayer, Boneyard boneyard, Board board, List<Integer> playerScores) {
        this.players = players;
        this.currPlayer = currPlayer;
        this.boneyard = boneyard;
        this.board = board;
        this.playerScores = playerScores;
    }
    /**************************************************************************
    Begin Getters/Setters
    **************************************************************************/   
    public int getCurrPlayer() {
        return currPlayer;
    }
    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
    }
    public List<Integer> getPlayerScores() {
        return playerScores;
    }
    public void setPlayerScores(List<Integer> playerScores) {
        this.playerScores = playerScores;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public Boneyard getBoneyard() {
        return boneyard;
    }
    public void setBoneyard(Boneyard boneyard) {
        this.boneyard = boneyard;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    /**************************************************************************
    End Getters/Setters
    **************************************************************************/   
    /**************************************************************************
    Function Name: createPlayers
    Purpose: 
        To initialize a human and computer player and their scores
    Paramaters: None
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void createPlayers() {
        Computer computer = new Computer();
        getPlayerScores().add(0);
        getPlayers().add(computer);

        Human human  = new Human();
        getPlayerScores().add(0);
        getPlayers().add(human);
    }
    /**************************************************************************
    Function Name: distributeTiles
    Purpose: 
        To create a tile List containing all of the tiles in the round,
        shuffle the list, and distribute the list to each player's hand and the
        boneyard
    Paramaters: None
    Local Variables:
        tileList, the tile List where the tiles are initially store to be
        shuffled and distributed
    Algorithm:
        1) Using the constants in round.h, create all of the tiles needed for 
        the round and push them into a List of tiles
        2) Using Collections.shuffle(), shuffle the tile list
        3) Distribute eight tiles from the shuffled tile list to each player,
        then pop the rest of the tiles in the tile list into the boneyard 
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void distributeTiles() {
        List<Tile> tileList = new ArrayList<Tile>();
        int leftPips, rightPips;
        for(leftPips = MIN_PIPS; leftPips <= MAX_PIPS; leftPips++) {
            for(rightPips = leftPips; rightPips <= MAX_PIPS; rightPips++) {
                Tile temp = new Tile();
                temp.setLeftPip(leftPips);
                temp.setRightPip(rightPips);
                tileList.add(temp);
            }
        }

        long seed = System.nanoTime();
        Collections.shuffle(tileList, new Random(seed));

        // distribute MAX_HAND_SIZE tiles to each player
        for(int i = 0; i < getPlayers().size(); i++) {
            for(int j = 0; j < MAX_HAND_SIZE; j++) {
                Tile temp = new Tile();
                temp = tileList.get(tileList.size() - 1);
                getPlayers().get(i).drawTile(temp);
                tileList.remove(tileList.size() - 1);
            }
        }

        // distribute rest to boneyard
        getBoneyard().addTiles(tileList);
    }
    /**************************************************************************
    Function Name: findEngine
    Purpose: 
        To find the engine for the round and set the current player if they
        have the engine
    Paramaters:
        roundNum, an interger containing the current round number
    Local Variables:
        e, a temporary tile object representing the engine for the round
    Algorithm:
        1) Loop over each player's hand looking for the engine
        2) If it is found in a player's hand, set the player that has the 
        engine as the current player
        3) If the engine is not in a player's hand, the boneyard is searched
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void findEngine(int roundNum) {
        int pips;
        if(roundNum <= (MAX_PIPS + 1)) { pips = (MAX_PIPS + 1) - roundNum; }
        else { pips = (MAX_PIPS + 1) - (roundNum % (MAX_PIPS + 1)); }

        Tile e = new Tile(pips, pips);
        for(int i = 0; i < getPlayers().size(); i++) {
            for(Tile t : getPlayers().get(i).getHand().getTiles())
                if(t.isEqual(e)) {
                    // player i has the engine
                    setCurrPlayer(i);
                    TextView lab_currPlayer = (TextView) findViewById(R.id.view_currPlayer);
                    if(i == 0) { lab_currPlayer.setText("Computer"); }
                    if(i == 1) { lab_currPlayer.setText("Human"); }
                    return;
                }
        }

        findEngineInBoneyard(roundNum);
    }
    /**************************************************************************
    Function Name: findEngineInBoneyard
    Purpose: 
        To force each player to draw from the boneyard until a player draws
        the engine. The player that draws the engine is set as the current
        player
    Paramaters:
        roundNum, an interger containing the current round number
    Local Variables:
        e, a temporary tile object representing the engine for the round
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void findEngineInBoneyard(int roundNum) {
        int pips;
        if(roundNum <= (MAX_PIPS + 1)) { pips = (MAX_PIPS + 1) - roundNum; }
        else { pips = (MAX_PIPS + 1) - (roundNum % (MAX_PIPS + 1)); }

        Tile e = new Tile(pips, pips);

        TextView lab_currPlayer = (TextView) findViewById(R.id.view_currPlayer);
        // engine in boneyard
        Tile temp0 = new Tile();
        Tile temp1 = new Tile();
        for(;;) {
            // both draw from the boneyard
            temp0 = getBoneyard().pop();
            getPlayers().get(0).drawTile(temp0);
            if(!getBoneyard().empty()) {
                temp1 = getBoneyard().pop();
                getPlayers().get(1).drawTile(temp1);
            }
            if (temp0.isEqual(e)) {
                // player 0 (computer) has the engine
                setCurrPlayer(0);
                lab_currPlayer.setText("Computer");
                return;
            } else if (temp1.isEqual(e)) {
                // player 1 (human) has the engines
                setCurrPlayer(1);
                lab_currPlayer.setText("Human");
                return;
            }
        }
    }
    /**************************************************************************
    Function Name: setupRound
    Purpose: 
        Caller function
    Paramaters:
        roundNum, an interger containing the current round number
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void setupRound(int roundNum) {
        createPlayers();
        distributeTiles();
        findEngine(roundNum);
        enableHuman(this);
    }
    /**************************************************************************
    Function Name: enableHuman
    Purpose: 
        To enable the human's hand, the board, and create onClick event handlers
        and listeners for each ImageButton in the human's hand as well as the
        buttons on the board.
    Paramaters:
        roundActivity, context where the views will be created in
    Local Variables:
        There is a local variable for every facet of the Round activity layout
    Algorithm:
        1) Set an onClickListener for the pass button, which when clicked will
        check to see if the human is allowed to pass.
        2) Set an onClickListener for the help button, which when clicked will
        display, via AlertDialog, the best possible play the human can make.
        3) For each tile in the human's hand model, create an ImageButton of
        the tile and add an onClickListener to it.
        4) When a tile ImageButton is clicked, onClickListeners for the board's
        buttons are created (L, engine, R). Clicking these buttons after
        clicking an ImageButton in the human's hand will attempt to play the
        tile clicked on the part of the board clicked.
        5) If the attempted turn is valid, the current player is toggled
        6) Otherwise, the user is notified of the illegal turn via AlertDialog 
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void enableHuman(final Context roundActivity) {
        // initialize component variables
        LinearLayout computerHandLayout = (LinearLayout) findViewById(R.id.view_computerHand);
        final LinearLayout humanHandLayout = (LinearLayout) findViewById(R.id.view_humanHand);
        final LinearLayout boneyardLayout = (LinearLayout) findViewById(R.id.view_boneyard);
        final LinearLayout leftSideLayout = (LinearLayout) findViewById(R.id.view_leftSide);
        final LinearLayout rightSideLayout = (LinearLayout) findViewById(R.id.view_rightSide);
        final ImageButton engineBtn = (ImageButton) findViewById(R.id.btn_engine);
        final Button leftSideBtn = (Button) findViewById(R.id.btn_leftSide);
        final Button rightSideBtn = (Button) findViewById(R.id.btn_rightSide);
        TextView currPlayerView = (TextView) findViewById(R.id.view_currPlayer);
        final TextView lastTurnView = (TextView) findViewById(R.id.view_lastTurn);

        Button passBtn = (Button) findViewById(R.id.btn_pass);
        Button helpBtn = (Button) findViewById(R.id.btn_help);

        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currPlayer == 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
                    alertDialog.setTitle("Pass Attempt");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    boolean canPass = getPlayers().get(1).pass(board, lastTurn);
                    if(canPass) {
                        alertDialog.setMessage("Pass successful");
                            if(!boneyard.empty()) {
                                Tile temp = new Tile();
                                temp = boneyard.getTiles().get(boneyard.getTiles().size() - 1);
                                getPlayers().get(1).getHand().addTile(temp);
                                boneyard.getTiles().remove(temp);
                                humanHandView.drawHumanInitial(humanHandLayout, roundActivity,
                                        getPlayers().get(1).getHand());
                                boneyardView.draw(boneyardLayout, roundActivity, boneyard);
                            }
                            lastTurn.sidePlayed = "";
                            lastTurn.tilePlayed = new Tile(-1, -1);
                            lastTurn.wasPassed = true;
                            setCurrPlayer(0);
                    } else {
                        alertDialog.setMessage("Play available, you cannot pass. Try the help button");
                    }
                    alertDialog.show();
                }
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currPlayer == 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
                    alertDialog.setTitle("Pass Attempt");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    Turn turn = getPlayers().get(1).help(board, lastTurn);
                    if(board.getEngine().getLeftPip() == -1) {
                        int roundNum = getIntent().getIntExtra("roundNum", 0);
                        int pips;
                        if(roundNum <= (MAX_PIPS + 1)) { pips = (MAX_PIPS + 1) - roundNum; }
                        else { pips = (MAX_PIPS + 1) - (roundNum % (MAX_PIPS + 1)); }
                        alertDialog.setMessage("Best play is " + pips + "-" + pips + " on E");
                    }
                    else if(turn.tilePlayed.getRightPip() == -1) {
                        alertDialog.setMessage("Pass your turn");
                    } else {
                        alertDialog.setMessage("Best play is " + turn.tilePlayed.getLeftPip() +
                        "-" + turn.getTilePlayed().getRightPip() + " on " + turn.getSidePlayed());
                    }
                    alertDialog.show();
                }
            }
        });


        for (int i = 0; i < humanHandLayout.getChildCount(); i++) {
            View v = humanHandLayout.getChildAt(i);
            final int currTile = i;
            if (v instanceof ImageButton) {
                v.setEnabled(true);
                // onclick for buttons in hand
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Turn attempt = new Turn();
                        attempt.setTilePlayed(getPlayers().get(1).getHand().getTiles().get(currTile));
                        //onclick for leftSide btn
                        leftSideBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                attempt.setSidePlayed("L");
                                if(getPlayers().get(1).legalTurn(attempt, board, lastTurn)) {
                                    getPlayers().get(1).getHand().removeTile(attempt.getTilePlayed());
                                    if(board.getLeftSide().isEmpty()) {
                                        if(attempt.tilePlayed.getRightPip() !=
                                                board.getEngine().getLeftPip()) {
                                            attempt.tilePlayed.swapPips();
                                        }
                                    }
                                    else if(attempt.tilePlayed.getRightPip() != board.getLeftSide().get(board.getLeftSide().size() - 1).getLeftPip())
                                    {
                                        attempt.tilePlayed.swapPips();
                                    }
                                    board.getLeftSide().add(attempt.getTilePlayed());
                                    boardView.drawEngine(engineBtn, board);
                                    boardView.drawLeft(leftSideLayout, roundActivity, board);
                                    boardView.drawRight(rightSideLayout, roundActivity, board);
                                    humanHandView.drawHumanInitial(humanHandLayout, roundActivity,
                                            getPlayers().get(1).getHand());
                                    lastTurn = attempt;
                                    setCurrPlayer(0);
                                } else {
                                    //On invalid play
                                    AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
                                    alertDialog.setTitle("Invalid Turn");
                                    alertDialog.setMessage("You cannot play "  +
                                            attempt.tilePlayed.getLeftPip() + "-" +
                                            attempt.tilePlayed.getRightPip() + " tile on L");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }

                            }
                        });
                        rightSideBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                attempt.setSidePlayed("R");
                                if(getPlayers().get(1).legalTurn(attempt, board, lastTurn)) {
                                    getPlayers().get(1).getHand().removeTile(attempt.getTilePlayed());
                                    if(board.getRightSide().isEmpty()) {
                                        if(attempt.tilePlayed.getLeftPip() !=
                                                board.getEngine().getRightPip()) {
                                            attempt.tilePlayed.swapPips();
                                        }
                                    }
                                    else if(attempt.tilePlayed.getLeftPip() != board.getRightSide().get(board.getRightSide().size() - 1).getRightPip())
                                    {
                                        attempt.tilePlayed.swapPips();
                                    }
                                    board.getRightSide().add(attempt.getTilePlayed());
                                    boardView.drawEngine(engineBtn, board);
                                    boardView.drawLeft(leftSideLayout, roundActivity, board);
                                    boardView.drawRight(rightSideLayout, roundActivity, board);
                                    humanHandView.drawHumanInitial(humanHandLayout, roundActivity,
                                            getPlayers().get(1).getHand());
                                    lastTurn = attempt;
                                    setCurrPlayer(0);
                                } else {
                                    //On invalid play
                                    AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
                                    alertDialog.setTitle("Invalid Turn");
                                    alertDialog.setMessage("You cannot play "  +
                                            attempt.tilePlayed.getLeftPip() + "-" +
                                            attempt.tilePlayed.getRightPip() + " tile on R");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }

                            }
                        });
                        // onclick for engine
                        engineBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                attempt.setSidePlayed("E");
                                if(getPlayers().get(1).legalTurn(attempt, board, lastTurn)) {
                                    getPlayers().get(1).getHand().removeTile(attempt.getTilePlayed());
                                    board.setEngine(attempt.getTilePlayed());
                                    boardView.drawEngine(engineBtn, board);
                                    boardView.drawLeft(leftSideLayout, roundActivity, board);
                                    boardView.drawRight(rightSideLayout, roundActivity, board);
                                    humanHandView.drawHumanInitial(humanHandLayout, roundActivity,
                                            getPlayers().get(1).getHand());
                                    lastTurn = attempt;
                                    setCurrPlayer(0);
                                } else {
                                    //On invalid play
                                    AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
                                    alertDialog.setTitle("Invalid Turn");
                                    alertDialog.setMessage("You cannot play "  +
                                            attempt.tilePlayed.getLeftPip() + "-" +
                                            attempt.tilePlayed.getRightPip() + " tile on E");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            }
                        });
                    }
                });
            }
        }
    }
    /**************************************************************************
    Function Name: disableHuman
    Purpose: 
        Disables each ImageButton in the human's hand and disables the board
        buttons
    Paramaters: None
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void disableHuman() {
        // initialize component variables
        LinearLayout computerHandLayout = (LinearLayout) findViewById(R.id.view_computerHand);
        final LinearLayout humanHandLayout = (LinearLayout) findViewById(R.id.view_humanHand);
        LinearLayout boneyardLayout = (LinearLayout) findViewById(R.id.view_boneyard);
        final LinearLayout leftSideLayout = (LinearLayout) findViewById(R.id.view_leftSide);
        final LinearLayout rightSideLayout = (LinearLayout) findViewById(R.id.view_rightSide);
        final ImageButton engineBtn = (ImageButton) findViewById(R.id.btn_engine);
        final Button leftSideBtn = (Button) findViewById(R.id.btn_leftSide);
        final Button rightSideBtn = (Button) findViewById(R.id.btn_rightSide);
        TextView currPlayerView = (TextView) findViewById(R.id.view_currPlayer);
        TextView lastTurnView = (TextView) findViewById(R.id.view_lastTurn);
        for (int i = 0; i < humanHandLayout.getChildCount(); i++) {
            View v = humanHandLayout.getChildAt(i);
            v.setEnabled(false);
        }
        leftSideBtn.setEnabled(false);
        rightSideBtn.setEnabled(false);
        engineBtn.setEnabled(false);
    }
    /**************************************************************************
    Function Name: runRound
    Purpose: 
        To run a round until a player wins the round or both players cannot
        play a tile
    Paramaters: None
    Return Value: None
    Local Variables:
        There is a local variable for every facet of the Round activity layout
    Algorithm:
        1) If the current player is the computer, simulate the comptuer's turn
        and display an AlertDialog of the turn.
        2) If the current player is the human, call enableHuman which enables
        the human's hand and the board
        3) If a hand is empty or a double pass occurs after either player's
        turn, the winner is determined and the round ends
    Assistance Received: None
    **************************************************************************/
    public void runRound(View view ) {

        // initialize component variables
        LinearLayout computerHandLayout = (LinearLayout) findViewById(R.id.view_computerHand);
        LinearLayout humanHandLayout = (LinearLayout) findViewById(R.id.view_humanHand);
        LinearLayout boneyardLayout = (LinearLayout) findViewById(R.id.view_boneyard);
        final LinearLayout leftSideLayout = (LinearLayout) findViewById(R.id.view_leftSide);
        LinearLayout rightSideLayout = (LinearLayout) findViewById(R.id.view_rightSide);
        ImageButton engineBtn = (ImageButton) findViewById(R.id.btn_engine);
        final Button leftSideBtn = (Button) findViewById(R.id.btn_leftSide);
        final Button rightSideBtn = (Button) findViewById(R.id.btn_rightSide);
        TextView currPlayerView = (TextView) findViewById(R.id.view_currPlayer);
        TextView lastTurnView = (TextView) findViewById(R.id.view_lastTurn);
        Button startRound = (Button) findViewById(R.id.btn_start);

        //TextView computerLogicView = (TextView) findViewById(R.id.view_computerLogic);
        String currPlayerText = "";
        String lastTurnText = "";
        String computerLogicText = "";
        drawInitialLayout();


        int roundNum = getIntent().getIntExtra("roundNum", 0);
        int pips;
        if(roundNum <= (MAX_PIPS + 1)) { pips = (MAX_PIPS + 1) - roundNum; }
        else { pips = (MAX_PIPS + 1) - (roundNum % (MAX_PIPS + 1)); }

        Tile tempEng = new Tile(pips, pips);
        boolean singlePass = false;
        boolean doublePass = false;
        boolean triplePass = false;

        if(getCurrPlayer() == 0) { currPlayerText = "computer"; }
        else { currPlayerText = "human"; }

        currPlayerView.setText(currPlayerText);
        lastTurnView.setText(lastTurnText);
        //computerLogicView.setText(computerLogicText);

        Turn lastLastTurn = new Turn();
        if(lastTurn.wasPassed()) {
            if(lastLastTurn.wasPassed && !boneyard.empty()) {
                calculateWinner();
            } else {
                lastLastTurn.wasPassed = true;
            }
        } else {
            lastLastTurn.wasPassed = false;
        }

        if(handIsEmpty() || triplePass) {
            calculateWinner();
        }
        switch (getCurrPlayer()) {
            case 0:
                AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
                alertDialog.setTitle("Computer Turn");

                lastTurn = getPlayers().get(0).play(board, boneyard, lastTurn, tempEng);
                if(lastTurn.tilePlayed.isEqual(new Tile(-1, -1))) {
                    alertDialog.setMessage("Computer played passed.");
                } else {
                    alertDialog.setMessage("Computer played "  +
                            lastTurn.tilePlayed.getLeftPip() + "-" +
                            lastTurn.tilePlayed.getRightPip() + " tile on " +
                            lastTurn.sidePlayed + ". The strategy is to first play non-double tiles" +
                            " on either side (if playable). If not non-double is legal, then attempt" +
                            " to play a double on either side. Tiles are prioritized by pip sum, the " +
                            " higher the pip sum the higher the priority to play that tile.");
                }
                //On invalid play
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                computerHandView.drawComputer(computerHandLayout, this, getPlayers().get(0).getHand());
                boneyardView.draw(boneyardLayout, this, boneyard);
                boardView.drawLeft(leftSideLayout, this, board);
                boardView.drawRight(rightSideLayout, this, board);
                boardView.drawEngine(engineBtn, board);
                setCurrPlayer(1);
                break;
            case 1:
                leftSideBtn.setEnabled(true);
                rightSideBtn.setEnabled(true);
                engineBtn.setEnabled(true);
                enableHuman(this);
                //humanHandView.drawHumanInitial(humanHandLayout, this, getPlayers().get(1).getHand());
                boneyardView.draw(boneyardLayout, this, boneyard);
                boardView.drawLeft(leftSideLayout, this, board);
                boardView.drawRight(rightSideLayout, this, board);
                boardView.drawEngine(engineBtn, board);
                // TODO somehow keep thread running until human makes a move
            default:
                break;
        }

        boneyardView.draw(boneyardLayout, this, getBoneyard());
        boardView.drawEngine(engineBtn, getBoard());
        boardView.drawLeft(leftSideLayout, this, getBoard());
        boardView.drawRight(rightSideLayout, this, getBoard());
    }
    /**************************************************************************
    Function Name: calculateWinner
    Purpose: 
        Calculate the winner of the round and diplay
    Paramaters: None
    Return Value: None
    Local Variables:
        otherPlayer, the losing player
    Algorithm:
        1) Set the current player to the player who has the least amount of
        tiles in their hand
        2) Set the otherPlayer to the loser and sum up all of the pips in the
        tiles in the otherPlayer's hand and add the result to the winner's
        score
        3) Create an AlertDialog displaying the winner and their score for
        the round 
    Assistance Received: None
    **************************************************************************/
    public void calculateWinner() {

        int winnersPts = 0;
        if(getPlayers().get(1).getHand().getTiles().isEmpty()) { setCurrPlayer(1); }
        else if(getPlayers().get(0).getHand().getTiles().isEmpty()) { setCurrPlayer(0); }
        if(getPlayers().get(0).getHand().getTiles().size() < getPlayers().get(1).getHand().getTiles().size()) {
            setCurrPlayer(0);
        }
        if(getPlayers().get(1).getHand().getTiles().size() < getPlayers().get(1).getHand().getTiles().size()) {
            setCurrPlayer(1);
        }

        int otherPlayer;
        String winner;
        if(currPlayer == 0) {
            otherPlayer = 1;
            winner = "Computer";
        }
        else {
            otherPlayer = 0;
            winner = "Human";
        }

        for(Tile t : getPlayers().get(otherPlayer).getHand().getTiles()) {
            winnersPts += t.getLeftPip() + t.getRightPip();
        }

        getPlayerScores().add(currPlayer, winnersPts);
        AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
        alertDialog.setTitle("Winner!");
        alertDialog.setMessage(winner + " has won the round with a score of " + winnersPts);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exitRound();
                    }
                });
        alertDialog.show();
    }
    /**************************************************************************
    Function Name: runRound
    Purpose: 
        Returns true if either player's hand is empty, false otherwise
    Paramaters: None
    Return Value:
        Boolean
    Assistance Received: None
    **************************************************************************/
    public boolean handIsEmpty() {
        return (getPlayers().get(0).getHand().empty() || getPlayers().get(1).getHand().empty());
    }
    /**************************************************************************
    Function Name: drawInitialLayout
    Purpose: 
        Programmatically draw the layout for the round
    Paramaters: None
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void drawInitialLayout() {

        // initialize component variables
        LinearLayout computerHandLayout = (LinearLayout) findViewById(R.id.view_computerHand);
        LinearLayout humanHandLayout = (LinearLayout) findViewById(R.id.view_humanHand);
        LinearLayout boneyardLayout = (LinearLayout) findViewById(R.id.view_boneyard);
        LinearLayout leftSideLayout = (LinearLayout) findViewById(R.id.view_leftSide);
        LinearLayout rightSideLayout = (LinearLayout) findViewById(R.id.view_rightSide);
        ImageButton engineBtn = (ImageButton) findViewById(R.id.btn_engine);
        Button leftSideBtn = (Button) findViewById(R.id.btn_leftSide);
        Button rightSideBtn = (Button) findViewById(R.id.btn_rightSide);

        computerHandLayout.removeAllViews();
        humanHandLayout.removeAllViews();
        computerHandLayout.removeAllViews();
        leftSideLayout.removeAllViews();
        rightSideLayout.removeAllViews();

        computerHandView.drawComputer(computerHandLayout, this, getPlayers().get(0).getHand());
        humanHandView.drawHumanInitial(humanHandLayout, this, getPlayers().get(1).getHand());
        boneyardView.draw(boneyardLayout, this, getBoneyard());
        boardView.drawEngine(engineBtn, getBoard());
        boardView.drawLeft(leftSideLayout, this, getBoard());
        boardView.drawRight(rightSideLayout, this, getBoard());
    }
    /**************************************************************************
    Function Name: exitround
    Purpose: 
        Creates Intent to the Tournmanet class and attaches Extras for the
        tourneyScore, roundNum, comptuerScore, and humanScore
    Paramaters: None
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void exitRound() {
        Intent activity = new Intent(Round.this, Tournament.class);

        // get values from Intent
        int tourneyScore = getIntent().getIntExtra("tourneyScore", 0);
        int roundNum = getIntent().getIntExtra("roundNum", 0) + 1;
        int computerScore = getIntent().getIntExtra("computerScore", 0);
        int humanScore = getIntent().getIntExtra("humanScore", 0);

        activity.putExtra("tourneyScore", Integer.toString(tourneyScore));
        activity.putExtra("roundNum", Integer.toString(roundNum));
        activity.putExtra("computerScore", Integer.toString(computerScore + playerScores.get(0)));
        activity.putExtra("humanScore", Integer.toString(humanScore + playerScores.get(1)));
        startActivity(activity);
        finish();
    }
    /**************************************************************************
    Function Name: showHelp
    Purpose: 
        Calls help on the human player's hand and displays the resulting turn
        in an AlertDialog
    Paramaters: None
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void showHelp() {
        Turn turn = new Turn();
        turn = getPlayers().get(1).help(board, lastTurn);
        AlertDialog alertDialog = new AlertDialog.Builder(Round.this).create();
        alertDialog.setTitle("Help");
        alertDialog.setMessage("Best play is "  +
                turn.tilePlayed.getLeftPip() + "-" +
                turn.tilePlayed.getRightPip() + " tile on " +
                turn.getSidePlayed());

        if(turn.tilePlayed.getRightPip() == -1) {
            alertDialog.setMessage("Best play is to pass.");
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        int roundNum = getIntent().getIntExtra("roundNum", 0);
        setupRound(roundNum);
        drawInitialLayout();
    }
}
