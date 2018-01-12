package edu.ramapo.afouch.longana;

import java.util.ArrayList;
import java.util.List;

public class Board {
    // member variables
    private List<Tile> leftSide;
    private List<Tile> rightSide;
    private Tile engine;
    // default cstor
    public Board() {
        this.leftSide = new ArrayList<>();
        this.rightSide = new ArrayList<>();
        this.engine = new Tile(-1, -1);
    }
    // copy cstor
    public Board(List<Tile> leftSide, List<Tile> rightSide, Tile engine) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.engine = engine;
    }
    /**************************************************************************
    Function Name: getLeftSide
    Purpose: 
        Return the value of the member variable leftSide
    Paramaters: None
    Return Value: 
        List object of type Tile
    Assistance Received: None.
    **************************************************************************/
    public List<Tile> getLeftSide() {
        return leftSide;
    }
    /**************************************************************************
    Function Name: setLeftSide
    Purpose: 
        To set the value of the member variable leftSide
    Paramaters:
        List object of type Tile
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setLeftSide(List<Tile> leftSide) {
        this.leftSide = leftSide;
    }
    /**************************************************************************
    Function Name: getRightSide
    Purpose: 
        Return the value of the member variable rightSide
    Paramaters: None
    Return Value: 
        List object of type Tile
    Assistance Received: None.
    **************************************************************************/
    public List<Tile> getRightSide() {
        return rightSide;
    }
    /**************************************************************************
    Function Name: setRightSide
    Purpose: 
        To set the value of the member variable rightSide
    Paramaters:
        List object of type Tile
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setRightSide(List<Tile> rightSide) {
        this.rightSide = rightSide;
    }
    /**************************************************************************
    Function Name: getEngine
    Purpose: 
        Return the value of the member variable engine
    Paramaters: None
    Return Value: 
        Tile object
    Assistance Received: None.
    **************************************************************************/
    public Tile getEngine() {
        return engine;
    }
    /**************************************************************************
    Function Name: setEngine
    Purpose: 
        To set the value of the member variable engine
    Paramaters:
        Tile object
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setEngine(Tile engine) {
        this.engine = engine;
    }
    /**************************************************************************
    Function Name: addLeftSide
    Purpose: 
        To add a Tile element to the leftSide member variable
    Paramaters:
        Tile object
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void addLeftSide(Tile t) {
        this.leftSide.add(t);
    }
    /**************************************************************************
    Function Name: addRightSide
    Purpose: 
        To add a Tile element to the rightSide member variable
    Paramaters:
        Tile object
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void addRightSide(Tile t) {
        this.leftSide.add(t);
    }
}
