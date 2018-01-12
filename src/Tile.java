package edu.ramapo.afouch.longana;

public class Tile {
    // member variables
    private int rightPip;
    private int leftPip;
    // default cstor   
    public Tile() {
        this.leftPip = -1;
        this.rightPip = -1;
    }
    // copy cstor
    public Tile(int leftPip, int rightPip) {
        this.leftPip = leftPip;
        this.rightPip = rightPip;
    }
    /**************************************************************************
    Function Name: getLeftPip
    Purpose: 
        Return the value of the member variable leftPip
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getLeftPip() {
        return leftPip;
    }
    /**************************************************************************
    Function Name: setLeftPip
    Purpose: 
        To set the value of the member variable leftPip
    Paramaters:
        leftPip, integer to set member variable leftPip to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setLeftPip(int leftPip) {
        this.leftPip = leftPip;
    }
    /**************************************************************************
    Function Name: getRightPip
    Purpose: 
        Return the value of the member variable RightPip
    Paramaters: None
    Return Value: 
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int getRightPip() {
        return rightPip;
    }
    /**************************************************************************
    Function Name: setRightPip
    Purpose: 
        To set the value of the member variable rightPip
    Paramaters:
        leftPip, integer to set member variable rightPip to
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setRightPip(int rightPip) {
        this.rightPip = rightPip;
    }
    /**************************************************************************
    Function Name: swapPips
    Purpose: 
        To the values of the member variables leftPip and rightPip
    Paramaters: None
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void swapPips() {
        int temp = getLeftPip();
        setLeftPip(getRightPip());
        setRightPip(temp);
    }
    /**************************************************************************
    Function Name: isEqual
    Purpose: 
        To return true if this tile is equal to the tile parameter, false 
        otherwise
    Paramaters:
        t, Tile object to compare to this tile
    Return Value:
        Boolean
    Assistance Received: None.
    **************************************************************************/
    public boolean isEqual(Tile t) {
        if(getLeftPip() == t.getLeftPip()) {
            if(getRightPip() == t.getRightPip()) {
                return true;
            }
        }
        return false;
    }
    /**************************************************************************
    Function Name: isDouble
    Purpose: 
        To return true if this tile's left pips equal this tile's right pips, 
        false otherwise
    Paramaters: None
    Return Value:
        Boolean
    Assistance Received: None.
    **************************************************************************/
    public boolean isDouble() {
        return (getLeftPip() == getRightPip());
    }
}
