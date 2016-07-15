/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.rectangle.freespace;

import java.util.Collection;
import xkcd.rectangle.CustomRectangle;

/**
 *
 * @author Hannes
 */
public abstract class FreeSpaceRectangle extends CustomRectangle {

    public FreeSpaceRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    /**
     * Method returns the smaller rectangles that will be generated if 'r' is placed inside the xkcd.rectangle
     * If r is outsdie of this xkcd.rectangle the Collection will conly contain this.
     * Otherwise it will only contain subrectangles.
     * @param r
     * the xkcd.rectangle which is placed and arround which shall be split
     * @return 
     * xkcd.rectangle
     */
    public abstract Collection<FreeSpaceRectangle> divideUp(CustomRectangle r);
}
