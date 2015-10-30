package cl.uchile.dcc.cc5303.elements;

import cl.uchile.dcc.cc5303.interfaces.IBench;
import java.rmi.RemoteException;

public class Bench extends GameObject implements IBench {

	public Bench(int x, int y, int width) throws RemoteException {
        super();
        this.w = width;
        this.h = 20;
        this.posX = x;
        this.posY = 600 - y - this.h;
    }
	
	public Bench(int x, int y, int width, int h) throws RemoteException {
        super();
        this.w = width;
        this.h = h;
        this.posX = x;
        this.posY = y;
    }
}
