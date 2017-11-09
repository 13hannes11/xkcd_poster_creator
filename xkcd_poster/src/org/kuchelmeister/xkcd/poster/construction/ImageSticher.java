/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster.construction;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.kuchelmeister.xkcd.poster.rectangle.imagepath.PathRectangle;

/**
 * @author Hannes
 */
public class ImageSticher {
	private static final double DEFAULT_SCALE = 1D;
	private static final int IMAGE_TYPE = BufferedImage.TYPE_4BYTE_ABGR;
	private static final Color BACKGROUND_COLOR = Color.BLACK;

	private BufferedImage collage;
	private final Collection<PathRectangle> rectangles;

	public ImageSticher(final Collection<PathRectangle> rect) {
		this.rectangles = rect;
	}

	public void saveImage(final File saveFile) throws IOException {
		saveImage(saveFile, DEFAULT_SCALE);
	}

	public void saveImage(final File saveFile, final double scale) throws IOException {
		initBufferedImage(scale);
		for (final PathRectangle rectangle : rectangles) {
			try {
				final BufferedImage image = loadImage(rectangle.getPath());
				// System.out.println(rectangle);
				collage.getGraphics().drawImage(image, (int) (rectangle.getX() * scale),
						(int) (rectangle.getY() * scale), (int) (rectangle.getWidth() * scale),
						(int) (rectangle.getHeight() * scale), null);
			} catch (final IOException ex) {
				System.err.println(ex.getMessage());
			}
		}
		ImageIO.write(collage, "png", saveFile);
	}

	private void initBufferedImage(final double scale) {
		int width = 0;
		int height = 0;
		for (final PathRectangle rectangle : rectangles) {
			final int tmpWidth = (int) (rectangle.getWidth() + rectangle.getX());
			final int tmpHeight = (int) (rectangle.getHeight() + rectangle.getY());

			if (tmpWidth > width) {
				width = tmpWidth;
			}
			if (tmpHeight > height) {
				height = tmpHeight;
			}
		}

		collage = new BufferedImage((int) (width * scale), (int) (height * scale), IMAGE_TYPE);

		// collage.getGraphics().setColor(BACKGROUND_COLOR);
		// collage.getGraphics().drawRect(0, 0, collage.getWidth(),
		// collage.getHeight());
		// collage.getGraphics().fillRect(0, 0, collage.getWidth(),
		// collage.getHeight());
	}

	private BufferedImage loadImage(final File filePath) throws IOException {
		return ImageIO.read(filePath);
	}
}
