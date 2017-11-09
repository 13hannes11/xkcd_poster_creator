/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster.construction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.kuchelmeister.xkcd.poster.rectangle.imagepath.PathRectangle;

/**
 * @author Hannes
 */
public class ImageDataLoader {
	public ImageDataLoader() {
	}

	/**
	 * loads images from a directory and all subdirectories
	 * 
	 * @param directoryPath
	 *            path to the directory
	 * @return returns a collection of loaded pathrectangles
	 */
	public Collection<PathRectangle> load(final File directoryPath) {
		return load(loadFromDirectory(directoryPath));
	}

	/**
	 * Loads images from the specified paths in the collection
	 * 
	 * @param paths
	 *            collection of paths to the images that should be loaded
	 * @return returns the loaded pathRectangles
	 */
	public Collection<PathRectangle> load(final Collection<File> paths) {
		final Collection<PathRectangle> images = new LinkedList<>();
		for (final File file : paths) {
			if (loadFile(file) != null) {
				images.add(loadFile(file));
			}
		}
		return images;
	}

	/**
	 * Searches recursively through a directory and adds all files to a list that is
	 * returned
	 * 
	 * @param directory
	 *            the directory that will be searched through
	 * @return returns all the added files from the directory
	 */
	private Collection<File> loadFromDirectory(final File directory) {
		final Collection<File> files = new LinkedList<>();

		for (final File fileEntry : directory.listFiles()) {
			if (fileEntry.isDirectory()) {
				files.addAll(loadFromDirectory(fileEntry));
			} else {
				files.add(fileEntry);
			}
		}
		return files;
	}

	private PathRectangle loadFile(final File file) {
		try {
			final BufferedImage tmpImage = ImageIO.read(file);
			// TODO use correct rotation data of an Image and rotate image if necessary
			if (tmpImage == null) {
				return null;
			}
			return new PathRectangle(file, 0, 0, tmpImage.getWidth(), tmpImage.getHeight());

		} catch (final IOException ex) {
			return null;
		}
	}
}
