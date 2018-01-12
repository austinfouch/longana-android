package edu.ramapo.afouch.longana;

public class Computer extends Player {
    // default cstor -- calls Player()
    public Computer() {
        super();
    }
    // copy cstor -- calls Player(int, Hand)
    public Computer(int score, Hand hand) {
        super(score, hand);
    }
    /**************************************************************************
    Function Name: play
    Purpose: 
        Conducts the computer player's turn based off of the board param,
        lastTurn param, and Hand member variable
    Paramaters: 
        board, board model that the computer will use to determine the best 
            turn with
        boneyard, boneyard model that the computer will draw a tile from if
            the turn is being passed
        lastTurn, the last turn in the round, used to check whether or not
            the computer can play single tiles on both sides of the board
        tempEng, the engine for the round, used to search the computer's
            hand for the engine in the event that the computer has the
            engine
    Return Value:
        Turn object containing the best tile the computer can play, the side
        it is played, and whether or not the turn is being passed
    Local Variables:
        possiblePlays, an integer representing the number of possible, legal plays
        the user can make
        bestSum, an integer representing the highest sum of pips for each tile in
        the player's hand
        bestSide, a string representing the side of the recommended play
        bestTile, an integer representing an index of the player's hand
        representing the recommended tile to be played
    Algorithm:
        1) Create an empty tile (-1,-1) and compare it against the engine of the
        board. If they are equal, the computer must have the engine.
            1.b) Iterate over the computer's hand and find the index representing
            the engine. Set bestTile equal to this index, bestSide equal to 
            E(for engine). Increment possiblePlays.
        2) If the engine is not empty, iterate over the computer's hand, summing
        up the pips of each tile. The tile with the highest pip sum has the 
        highest priority in the computer's attempt at a play.
        3) Check to see if the highest priority tile is a legal play on the board.
            3.a) If it is, play that tile and the side of the board it can be 
            played. If the tile can be played on either side, play it on their 
            opponent's side. Set the bestSide to the side being played, and set 
            bestTile to the tile's index in the computer's hand. Increment
            possiblePlays
            3.b) If the highest priority tile is not a legal play, then look for the
            next highest priority tile and repeat the process.
        4) If possiblePlays is greater than 0, then complete the play according
        to bestTile and bestSide and return a turn object representing this play
        5) If possiblePlays is 0, then return a turn object representing the
        computer has passed its turn
    Assistance Received: None.
    **************************************************************************/
    public Turn play(Board board, Boneyard boneyard, Turn lastTurn, Tile tempEng) {
        // check to see if engine is empty, meaning the computer must play it
        Turn confirmedTurn = new Turn();
        Tile temp = new Tile(-1, -1);
        if(board.getEngine().isEqual(temp))
        {
            for(Tile t : getHand().getTiles()) {
                if(t.isEqual(tempEng))
                {
                    Turn turn = new Turn();
                    turn.setTilePlayed(t);
                    turn.setSidePlayed("E");
                    turn.wasPassed = false;
                    board.setEngine(turn.tilePlayed);
                    getHand().getTiles().remove(turn.tilePlayed);
                    confirmedTurn = turn;
                    return confirmedTurn;
                }
            }
        }

        // used to store last tile in hand
        String currSide = "";
        String bestSide = "";
        Tile bestTile = new Tile();
        int possiblePlays = 0;
        int bestSum = 0;
        int currSum = 0;
        Turn turn = new Turn();
        for(Tile currTile : getHand().getTiles()) {
            currSum = currTile.getLeftPip() + currTile.getRightPip();
            if(currSum > bestSum)
            {
                currSide = "R";
                // play single on right first, if not, try left
                if(!(currTile.isDouble()) && lastTurn.wasPassed())
                {
                    currSide = "R";
                    turn.setSidePlayed(currSide);
                    turn.setTilePlayed(currTile);
                    if(legalTurn(turn, board, lastTurn)) {
                        bestSum = currSum;
                        bestTile = currTile;
                        bestSide = currSide;
                        possiblePlays++;
                    }
                    currSide = "L";
                    turn.setSidePlayed(currSide);
                    turn.setTilePlayed(currTile);
                    if(legalTurn(turn, board, lastTurn)) {
                        bestSum = currSum;
                        bestTile = currTile;
                        bestSide = currSide;
                        possiblePlays++;
                    }
                }
                // play double on right first, then left
			else if(currTile.isDouble())
                {
                    currSide = "R";
                    turn.setSidePlayed(currSide);
                    turn.setTilePlayed(currTile);
                    if(legalTurn(turn, board, lastTurn)) {
                        bestSum = currSum;
                        bestTile = currTile;
                        bestSide = currSide;
                        possiblePlays++;
                    }
                    currSide = "L";
                    turn.setSidePlayed(currSide);
                    turn.setTilePlayed(currTile);
                    if(legalTurn(turn, board, lastTurn)) {
                        bestSum = currSum;
                        bestTile = currTile;
                        bestSide = currSide;
                        possiblePlays++;
                    }
                }
                // play single on right side
			else
                {
                    currSide = "R";
                    turn.setSidePlayed(currSide);
                    turn.setTilePlayed(currTile);
                    if(legalTurn(turn, board, lastTurn)) {
                        bestSum = currSum;
                        bestTile = currTile;
                        bestSide = currSide;
                        possiblePlays++;
                    }
                }
            }
        }

        if(possiblePlays < 1)
        {
            confirmedTurn.wasPassed = true;
            confirmedTurn.tilePlayed = new Tile(-1, -1);
            confirmedTurn.sidePlayed = "";
            if(!boneyard.empty())
            {
                temp = boneyard.getTiles().get(boneyard.getTiles().size() - 1);
                getHand().addTile(temp);
                boneyard.getTiles().remove(boneyard.getTiles().size() - 1);

                Turn afterDrawTurn = new Turn();
                afterDrawTurn.setTilePlayed(getHand().getTiles().get(getHand().size() - 1));
                afterDrawTurn.setSidePlayed("R");
                if(legalTurn(afterDrawTurn, board,lastTurn)) {
                    Turn newTurn = new Turn();
                    newTurn.tilePlayed = afterDrawTurn.getTilePlayed();
                    newTurn.wasPassed = false;
                    newTurn.sidePlayed = "R";
                    Tile tempTile = new Tile();
                    tempTile = newTurn.getTilePlayed();
                    getHand().getTiles().remove(tempTile);
                    if(tempTile.getLeftPip() != board.getRightSide().get(board.getRightSide().size() - 1).getRightPip()) {
                        tempTile.swapPips();
                        newTurn.tilePlayed.swapPips();
                    }
                    board.getRightSide().add(tempTile);
                    confirmedTurn = newTurn;
                    return confirmedTurn;
                }
                else
                {
                    afterDrawTurn.setSidePlayed("L");
                    if(legalTurn(afterDrawTurn, board, lastTurn) && lastTurn.wasPassed()) {
                        Turn newTurn = new Turn();
                        newTurn.tilePlayed = afterDrawTurn.getTilePlayed();
                        newTurn.wasPassed = false;
                        newTurn.sidePlayed = "L";
                        Tile tempTile = new Tile();
                        tempTile = newTurn.getTilePlayed();
                        getHand().getTiles().remove(tempTile);
                        if(tempTile.getRightPip() != board.getLeftSide().get(board.getLeftSide().size() - 1).getLeftPip()) {
                            tempTile.swapPips();
                            newTurn.tilePlayed.swapPips();
                        }
                        board.getLeftSide().add(tempTile);
                        confirmedTurn =  newTurn;
                        return confirmedTurn;
                    }
                }
            }
        }
        else
        {
            Turn newTurn = new Turn();
            newTurn.tilePlayed = bestTile;
            newTurn.sidePlayed = bestSide;
            newTurn.wasPassed = false;
            if(newTurn.sidePlayed == "L")
            {
                int tmpLeftPips;
                if(board.getLeftSide().isEmpty())
                    tmpLeftPips = board.getEngine().getLeftPip();
                else
                    tmpLeftPips = board.getLeftSide().get(board.getLeftSide().size() - 1).getLeftPip();
                if(newTurn.tilePlayed.getRightPip() != tmpLeftPips)
                    newTurn.tilePlayed.swapPips();

                board.getLeftSide().add(newTurn.getTilePlayed());
                getHand().getTiles().remove(newTurn.getTilePlayed());
                confirmedTurn = newTurn;
                return confirmedTurn;
            }
            else if(newTurn.sidePlayed == "R")
            {
                int tmpRightPips;
                if(board.getRightSide().isEmpty())
                    tmpRightPips = board.getEngine().getRightPip();
                else
                    tmpRightPips = board.getRightSide().get(board.getRightSide().size() - 1).getRightPip();

                if(newTurn.tilePlayed.getLeftPip() != tmpRightPips)
                    newTurn.tilePlayed.swapPips();

                board.getRightSide().add(newTurn.getTilePlayed());
                getHand().getTiles().remove(newTurn.getTilePlayed());
                confirmedTurn = newTurn;
                return confirmedTurn;
            }
        }
        return confirmedTurn;
    }
}
