package org.kuchelmeister.xkcd.poster.rectangle.freespace;

import java.util.Collection;

import org.kuchelmeister.xkcd.poster.rectangle.CustomRectangle;

/**
 * @param <T>
 * @author Hannes
 */
public class TopBottomSplitFreeSpaceRectangle extends FreeSpaceRectangle {

	public TopBottomSplitFreeSpaceRectangle(final int x, final int y, final int width, final int height) {
		super(x, y, width, height);
	}

	@Override
	public Collection<FreeSpaceRectangle> divideUp(final CustomRectangle r) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}
}
