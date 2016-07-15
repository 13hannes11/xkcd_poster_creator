/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.construction.comparator;

import xkcd.rectangle.CustomRectangle;

import java.util.Comparator;

/**
 * @author Hannes
 */
public class HeightFirstComparator implements Comparator<CustomRectangle> {

    @Override
    public int compare(final CustomRectangle o1, final CustomRectangle o2) {
        if (Double.compare(o1.getHeight(), o2.getHeight()) == 0) {
            if (Double.compare(o1.getWidth(), o2.getWidth()) == 0) {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            } else {
                return Double.compare(o1.getWidth(), o2.getWidth());
            }
        } else {
            return Double.compare(o1.getHeight(), o2.getHeight());
        }
    }

}
