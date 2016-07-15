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
public class HeightFirstComparator implements Comparator<CustomRectangle> {

    @Override
    public int compare(CustomRectangle o1, CustomRectangle o2) {
        if (Double.compare(o1.getHeight(), o2.getHeight()) == 0) {
            return Double.compare(o1.getWidth(), o2.getWidth());
        } else {
            return Double.compare(o1.getHeight(), o2.getHeight());
        }
    }

}
