package cl.uchile.dcc.cc5303;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;



public class Level {
	
	LinkedList<Bench> benches;
	private int id;
	static int staticId = 0;
	
	public Level(int nBench){
		this.id = staticId++;
		Random r = new Random();
		benches = new LinkedList<Bench>();
		Bench b1 = new Bench(r.nextInt(800-100),id*100,100);
		Bench b2 = new Bench(r.nextInt(800-300),id*100,300);
		benches.add(b1);
		benches.add(b2);
	}
	
	public void draw(Graphics g){
		for(Bench b : benches){
			 g.fillRect(b.posX, b.posY, b.w, b.h);
		}
	}
	
	public void moveDown(){
		this.id--;
		for(Bench b : benches){
			b.posY = 600 - id*100 - b.h;
		}
	}
	
	
	

}
