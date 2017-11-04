/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.construction.comparator;

import java.awt.image.BufferedImage;
import java.util.Comparator;

/**
 *
 * @author Hannes
 */
public class ImageMegapixelComparator implements Comparator<BufferedImage> {

	@Override
	public int compare(final BufferedImage o1, final BufferedImage o2) {
		return o2.getWidth() * o2.getHeight() - o1.getWidth() * o1.getHeight();
	}
}
