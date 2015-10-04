package cl.uchile.dcc.cc5303.elements;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public abstract class GameObject extends UnicastRemoteObject{

    int posX, posY, w, h;

    protected GameObject() throws RemoteException {
        super();
    }

    public boolean isBottomCollidingWith(GameObject gameObject) {
        return Math.abs(getBottom() - gameObject.getTop()) < 5 &&
                getRight() - gameObject.getRight() < 15 &&
                gameObject.getLeft() - getLeft() < 15;
    }

    public boolean isTopCollidingWith(GameObject gameObject) {
        return Math.abs(getTop() - gameObject.getBottom()) < 5 &&
                getRight() - gameObject.getRight() < 15 &&
                gameObject.getLeft() - getLeft() < 15;
    }

    public boolean isRightCollidingWith(GameObject gameObject) {
        boolean right = (Math.abs(getRight() - gameObject.getLeft()) < 5);
        boolean m = Math.abs(getTop() - gameObject.getTop()) < 20;
        return right && m;
    }

    public boolean isLeftCollidingWith(GameObject gameObject){
        boolean left = (Math.abs(gameObject.getRight() - getLeft()) < 5);
        boolean m = Math.abs(getTop() - gameObject.getTop()) < 20;
        return left && m;
    }

    public boolean isRightCollidingWith(List<? extends GameObject> otherGameObjects) {
        for(GameObject otherGameObject : otherGameObjects) {
            if (isRightCollidingWith(otherGameObject)) return true;
        }

        return false;
    }

    public boolean isLeftCollidingWith(List<? extends GameObject> otherGameObjects) {
        for(GameObject otherGameObject : otherGameObjects) {
            if (isLeftCollidingWith(otherGameObject)) return true;
        }

        return false;
    }

    public boolean isBottomCollidingWith(List<? extends GameObject> otherGameObjects) {
        for(GameObject otherGameObject : otherGameObjects) {
            if (isBottomCollidingWith(otherGameObject)) return true;
        }

        return false;
    }

    public boolean isTopCollidingWith(List<? extends GameObject> otherGameObjects) {
        for(GameObject otherGameObject : otherGameObjects) {
            if (isTopCollidingWith(otherGameObject)) return true;
        }

        return false;
    }


    public void setPosX(int x) {
        this.posX = x;
    }
    public void setPosY(int y) {
        this.posY = y;
    }

    public int getLeft() {
        return this.posX;
    }
    public int getBottom() {
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

}


