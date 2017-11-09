/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster.rectangle.freespace;

import org.kuchelmeister.xkcd.poster.rectangle.freespace.ClockwiseSplitFreeSpaceRectangle;
import org.kuchelmeister.xkcd.poster.rectangle.freespace.FreeSpaceRectangle;

/**
 * @author Hannes
 */
public class FreeSpaceFactory {
	private int counter;

	public FreeSpaceFactory() {
		counter = 0;
	}

	public FreeSpaceRectangle getFreeSpaceRect(final int x, final int y, final int width, final int height) {
		counter++;
		switch (counter % 2) {
		case 1:
			return new ClockwiseSplitFreeSpaceRectangle(x, y, width, height);
		// case 0
		default:
			return new CounterclockwiseFreeSpaceRectangle(x, y, width, height);
		}
	}
}
