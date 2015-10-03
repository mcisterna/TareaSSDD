package cl.uchile.dcc.cc5303.elements;

import java.io.Serializable;

public class Bench implements Serializable{
    int posX, posY;
    int w, h;

    public Bench(int x, int y, int width){
        this.w = width;
        this.h = 20;
        this.posX = x;
        this.posY = 600 - y - this.h;
    }

    public int top() {
        return this.posY;
    }

    public int left() {
        return this.posX;
    }

    public int bottom() {
        return this.posY + this.h;
    }

    public int right() {
        return this.posX + this.w;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

}
