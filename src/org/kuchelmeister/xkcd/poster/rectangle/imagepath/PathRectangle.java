/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster.rectangle.imagepath;

import java.io.File;
import java.io.FileNotFoundException;

import org.kuchelmeister.xkcd.poster.rectangle.CustomRectangle;

/**
 * @author Hannes
 */
public class PathRectangle extends CustomRectangle {
	private final File path;

	public PathRectangle(final File filePath, final int x, final int y, final int width, final int height)
			throws FileNotFoundException {
		super(x, y, width, height);
		path = filePath;
		if (!path.exists()) {
			throw new FileNotFoundException("The filepath is not valid");
		}
	}

	/**
	 * @return the path
	 */
	public File getPath() {
		return path;
	}

	@Override
	public String toString() {
		return super.toString() + " Path: " + path.toString();
	}
}
