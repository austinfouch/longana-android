package edu.ramapo.afouch.longana;

public class Turn {
    // member variables
    Tile tilePlayed;
    boolean wasPassed;
    String sidePlayed;
    // default cstor
    public Turn() {
        this.wasPassed = false;
        this.sidePlayed = "";
        this.tilePlayed = new Tile(-1, -1);
    }
    // copy cstor
    public Turn(Tile tilePlayed, boolean wasPassed, String sidePlayed) {
        this.tilePlayed = tilePlayed;
        this.wasPassed = wasPassed;
        this.sidePlayed = sidePlayed;
    }
    /**************************************************************************
    Function Name: getTilePlayed
    Purpose: 
        Return the value of the member variable tilePLayed
    Paramaters: None
    Return Value: 
        Tile object
    Assistance Received: None.
    **************************************************************************/   
    public Tile getTilePlayed() {
        return tilePlayed;
    }
    /**************************************************************************
    Function Name: setTilePlayed
    Purpose: 
        To set the value of the member variable tilePlayed
    Paramaters:
        tilePlayed, tile to set member variable tilePlayed to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setTilePlayed(Tile tilePlayed) {
        this.tilePlayed = tilePlayed;
    }
    /**************************************************************************
    Function Name: wasPassed
    Purpose: 
        Return the value of the member variable wasPassed
    Paramaters: None
    Return Value: 
        Boolean
    Assistance Received: None.
    **************************************************************************/ 
    public boolean wasPassed() {
        return wasPassed;
    }
    /**************************************************************************
    Function Name: setWasPassed
    Purpose: 
        To set the value of the member variable wasPassed
    Paramaters:
        wasPassed, boolean to set member variable wasPassed to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setWasPassed(boolean wasPassed) {
        this.wasPassed = wasPassed;
    }
    /**************************************************************************
    Function Name: getSidePlayed
    Purpose: 
        Return the value of the member variable sidePlayed
    Paramaters: None
    Return Value: 
        String
    Assistance Received: None.
    **************************************************************************/ 
    public String getSidePlayed() {
        return sidePlayed;
    }
    /**************************************************************************
    Function Name: setSidePlayed
    Purpose: 
        To set the value of the member variable sidePlayed
    Paramaters:
        sidePlayed, string to set member variable sidePlayed to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setSidePlayed(String sidePlayed) {
        this.sidePlayed = sidePlayed;
    }
}
