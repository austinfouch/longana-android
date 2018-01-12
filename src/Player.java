package edu.ramapo.afouch.longana;

public abstract class Player {
    // member variables
    private int score;
    private Hand hand;
    // default cstor
    protected Player() {
        this.hand = new Hand();
        this.score = 0;
    }
    // copy cstor
    protected Player(int score, Hand hand) {
        this.score = score;
        this.hand = hand;
    }
    /**************************************************************************
    Function Name: getScore
    Purpose: 
        Return the value of the member variable score
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getScore() {
        return score;
    }
    /**************************************************************************
    Function Name: setTiles
    Purpose: 
        To set the value of the member variable score
    Paramaters:
        score, integer value to set the member variable score to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setScore(int score) {
        this.score = score;
    }
    /**************************************************************************
    Function Name: getHand
    Purpose: 
        Return the value of the member variable hand
    Paramaters: None
    Return Value: 
        Hand object
    Assistance Received: None.
    **************************************************************************/
    public Hand getHand() {
        return hand;
    }
    /**************************************************************************
    Function Name: setHand
    Purpose: 
        To set the value of the member variable hand
    Paramaters:
        hand, Hand object to set the member variable hand to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setHand(Hand hand) {
        this.hand = hand;
    }
    /**************************************************************************
    Function Name: play
    Purpose: 
        Nothing, abstract fucnction
    Paramaters:
        board, the board of the round
        boneyard, the boneyard of the round
        lastTurn, the last turn of the round
        tempEng, the engine of the round
    Return Value:
        an object of type Turn
    Assistance Received: None
    **************************************************************************/
    abstract Turn play(Board board, Boneyard boneyard, Turn lastTurn, Tile tempEng);
    /**************************************************************************
    Function Name: legal_play
    Purpose: 
        To check the passed play for legality against the passed board
    Paramaters:
        turn, a turn object representing the play being attempted
        board, the board model the attempt is being made on
        lastTurn, the last turn of the round, used to determine which sides
            of the board are legal plays
    Return Value: 
        true if the play is legal
        fasle if it is not
    Local variables:
        inLeftPips, an integer representing the left pips of the tile in the 
            player's hand at the index passed
        inRightPips, an integer representing the right pips of the tile in the 
            player's hand at the index passed
    Algorithm:
        1) Set inLeftPips and inRightPips based on the player's hand and the
        index passed to the function
        2) If the play is attempting to play a tile on the left side, check the
        board's left most tile. If this tile's left pips are equal to either
        inRightPips or inLeftPips, return true.
        3) If the play is attempting to play on the right side, check the
        board's right most tile. If this tile's right pips are equal to either
        inRightPips or in LeftPips, return true.
        4) Otherwise, return false.
    Assistance Received: None
    **************************************************************************/
    public boolean legalTurn(Turn turn, Board board, Turn lastTurn) {
        int inLeftPips, inRightPips;
        inLeftPips = turn.getTilePlayed().getLeftPip();
        inRightPips = turn.getTilePlayed().getRightPip();

        if(turn.sidePlayed == "E") {
            if(board.getEngine().getLeftPip() == -1) {
                return true;
            }
        }

        int tmpLeftSide = -1;
        int tmpRightSide = -1;
        if(turn.getSidePlayed() == "L" && (this instanceof Human || lastTurn.wasPassed || turn.tilePlayed.isDouble())) {
            if (board.getLeftSide().isEmpty()) {
                tmpLeftSide = board.getEngine().getLeftPip();
            } else {
                tmpLeftSide = board.getLeftSide().get(board.getLeftSide().size() - 1).getLeftPip();
            }
        } else if(turn.getSidePlayed() == "R" && (this instanceof Computer || lastTurn.wasPassed || turn.tilePlayed.isDouble())) {
            if (board.getRightSide().isEmpty()) {
                tmpRightSide = board.getEngine().getLeftPip();
            } else {
                tmpRightSide = board.getRightSide().get(board.getRightSide().size() - 1).getRightPip();
            }
        }

        if(inLeftPips == tmpLeftSide || inLeftPips == tmpRightSide) {
            return true;
        }
        if(inRightPips == tmpLeftSide || inRightPips == tmpRightSide) {
            return true;
        }

        return false;
    }
    /**************************************************************************
    Function Name: help
    Purpose: 
        To return a Turn object that represents the recommended play based on 
        the parameters and the member variable values
    Paramaters:
        board, the board of the current round
        lastTurn, the last turn of the round for determing if there was a pass
    Return Value:
        Turn object representing the best tile to play, the side to play it,
            and whether or not the turn should be passed
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
        board. If they are equal, the user must have the engine.
            1.b) Iterate over the player's hand and find the index representing the
            engine. Set bestTile equal to this index, bestSide equal to E(for engine)
            and increment possiblePlays by 1;
        2) If the engine is not empty, iterate ovr the player's hand, summing up
        the pips of each tile. The tile with the highest pip sum has the highest 
        priority to be recommended.
        3) Check to see if the highest priority tile is a legal play on the board.
            3.a) If it is, recommend that tile and the side of the board it can be 
            played. If the tile can be played on either side, play it on their 
            opponent's side. Increment possiblePlays by one, set the bestSide to the
            side being played, and set bestTile to the tile's index in the player's
            hand.
            3.b) If the highest priority tile is not a legal play, then look for the
            next highest priority tile and repeat the process.
        4) If possiblePlays is greater than 0, then the recommended play is
        displayed.
        5) If possiblePlays is 0, then recommend the user passes.
    Assistance Received: None
    **************************************************************************/
    public Turn help(Board board, Turn lastTurn) {
        Turn turn = new Turn();
        int possiblePlays = 0;
        String currSide = "";
        String bestSide = "";
        //Tile currTile = new Tile();
        Tile bestTile = new Tile();
        int bestSum = 0;
        int currSum = 0;
        // check to see if engine is empty, meaning the computer must play it
        Tile tempEng = new Tile(-1, -1);
        if(board.getEngine().isEqual(tempEng)) {
            for(Tile t : getHand().getTiles()) {
                if(board.getEngine().isEqual(t)) {
                    bestTile = t;
                    bestSide = "E";
                    possiblePlays++;
                }
            }
        } else {
            // used to store last tile in hand
            for(Tile currTile : getHand().getTiles()) {
                // sum pips of currTile
                currSum = currTile.getLeftPip() + currTile.getRightPip();
                if(currSum > bestSum) {
                    currSide = "L";
                    // attempt to play a single on the left first, then right
                    if(!(currTile.isDouble()) && lastTurn.wasPassed()) {
                        currSide = "L";
                        turn.setSidePlayed(currSide);
                        turn.setTilePlayed(currTile);
                        if(legalTurn(turn, board, lastTurn)) {
                            bestSum = currSum;
                            bestTile = currTile;
                            bestSide = currSide;
                            possiblePlays++;
                        }
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
                    // attempt to play double on left first, then right
				    else if(currTile.isDouble()) {
                        currSide = "L";
                        turn.setSidePlayed(currSide);
                        turn.setTilePlayed(currTile);
                        if(legalTurn(turn, board, lastTurn)) {
                            bestSum = currSum;
                            bestTile = currTile;
                            bestSide = currSide;
                            possiblePlays++;
                        }
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
                    // play single on right left
				    else {
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
                }
            }
        }

        turn.setSidePlayed(bestSide);
        turn.setTilePlayed(bestTile);
        return turn;
    }
    /**************************************************************************
    Function Name: pass
    Purpose: 
        To check the user's hand for possible moves and determine if a pass is a
        legal play. The function makes a call to help, and if the turn object
        returned by help has a valid tile stored, then the player cannot pass.
    Paramaters:
        board, the board of the round
        lastTurn, the last turn of the round
    Return Value:
        a boolean value representing whether or not the player can pass
    Assistance Received: None
    **************************************************************************/
    public boolean pass(Board board, Turn lastTurn) {
        Turn temp = help(board, lastTurn);
        if(board.getEngine().getLeftPip() == -1) {
            return false;
        } else if(temp.getTilePlayed().getLeftPip() == -1) {
            return true;
        } else {
            return false;
        }
    }
    /**************************************************************************
    Function Name: drawTile
    Purpose: 
        Adds a tile to the player's hand
    Paramaters:
        t, tile object to be added
    Return Value: None
    Assistance Received: None
    **************************************************************************/
    public void drawTile(Tile t) {
        this.hand.addTile(t);
    }
    /**************************************************************************
    Function Name: playTile
    Purpose: 
        Removes the tile in the player's hand matching the Tile parameter and
        returns its value
    Paramaters:
        t, tile object to be searched for
    Return Value:
        Tile object
    Assistance Received: None
    **************************************************************************/
    public Tile playTile(Tile t) {
        Tile temp = t;
        this.hand.removeTile(t);
        return temp;
    }
}
