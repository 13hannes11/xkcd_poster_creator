package xkcd.rectangle.freespace;

import xkcd.rectangle.CustomRectangle;

import java.util.Collection;

public class LeftRightSplitFreeSpaceRectangle extends FreeSpaceRectangle {

    public LeftRightSplitFreeSpaceRectangle(final int x, final int y, final int width, final int height) {
        super(x, y, width, height);
    }

    @Override
    public Collection<FreeSpaceRectangle> divideUp(final CustomRectangle r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
