package cl.uchile.dcc.cc5303;

import java.awt.*;

/**
 * Created by sebablasko on 9/11/15.
 */
public class Player {

    int posX, posY, w = 14, h = 20;
    double speed = 0.4;
    public boolean standUp = false;

    public Player(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public void jump(){
        if(this.standUp)
            this.speed = -0.9;
        this.standUp = false;
    }

    public void moveRight() {
        this.posX += 2;
    }

    public void moveLeft() {
        this.posX -= 2;
    }

    public void update(int dx){
        this.posY += this.speed*dx;
        this.speed += this.speed < 0.8 ? 0.02: 0;
        this.standUp = (this.speed == 0);
    }

    public void draw(Graphics g){
        g.fillRect(this.posX, this.posY, this.w, this.h);
    }

    @Override
    public String toString(){
        return "player: position ("+this.posX+","+this.posY+")";
    }

    public boolean collide(Bench b){
        return Math.abs(bottom() - b.top()) < 10 && right() <= b.right() && left() >= b.left();
    }

    public boolean hit(Bench b){
        return Math.abs(top() - b.bottom()) < 10 && right() <= b.right() && left() >= b.left();
    }
    
    public boolean topCollide(Player p){
    	return Math.abs(top() - p.bottom()) < 5 && Math.abs(right() - p.right()) < 10 && Math.abs(left() - p.left()) < 10;
    }
    
    public boolean bottomCollide(Player p){
    	return Math.abs(p.top() - bottom()) < 5 && Math.abs(right() - p.right()) < 10 && Math.abs(left() - p.left()) < 10;
    }
    
    public boolean rightCollide(Player p){
    	boolean right = (Math.abs(right() - p.left()) < 5);
    	boolean m = Math.abs(top() - p.top()) < 20;
    	return right && m;
    }
    
    public boolean leftCollide(Player p){
    	boolean left = (Math.abs(p.right() - left()) < 5);
    	boolean m = Math.abs(top() - p.top()) < 20;
    	return left && m;
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
}