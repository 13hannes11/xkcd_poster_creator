/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster.construction.comparator;

import java.util.Comparator;

import org.kuchelmeister.xkcd.poster.rectangle.CustomRectangle;

/**
 * @author Hannes
 */
public class WidthFirstComparator implements Comparator<CustomRectangle> {

	@Override
	public int compare(final CustomRectangle o1, final CustomRectangle o2) {
		if (Double.compare(o1.getWidth(), o2.getWidth()) == 0) {
			if (Double.compare(o1.getHeight(), o2.getHeight()) == 0) {
				return Integer.compare(o1.hashCode(), o2.hashCode());
			} else {
				return Double.compare(o1.getHeight(), o2.getHeight());
			}
		} else {
			return Double.compare(o1.getWidth(), o2.getWidth());
		}
	}

}
