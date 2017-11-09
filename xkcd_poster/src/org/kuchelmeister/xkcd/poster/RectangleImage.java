/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuchelmeister.xkcd.poster;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Hannes
 */
public class RectangleImage {
    Rectangle r;

    public RectangleImage(BufferedImage image, int x, int y) {
        r = new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
    public RectangleImage(Rectangle rec){
        r = rec;
    }

    public int getHeight() {
        return r.height;
    }

    public int getWidth() {
        return r.width;
    }

    public int getBoundY() {
        return r.y + r.height;
    }

    public int getBoundX() {
        return r.x + r.width;
    }

    public int getX() {
        return r.x;
    }

    public int getY() {
        return r.y;
    }

    public Rectangle getRectangle() {
        return r;
    }
    public void setY(int y){
        r.y = y;
    }
    public boolean intersects(RectangleImage image) {
        return r.intersects(image.getRectangle());
    }
}
