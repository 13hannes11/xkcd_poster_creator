/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.construction;

import xkcd.construction.comparator.AreaComparator;
import xkcd.construction.comparator.HeightFirstComparator;
import xkcd.construction.comparator.WidthFirstComparator;
import xkcd.rectangle.freespace.ClockwiseSplitFreeSpaceRectangle;
import xkcd.rectangle.freespace.FreeSpaceRectangle;
import xkcd.rectangle.imagepath.PathRectangle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * @author Hannes
 */
public class ImageArranger {


    private final FreeSpaceRectangle currentBounds;

    private final Collection<PathRectangle> placed;

    private final double factor;
    private final int startStepCount;
    private final int stopper;

    public ImageArranger() {
        factor = 0.5D;
        startStepCount = 10000;
        stopper = 500;

        currentBounds = null;

        placed = new ArrayList<>();

    }

    /**
     * Shifts the left top corener to the coordinates (0;0)
     */
    private void shiftToLeftTop() {
        if (currentBounds == null)
            return;
        final int x = (int) -currentBounds.getX();
        final int y = (int) -currentBounds.getY();

        for (final PathRectangle rect : placed) {
            rect.setX((int) (rect.getX() + x));
            rect.setY((int) (rect.getY() + y));
        }
        currentBounds.setX(0);
        currentBounds.setY(0);
    }

    public Collection<PathRectangle> arrangeRectangles(final Collection<PathRectangle> rectangles, final double heightToWidthRatio) {
        int stepCount = startStepCount;

        int height = stepCount;
        int width = calcWidthFromHeight(stepCount, heightToWidthRatio);


        int direction = 1;
        boolean readyToStop = false;

        while (!readyToStop || arrangeRectangles(rectangles, width, height) == null) {
            //Loopcancelpermission: if accuracy is reached (stopper) and direction is from to small to to big (direction > 0)
            if (stepCount < stopper && direction > 0) {
                readyToStop = true;
            }
            if ((arrangeRectangles(rectangles, width, height) != null && direction > 0)
                    || (arrangeRectangles(rectangles, width, height) == null && direction < 0)) {
                stepCount *= factor;
                direction *= -1;
            }
            height += direction * stepCount;
            width = calcWidthFromHeight(height, heightToWidthRatio);
            System.out.println("W: " + width + " H:" + height + " StepCount: " + stepCount + " Direction: " + direction +
                    " ArrangeRectangles: " + ((arrangeRectangles(rectangles, width, height) == null) ? null : arrangeRectangles(rectangles, width, height).size()));
        }

        return arrangeRectangles(rectangles, width, height);
    }

    private int calcWidthFromHeight(final int height, final double heightToWidthRatio) {
        return (int) Math.round(height * heightToWidthRatio);
    }

    public Collection<PathRectangle> arrangeRectangles(final Collection<PathRectangle> rectangles, final int width, final int height) {
        //System.out.println("Size: " + rectangles);

        placed.clear();
        final TreeSet<PathRectangle> widthSortedSet = new TreeSet<>(new WidthFirstComparator());
        final TreeSet<PathRectangle> heightSortedSet = new TreeSet<>(new HeightFirstComparator());

        final TreeSet<FreeSpaceRectangle> freeSpaceArea = new TreeSet<>(new AreaComparator());

        widthSortedSet.addAll(rectangles);
        heightSortedSet.addAll(rectangles);

        freeSpaceArea.add(new ClockwiseSplitFreeSpaceRectangle(0, 0, width, height));

        while (widthSortedSet.size() > 0 && heightSortedSet.size() > 0) {
            final PathRectangle _tmp = (widthSortedSet.last().getWidth() > heightSortedSet.last().getHeight()) ? widthSortedSet.last() : heightSortedSet.last();

            widthSortedSet.remove(_tmp);
            heightSortedSet.remove(_tmp);

            FreeSpaceRectangle _space = null;

            for (final FreeSpaceRectangle s : freeSpaceArea) {
                if (s.couldContain(_tmp)) {
                    _space = s;
                    break;
                }
            }
            //No space means not every image can be placed
            if (_space == null) {
                return null;
            }

            placed.add(_tmp);
            _tmp.setLocation((int) _space.getX(), (int) _space.getY());
            freeSpaceArea.addAll(_space.divideUp(_tmp));
            freeSpaceArea.remove(_space);
        }

        shiftToLeftTop();

        //<DEBUG>
        /*
        double spaceHeight = 0;
        double spaceWidth = 0;
        for (final FreeSpaceRectangle rect : freeSpaceArea) {
            if (rect.getWidth() + rect.getX() > spaceWidth) {
                spaceWidth = rect.getWidth() + rect.getX();
            }
            if (rect.getHeight() + rect.getY() > spaceHeight) {
                spaceHeight = rect.getHeight() + rect.getY();
            }
        }
        final double multiplier = 0.1;
        final BufferedImage bImage = new BufferedImage((int) (spaceWidth * multiplier), (int) (spaceHeight * multiplier), BufferedImage.TYPE_INT_ARGB);

        for (final FreeSpaceRectangle rect : freeSpaceArea) {
            final Graphics g = bImage.getGraphics();


            g.setColor(Color.blue);
            g.fillRect((int) (rect.getX() * multiplier), (int) (rect.getY() * multiplier), (int) (rect.getWidth() * multiplier), (int) (rect.getHeight() * multiplier));
            g.setColor(Color.red);
            g.drawRect((int) (rect.getX() * multiplier), (int) (rect.getY() * multiplier), (int) (rect.getWidth() * multiplier), (int) (rect.getHeight() * multiplier));
        }
        try {
            ImageIO.write(bImage, "png", new File("C:\\Users\\Hannes\\Desktop\\savedNewFreeSpace.png"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        */
        //</DEGUB>
        return placed;
    }

    private PathRectangle getNextPathRectangle(final PathRectangle widthRec, final PathRectangle heightRect) {
        return (widthRec.getWidth() > heightRect.getHeight())
                ? widthRec : heightRect;
    }
}
