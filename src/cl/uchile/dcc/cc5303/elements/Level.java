package cl.uchile.dcc.cc5303.elements;
import cl.uchile.dcc.cc5303.elements.Bench;

import java.rmi.Remote;
import java.util.LinkedList;
import java.util.Random;

public class Level implements Remote {
	
	LinkedList<Bench> benches;
	public int id;
	static int staticId = 0;
	
	public Level(int nBench){
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

	public void moveDown(){
		this.id--;
		if(id == 3) staticId--;
		for(Bench b : benches){
			b.posY += 100;
		}
	}
	
	
	

}