package edu.ramapo.afouch.longana;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.lang.reflect.Field;

public class BoneyardView {
    // default cstor
    public BoneyardView() {}
    /**************************************************************************
    Function Name: draw
    Purpose: 
        To add views to the context parameter and place the views in the layout
        paramater based on the board parameter
    Paramaters: 
        layout, layout where the views will be placed
        roundActivity, context wher the view will be initially created
        boneyard, the model the layout is drawn from
    Return Value: None
    Assistance Received: None.
    **************************************************************************/
    public void draw(LinearLayout layout, Context context, Boneyard boneyard) {
        layout.removeAllViews();
        for (Tile t : boneyard.getTiles()) {
            ImageView newTile = new ImageView(context);
            int id = convertTileToId(t);
            newTile.setImageResource(id);
            newTile.setPadding(5, 5, 5, 5);
            layout.addView(newTile);
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
