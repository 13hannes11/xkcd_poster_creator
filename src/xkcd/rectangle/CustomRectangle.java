/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xkcd.rectangle;

import java.awt.*;

/**
 * @author Hannes
 */
public abstract class CustomRectangle extends Rectangle {

    public CustomRectangle(final int x, final int y, final int width, final int height) {
        super(x, y, width, height);
    }


    public final void setX(final int x) {
        this.setLocation(x, (int) this.getY());
    }

    public final void setY(final int y) {
        this.setLocation((int) this.getX(), y);
    }

    public final void setWidth(final int width) {
        this.setSize(width, (int) this.getHeight());
    }

    public final void setHeight(final int height) {
        this.setSize((int) this.getWidth(), height);
    }

    public final boolean couldContain(final Rectangle rect) {
        return (rect.getWidth() <= this.getWidth() && rect.getHeight() <= this.getHeight());
    }

    @Override
    public String toString() {
        String str = "";
        str += "X: " + this.getX() + " Y: " + this.getY() + " w: " + this.getWidth() + " h: " + this.getHeight();
        return str;
    }
}
