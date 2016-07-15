/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.construction.comparator;

import java.util.Comparator;
import xkcd.rectangle.CustomRectangle;

/**
 *
 * @author Hannes
 */
public class AreaComparator implements Comparator<CustomRectangle> {

    @Override
    public int compare(CustomRectangle o1, CustomRectangle o2) {
        return Double.compare(o1.width * o1.height, o2.width * o2.height);
    }

}
