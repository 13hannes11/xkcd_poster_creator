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
import xkcd.rectangle.freespace.FreeSpaceFactory;
import xkcd.rectangle.freespace.FreeSpaceRectangle;
import xkcd.rectangle.imagepath.PathRectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Hannes
 */
public class ImageArranger {

    private double ratio;
    private final boolean top;
    private final boolean left;

    private FreeSpaceRectangle currentBounds;

    private final TreeSet<FreeSpaceRectangle> freeSpaceArea;

    private final TreeSet<PathRectangle> widthSortedSet;
    private final TreeSet<PathRectangle> heightSortedSet;

    private final Collection<PathRectangle> placed;

    public ImageArranger() {
        ratio = 1D;
        currentBounds = null;

        left = false; //Bug with left = true
        top = false;

        widthSortedSet = new TreeSet<>(new WidthFirstComparator());
        heightSortedSet = new TreeSet<>(new HeightFirstComparator());

        freeSpaceArea = new TreeSet<>(new AreaComparator());

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
        System.out.println("Size: " + rectangles);
        ratio = heightToWidthRatio;

        widthSortedSet.addAll(rectangles);
        heightSortedSet.addAll(rectangles);

        freeSpaceArea.add(new ClockwiseSplitFreeSpaceRectangle(0, 0, 30000, 15000));
        //expandAndPlace(getNextPathRectangle(widthSortedSet.last(), heightSortedSet.last()));
        while (widthSortedSet.size() > 0 && heightSortedSet.size() > 0) {
            //expandAndPlace(getNextPathRectangle(widthSortedSet.last(), heightSortedSet.last()));

            final Iterator<PathRectangle> heightIterator = heightSortedSet.descendingIterator();
            final Iterator<PathRectangle> widthIterator = widthSortedSet.descendingIterator();

            final Collection<PathRectangle> pathRectToRemove = new LinkedList<>();
            boolean oneFree = false;

            while (heightIterator.hasNext() || widthIterator.hasNext()) {
                PathRectangle tmpPathRect = null;
                if (heightIterator.hasNext()) {
                    tmpPathRect = heightIterator.next();
                }
                if (widthIterator.hasNext()) {
                    if (tmpPathRect == null) {
                        tmpPathRect = widthIterator.next();
                    } else {
                        tmpPathRect = getNextPathRectangle(widthIterator.next(), tmpPathRect);
                    }
                }
                final Collection<FreeSpaceRectangle> freeSpaceToAdd = new ArrayList<>();
                for (final Iterator<FreeSpaceRectangle> iterator = freeSpaceArea.iterator(); iterator.hasNext(); ) {
                    final FreeSpaceRectangle nextFree = iterator.next();
                    if (nextFree.couldContain(tmpPathRect) && !placed.contains(tmpPathRect)) {
                        //place(nextFree, tmpPathRect, freeSpaceToAdd);
                        oneFree = true;

                        iterator.remove();
                        tmpPathRect.setX((int) nextFree.getX());
                        tmpPathRect.setY((int) nextFree.getY());


                        freeSpaceToAdd.addAll(nextFree.divideUp(tmpPathRect));
                        placed.add(tmpPathRect);
                        System.out.println("Size: " + placed.size());
                        //DEBUG
                        if (Math.abs(tmpPathRect.getWidth() - 612) < 1.0 && Math.abs(tmpPathRect.getHeight() - 6370) < 1.0)
                            System.out.print("EXISTS");
                        //ENDDEBUG
                        System.out.println(tmpPathRect);
                        //System.out.println("Freespace to add: " + freeSpaceToAdd.size());


                        pathRectToRemove.add(tmpPathRect);

                        break;
                    }
                }
                freeSpaceArea.addAll(freeSpaceToAdd);
            }
            if (!oneFree) {
                System.out.println("No Space Left");
                break;
            }
            widthSortedSet.removeAll(pathRectToRemove);
            heightSortedSet.removeAll(pathRectToRemove);
            //TODO: freeSpaceArea.clear();
        }
        shiftToLeftTop();

        //<DEBUG>
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
        //</DEGUB>
        return placed;
    }

    private PathRectangle getNextPathRectangle(final PathRectangle widthRec, final PathRectangle heightRect) {
        return (widthRec.getWidth() > heightRect.getHeight())
                ? widthRec : heightRect;
    }
    
    /*
    private void place(FreeSpaceRectangle freeSpace, PathRectangle toPlace,){
        freeSpaceArea.remove(freeSpace);
        freeSpaceArea.addAll(freeSpace.divideUp(toPlace));
        
        heightSortedSet.remove(toPlace);
        widthSortedSet.remove(toPlace);
        placed.add(toPlace);

        toPlace.setX((int) freeSpace.getX());
        toPlace.setY((int) freeSpace.getY());
    }*/

    private void expandAndPlace(final PathRectangle rect) {
        final FreeSpaceFactory factory = new FreeSpaceFactory();
        if (currentBounds == null) {
            currentBounds = factory.getFreeSpaceRect(0, 0, (int) rect.getWidth(), (int) rect.getHeight());

            rect.setX(0);
            rect.setY(0);

            addPlaced(rect);
        } else {
            final FreeSpaceRectangle tmpNewFree = factory.getFreeSpaceRect((int) currentBounds.getX(), (int) currentBounds.getY(),
                    (int) currentBounds.getWidth(),
                    (int) currentBounds.getHeight());
            //Top or Bottom
            if (rect.height > rect.width) {

                tmpNewFree.setHeight((int) (currentBounds.getHeight() + rect.getHeight()));
                tmpNewFree.setWidth((int) (tmpNewFree.getHeight() * ratio));

                if (top) {
                    final int x = (int) currentBounds.getX();
                    final int y = (int) (currentBounds.getY() - rect.getHeight());

                    rect.setX(x);
                    rect.setY(y);
                    tmpNewFree.setX(x);
                    tmpNewFree.setY(y);
                } else {
                    rect.setX((int) currentBounds.getX());
                    rect.setY((int) (currentBounds.getY() + currentBounds.getHeight()));
                }
                //top = !top;
            } // Left and Right
            else {
                tmpNewFree.setWidth((int) (currentBounds.getWidth() + rect.getWidth()));
                tmpNewFree.setHeight((int) (tmpNewFree.getWidth() / ratio));
                if (left) {
                    //TODO look for bug
                    final int x = (int) (currentBounds.getX() - rect.getWidth());
                    final int y = (int) currentBounds.getY();

                    rect.setX(x);
                    rect.setY(y);
                    tmpNewFree.setX(x);
                    tmpNewFree.setY(y);
                } else {
                    rect.setX((int) (currentBounds.getX() + currentBounds.getWidth()));
                    rect.setY((int) currentBounds.getY());
                }
                //left = !left;
            }
            addPlaced(rect);
            for (final FreeSpaceRectangle freeSpaceRect : tmpNewFree.divideUp(rect)) {
                freeSpaceArea.addAll(freeSpaceRect.divideUp(currentBounds));
                freeSpaceArea.remove(freeSpaceRect);
            }
            currentBounds = tmpNewFree;
        }
    }

    private void addPlaced(final PathRectangle rect) {
        widthSortedSet.remove(rect);
        heightSortedSet.remove(rect);
        placed.add(rect);
        System.out.println(rect);
    }
}
