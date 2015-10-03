package cl.uchile.dcc.cc5303.elements;

import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Player extends UnicastRemoteObject implements IPlayer {

    public static int playerCounter= 0;
    static public Color[] playerColors = {Color.red, Color.blue, Color.green, Color.cyan};
    int posX, posY, w = 14, h = 20;
    double speed = 0.4;
    public boolean isStandingUp = false;
    int lives;
    Color color;
    public boolean wantsToMoveRight, wantsToMoveLeft, wantsToJump;

    public Player(int x, int y, int lives) throws RemoteException {
        super();
        this.posX = x;
        this.posY = 600 -  y - this.h;
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

    public void update(int dx){
        this.posY += this.speed*dx;
        this.speed += this.speed < 0.8 ? 0.02: 0;
    }

    public void draw(Graphics g){
        g.fillRect(this.posX, this.posY, this.w, this.h);
    }

    @Override
    public String toString(){
        return "player: position ("+this.posX+","+this.posY+")";
    }

    public boolean isBottomCollidingWith(Bench b){
        return Math.abs(bottom() - b.top()) < 10 && getRight() <= b.right() && getLeft() >= b.left();
    }

    public boolean isTopCollidingWith(Bench b){
        return Math.abs(getTop() - b.bottom()) < 10 && getRight() <= b.right() && getLeft() >= b.left();
    }
    
    public boolean isTopCollidingWith(Player p){
    	return Math.abs(getTop() - p.bottom()) < 5 && Math.abs(getRight() - p.getRight()) < 10 && Math.abs(getLeft() - p.getLeft()) < 10;
    }

    public boolean isBottomCollidingWith(Player p){
        return Math.abs(bottom() - p.getTop()) < 5 && Math.abs(getRight() - p.getRight()) < 10 && Math.abs(getLeft() - p.getLeft()) < 10;
    }

    public boolean isRightCollidingWith(Player p){
    	boolean right = (Math.abs(getRight() - p.getLeft()) < 5);
    	boolean m = Math.abs(getTop() - p.getTop()) < 20;
    	return right && m;
    }

    public boolean isRightCollidingWith(List<Player> otherPlayers) {
        for(Player otherPlayer : otherPlayers) {
            if (isRightCollidingWith(otherPlayer)) return true;
        }

        return false;
    }

    public boolean isLeftCollidingWith(List<Player> otherPlayers) {
        for(Player otherPlayer : otherPlayers) {
            if (isLeftCollidingWith(otherPlayer)) return true;
        }

        return false;
    }

    public boolean isBottomCollidingWith(List<Player> otherPlayers) {
        for(Player otherPlayer : otherPlayers) {
            if (otherPlayer != this && isBottomCollidingWith(otherPlayer)) {
                return true;
            }
        }

        return false;
    }

    public boolean isTopCollidingWith(List<Player> otherPlayers) {
        for(Player otherPlayer : otherPlayers) {
            if (isTopCollidingWith(otherPlayer)) return true;
        }

        return false;
    }
    
    public boolean isLeftCollidingWith(Player p){
    	boolean left = (Math.abs(p.getRight() - getLeft()) < 5);
    	boolean m = Math.abs(getTop() - p.getTop()) < 20;
    	return left && m;
    }

    public boolean isRightCollidingWith(Bench p){
        boolean right = (Math.abs(getRight() - p.left()) < 5);
        boolean m = Math.abs(getTop() - p.top()) < 20;
        return right && m;
    }

    public boolean isLeftCollidingWith(Bench p){
        boolean left = (Math.abs(p.right() - getLeft()) < 5);
        boolean m = Math.abs(getTop() - p.top()) < 20;
        return left && m;
    }

    public int getLeft() {
        return this.posX;
    }

    public int bottom() {
        return this.posY + this.h;
    }

    public int getRight() {
        return this.posX + this.w;
    }

    public int getTop() {
        return posY;
    }
    public int getHeight() {
        return h;
    }
    public int getWidth() {
        return w;
    }

    public void setPosX(int x) {
        this.posX = x;
    }
    public void setPosY(int y) {
        this.posY = y;
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
