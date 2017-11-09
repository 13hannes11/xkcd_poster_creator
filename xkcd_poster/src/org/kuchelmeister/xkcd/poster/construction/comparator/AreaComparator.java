/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster.construction.comparator;

import org.kuchelmeister.xkcd.poster.rectangle.CustomRectangle;

import java.util.Comparator;

/**
 * @author Hannes
 */
public class AreaComparator implements Comparator<CustomRectangle> {

    @Override
    public int compare(final CustomRectangle o1, final CustomRectangle o2) {
        if (Double.compare(o1.width * o1.height, o2.width * o2.height) == 0) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
        } else {
            return Double.compare(o1.width * o1.height, o2.width * o2.height);
        }
    }

}
