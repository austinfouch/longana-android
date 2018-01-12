package edu.ramapo.afouch.longana;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Boneyard {
    // member variables
    private List<Tile> tiles;
    // default cstor
    public Boneyard() { this.tiles = new ArrayList<Tile>(); }
    // copy cstor
    public Boneyard(List<Tile> tiles) {
        this.tiles = tiles;
    }
    /**************************************************************************
    Function Name: addTiles
    Purpose: 
        Appends the List paramater to the member variable tiles
    Paramaters: 
        tiles, the List object of type Tile that will be appended to the tiles
        member variable
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void addTiles(List<Tile> tiles) {
        this.tiles.addAll(tiles);
    }
    /**************************************************************************
    Function Name: pop
    Purpose: 
        Remove the last tile in the tiles member variable and return its value
    Paramaters: None
    Return Value:
        Tile object
    Assistance Received: None.
    **************************************************************************/
    public Tile pop() {
        Tile temp = new Tile();
        temp = getTiles().get(getTiles().size() - 1);
        getTiles().remove(getTiles().size() - 1);
        return temp;
    }
    /**************************************************************************
    Function Name: getTiles
    Purpose: 
        Return the value of the member variable tiles
    Paramaters: None
    Return Value: 
        List object of type Tile
    Assistance Received: None.
    **************************************************************************/
    public List<Tile> getTiles() {
        return tiles;
    }
    /**************************************************************************
    Function Name: setTiles
    Purpose: 
        To set the value of the member variable tiles
    Paramaters:
        List object of type Tile
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
    /**************************************************************************
    Function Name: empty
    Purpose: 
        To return true if the member variable tiles is empty or false otherwise
    Paramaters: None
    Return Value:
        Boolean
    Assistance Received: None.
    **************************************************************************/
    public boolean empty() { return getTiles().isEmpty(); }
}
