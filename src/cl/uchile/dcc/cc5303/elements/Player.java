package cl.uchile.dcc.cc5303.elements;

import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;

public class Player extends GameObject implements IPlayer {

    public static int playerCounter= 0;
    static public Color[] playerColors = {Color.red, Color.blue, Color.green, Color.cyan};
    double speed = 0.4;
    public boolean isStandingUp = false;
    int lives;
    Color color;
    public boolean wantsToMoveRight, wantsToMoveLeft, wantsToJump;

    public Player(int x, int y, int lives) throws RemoteException {
        super();
        this.posX = x;
        this.posY = 600 -  y - this.h;
        this.h = 20;
        this.w = 14;
        this.lives = lives;
        this.color = playerColors[playerCounter];
        playerCounter++;
        wantsToMoveLeft = false;
        wantsToMoveRight = false;
        wantsToJump = false;
    }

    public void jump(){
        if(this.isStandingUp)
            this.speed = -0.9;
        this.isStandingUp = false;
    }

    public void moveRight() {
        this.posX += 2;
    }

    public void moveLeft() {
        this.posX -= 2;
    }

    @Override
    public void startJumping() throws RemoteException {
        wantsToJump = true;
    }

    @Override
    public void startMovingRight() throws RemoteException {
        wantsToMoveRight = true;
    }

    @Override
    public void startMovingLeft() throws RemoteException {
        wantsToMoveLeft = true;
    }

    @Override
    public void stopJumping() throws RemoteException {
        wantsToJump = false;
    }

    @Override
    public void stopMovingRight() throws RemoteException {
        wantsToMoveRight = false;
    }

    @Override
    public void stopMovingLeft() throws RemoteException {
        wantsToMoveLeft = false;
    }

    public Color getColor() throws RemoteException {
        return color;
    }

    public boolean isRightCollidingBenches(List<Level> levels) {
        for(Level level : levels) {
            if (isRightCollidingWith(level.getLocalBenches())) return true;
        }

        return false;
    }

    public boolean isLeftCollidingBenches(List<Level> levels) {
        for(Level level : levels) {
            if (isLeftCollidingWith(level.getLocalBenches())) return true;
        }

        return false;
    }

    public void update(int dx){
        this.posY += this.speed*dx;
        this.speed += this.speed < 0.8 ? 0.02: 0;
    }

    @Override
    public String toString(){
        return "player: position ("+this.posX+","+this.posY+")";
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getLives() {
        return lives;
    }

    public void  looseLife() {
        lives--;
    }
}
