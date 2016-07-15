/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.construction;

import xkcd.rectangle.imagepath.PathRectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Hannes
 */
public class ImageDataLoader {
    public ImageDataLoader() {
    }

    public Collection<PathRectangle> load(final File path) {
        final Collection<PathRectangle> rectangles = new ArrayList<>();

        rectangles.addAll(loadFromDirectory(path));

        return rectangles;
    }

    private Collection<PathRectangle> loadFromDirectory(final File directory) {
        final Collection<PathRectangle> rectangles = new ArrayList<>();

        for (final File fileEntry : directory.listFiles()) {
            if (fileEntry.isDirectory()) {
                rectangles.addAll(loadFromDirectory(fileEntry));
            } else {
                if (loadFile(fileEntry) != null) {
                    rectangles.add(loadFile(fileEntry));
                }
            }
        }
        return rectangles;
    }

    private PathRectangle loadFile(final File file) {
        try {
            final BufferedImage tmpImage = ImageIO.read(file);
            //TODO use correct rotation data of an Image and rotate image if necessary
            if (tmpImage == null) {
                return null;
            }
            return new PathRectangle(file, 0, 0, tmpImage.getWidth(), tmpImage.getHeight());

        } catch (final IOException ex) {
            return null;
        }
    }
}
