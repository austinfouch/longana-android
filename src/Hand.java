package edu.ramapo.afouch.longana;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    // member variables
    private List<Tile> tiles;
    // default cstor
    public Hand() { this.tiles = new ArrayList<Tile>(); }
    // copy cstor
    public Hand(List<Tile> tiles) {
        this.tiles = tiles;
    }
    /**************************************************************************
    Function Name: addTile
    Purpose: 
        To add a Tile object to the tiles member variable
    Paramaters:
        t, a Tile object
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void addTile(Tile t) {
        this.tiles.add(t);
    }
    /**************************************************************************
    Function Name: removeTile
    Purpose: 
        To remove a specific tile from the tiles member variable
    Paramaters:
        t, a Tile object to remove
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void removeTile(Tile t) {
        this.tiles.remove(t);
    }
    /**************************************************************************
    Function Name: searchFor
    Purpose: 
        To return the index of a specific tile in the tiles member variable
    Paramaters:
        t, a Tile object to search for
    Return Value:
        Returns an integer value corresponding to the index of the searched
        tile. If the tile was not found, -1 is returned.
    Assistance Received: None.
    **************************************************************************/
    public int searchFor(Tile t) {
        return this.tiles.indexOf(t);
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
    /**************************************************************************
    Function Name: size
    Purpose: 
        To return the size of the member variables tiles as an integer value
    Paramaters: None
    Return Value:
        Integer
    Assistance Received: None.
    **************************************************************************/
    public int size() { return getTiles().size(); }
}
