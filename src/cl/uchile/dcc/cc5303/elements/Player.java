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
    public int lives;
    public int id;
    
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
        playerCounter = (playerCounter + 1) % 4;
        id = playerCounter;
        wantsToMoveLeft = false;
        wantsToMoveRight = false;
        wantsToJump = false;
    }
    
    public Player(int posX, int posY, int h, int w, int lives, int playerCounter, Color color) throws RemoteException {
        super();
        this.posX = posX;
        this.posY = posY;
        this.h = h;
        this.w = w;
        this.lives = lives;
        this.color = color;

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

	@Override
	public void setColor(Color color) throws RemoteException {
		this.color = color;
		
	}
	
	@Override
	public int getPlayerCounter() {
		return playerCounter;
	}

	@Override
	public void setPlayerCounter(int playerCounter) {
		Player.playerCounter = playerCounter;
	}

	@Override
	public boolean isStandingUp() {
		return isStandingUp;
	}

	@Override
	public void setStandingUp(boolean isStandingUp) {
		this.isStandingUp = isStandingUp;
	}

	@Override
	public boolean isWantsToMoveRight() {
		return wantsToMoveRight;
	}

	@Override
	public void setWantsToMoveRight(boolean wantsToMoveRight) {
		this.wantsToMoveRight = wantsToMoveRight;
	}

	@Override
	public boolean isWantsToMoveLeft() {
		return wantsToMoveLeft;
	}

	@Override
	public void setWantsToMoveLeft(boolean wantsToMoveLeft) {
		this.wantsToMoveLeft = wantsToMoveLeft;
	}

	@Override
	public boolean isWantsToJump() {
		return wantsToJump;
	}

	@Override
	public void setWantsToJump(boolean wantsToJump) {
		this.wantsToJump = wantsToJump;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public void setLives(int lives) {
		this.lives = lives;
	}

	@Override
	public Color getColor() throws RemoteException {
		return this.color;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
