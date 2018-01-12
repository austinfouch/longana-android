package edu.ramapo.afouch.longana;

public class Human extends Player {
    // default cstor -- calls Player()
    public Human() {
        super();
    }
    // copy cstor -- calls 
    public Human(int score, Hand hand) {
        super(score, hand);
    }
    /**************************************************************************
    Function Name: play
    Purpose: 
        Functionality is contained in the Player class instead of here
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
    Assistance Received: None.
    **************************************************************************/
    public Turn play(Board board, Boneyard boneyard, Turn lastTurn, Tile tempEng) {
        Turn turn = new Turn();
        return turn;
    }
}
