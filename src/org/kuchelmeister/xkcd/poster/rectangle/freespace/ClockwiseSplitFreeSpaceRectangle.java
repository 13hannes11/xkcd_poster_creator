package org.kuchelmeister.xkcd.poster.rectangle.freespace;

import java.util.ArrayList;
import java.util.Collection;

import org.kuchelmeister.xkcd.poster.rectangle.CustomRectangle;

/**
 * @author Hannes
 */
public class ClockwiseSplitFreeSpaceRectangle extends FreeSpaceRectangle {

	public ClockwiseSplitFreeSpaceRectangle(final int x, final int y, final int width, final int height) {
		super(x, y, width, height);
	}

	@Override
	public Collection<FreeSpaceRectangle> divideUp(final CustomRectangle r) {
		// System.out.println("Main: " + r.toString());
		// System.out.println("THIS: " + this.toString());
		final Collection<FreeSpaceRectangle> rectangles = new ArrayList<>();
		if (!this.contains(r)) {
			return rectangles;
		}
		final FreeSpaceFactory factory = new FreeSpaceFactory();

		// Left
		int x = 0;
		int y = 0;
		int tmpWidth = (int) (r.getX() - this.getX());
		int tmpHeight = (int) (r.getY() + r.getHeight() - this.getY());

		if (tmpHeight > 0 && tmpWidth > 0) {
			rectangles.add(factory.getFreeSpaceRect(x, y, tmpWidth, tmpHeight));
			// System.out.println("Left: " + factory.getFreeSpaceRect(x, y, tmpWidth,
			// tmpHeight).toString());
		}
		// Top
		x = (int) r.getX();
		y = (int) this.getY();
		tmpWidth = (int) (this.getX() + this.getWidth() - r.getX());
		tmpHeight = (int) (r.getY() - this.getY());

		if (tmpHeight > 0 && tmpWidth > 0) {
			rectangles.add(factory.getFreeSpaceRect(x, y, tmpWidth, tmpHeight));
			// System.out.println("Top: " + factory.getFreeSpaceRect(x, y, tmpWidth,
			// tmpHeight).toString());
		}
		// Right
		x = (int) (r.getX() + r.getWidth());
		y = (int) r.getY();
		tmpWidth = (int) (this.getX() + this.getWidth() - (r.getX() + r.getWidth()));
		tmpHeight = (int) (this.getY() + this.getHeight() - r.getY());

		if (tmpHeight > 0 && tmpWidth > 0) {
			rectangles.add(factory.getFreeSpaceRect(x, y, tmpWidth, tmpHeight));
			// System.out.println("Right: " + factory.getFreeSpaceRect(x, y, tmpWidth,
			// tmpHeight).toString());
		}

		// Bottom
		x = (int) this.getX();
		y = (int) (r.getY() + r.getHeight());
		tmpWidth = (int) ((r.getX() + r.getWidth()) - this.getX());
		tmpHeight = (int) (this.getY() + this.getHeight() - (r.getY() + r.getHeight()));

		if (tmpHeight > 0 && tmpWidth > 0) {
			rectangles.add(factory.getFreeSpaceRect(x, y, tmpWidth, tmpHeight));
			// System.out.println("Bottom: " + factory.getFreeSpaceRect(x, y, tmpWidth,
			// tmpHeight).toString());
		}
		return rectangles;
	}
}
