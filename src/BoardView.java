package edu.ramapo.afouch.longana;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class BoardView {
    /**************************************************************************
    Function Name: drawEngine
    Purpose: 
        To set the resource image of the ImageButton parameter based on the 
        value of the engine in the Board parameter
    Paramaters: 
        engineView, the ImageButton being modified
        board, the board model needed to set the engineView resource image
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void drawEngine(ImageButton engineView, Board board) {
        if(board.getEngine().getLeftPip() != -1) {
            int resId = convertTileToId(board.getEngine());
            engineView.setImageResource(resId);
        }
    }
    /**************************************************************************
    Function Name: drawLeft
    Purpose: 
        To add views to the context parameter and place the views in the layout
        paramater based on the board parameter. Each view is placed on the left
        side of the board
    Paramaters: 
        layout, layout where the views will be placed
        roundActivity, context wher the view will be initially created
        board, the model the layout is drawn from
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void drawLeft(LinearLayout layout, Context roundActivity, Board board) {
        layout.removeAllViews();
        for (Tile t : board.getLeftSide()) {
            ImageView temp = new ImageView(roundActivity);
            int id = convertTileToId(t);
            temp.setImageResource(id);
            temp.setPadding(5, 5, 5, 5);
            layout.addView(temp, 0);
        }
    }
    /**************************************************************************
    Function Name: drawRight
    Purpose: 
        To add views to the context parameter and place the views in the layout
        paramater based on the board parameter. Each view is placed on the right
        side of the board
    Paramaters: 
        layout, layout where the views will be placed
        roundActivity, context wher the view will be initially created
        board, the model the layout is drawn from
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void drawRight(LinearLayout layout, Context roundActivity, Board board) {
        layout.removeAllViews();
        for (Tile t : board.getRightSide()) {
            ImageView temp = new ImageView(roundActivity);
            int id = convertTileToId(t);
            temp.setImageResource(id);
            temp.setPadding(5, 5, 5, 5);
            layout.addView(temp);
        }
    }
    /**************************************************************************
    Function Name: convertTiletoId
    Purpose: 
        Determine the resource image id that corresponds with the tile parameter
    Paramaters: 
        t, the tile used to determine the correct resource image id
    Return Value:
        Integer value representing the resource image id that matched the tile
        parameter
    Assistance Received: None.
    **************************************************************************/
    public int convertTileToId(Tile t) {
        String temp = "";
        int leftPips = t.getLeftPip();
        int rightPips = t.getRightPip();
        switch(leftPips) {
            case 0:
                temp = "zero_";
                break;
            case 1:
                temp = "one_";
                break;
            case 2:
                temp = "two_";
                break;
            case 3:
                temp = "three_";
                break;
            case 4:
                temp = "four_";
                break;
            case 5:
                temp = "five_";
                break;
            case 6:
                temp = "six_";
                break;
            default:
                break;
        }

        switch(rightPips) {
            case 0:
                temp +=  "zero";
                break;
            case 1:
                temp += "one";
                break;
            case 2:
                temp += "two";
                break;
            case 3:
                temp += "three";
                break;
            case 4:
                temp += "four";
                break;
            case 5:
                temp += "five";
                break;
            case 6:
                temp += "six";
                break;
            default:
                break;
        }

        int id = getResId(temp, R.drawable.class);
        return id;
    }
    /**************************************************************************
    Function Name: getResId
    Purpose: 
        Determine the id value of the resName parameter within the Class c
    Paramaters: 
        resName, the resource name
        c, the class where the resource is located
    Return Value:
        Integer value representing the resource image id that matched the tile
        parameter
    Assistance Received: None.
    **************************************************************************/
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
