package cl.uchile.dcc.cc5303.elements;

import cl.uchile.dcc.cc5303.interfaces.IBench;
import cl.uchile.dcc.cc5303.interfaces.ILevel;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Level extends GameObject implements ILevel {

	LinkedList<Bench> benches;
	public int id;
	public static int staticId = 0;
	
	public Level() throws RemoteException {
		super();
		this.id = staticId++;

		benches = new LinkedList<Bench>();
		if(id != 0) {
			Random r = new Random();
			Bench b1 = new Bench(r.nextInt(800-100),id*100,100);
			Bench b2 = new Bench(r.nextInt(800-300),id*100,300);
			benches.add(b1);
			benches.add(b2);
		}
		else {
			benches.add(new Bench(0, 0, 800));
		}

	}
	
	public Level(int id, int staticID, LinkedList<Bench> benches) throws RemoteException {
		super();
		this.id = id;
		staticId = staticID;
		this.benches = benches;

	}

	public void moveDown(){
		this.id--;
		if(id == 3) staticId--;
		for(Bench b : benches){
			b.posY += 100;
		}
	}

	public List<Bench> getLocalBenches() {
		return benches;
	}


	public List<IBench> getBenches() throws RemoteException{

		LinkedList<IBench> ibenches;
		ibenches = new LinkedList<IBench>();
		for(IBench bench : benches) {
			ibenches.add(bench);
		}

		return ibenches;
	}

	@Override
	public int getId() throws RemoteException {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int getStaticId() throws RemoteException {
		// TODO Auto-generated method stub
		return staticId;
	}
	
	

}
