import xkcd.construction.ImageArranger;
import xkcd.construction.ImageDataLoader;
import xkcd.construction.ImageSticher;
import xkcd.rectangle.imagepath.PathRectangle;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Main {

    static final String PATH = "C:\\Users\\Hannes\\xkcd\\";

    public static void main(final String[] args) throws IOException {
        System.out.println("Loading Now!");
        final ImageDataLoader loader = new ImageDataLoader();
        Collection<PathRectangle> images = loader.load(new File(PATH));

        System.out.println("Arranging Now!");
        final ImageArranger arranger = new ImageArranger();
        images = arranger.arrangeRectangles(images, 3D / 2D);
        System.out.println("Count: " + images.size());

        System.out.println("Saving Now!");
        final ImageSticher sticher = new ImageSticher(images);
        sticher.saveImage(new File("C:\\Users\\Hannes\\Desktop\\savedNew.png"));
        System.out.println("DONE!");
    }
}
