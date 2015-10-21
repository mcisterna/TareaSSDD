package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IBench;
import cl.uchile.dcc.cc5303.interfaces.ILevel;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class Renderer extends Canvas{


	public Image img;
    public Graphics buffer;
    public int width, height;
    public List<IPlayer> players;
    public List<ILevel> levels;
    boolean playing;
    public List<IPlayer> ranking;

    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        playing = true;
    }

    @Override
    public void update(Graphics g) { paint(g); }

    @Override
    public void paint(Graphics g){
    	
	        if(buffer==null){
	            img = createImage(getWidth(),getHeight());
	            buffer = img.getGraphics();
	        }
	
	        buffer.setColor(Color.black);
	        buffer.fillRect(0, 0, getWidth(), getHeight());
	        if(playing){
		        int pos = 30;
		        for(IPlayer player : players) {
		            try {
		                buffer.setColor(player.getColor());
		                buffer.drawString(""+player.getLives(), 780, pos);
		                pos += 13;
		                paint(player);
		            } catch (RemoteException e) {
		                e.printStackTrace();
		            }
		        }
		
		        buffer.setColor(Color.white);
		        for(ILevel l : levels){
		            try {
		                paint(l);
		            } catch (RemoteException e) {
		                e.printStackTrace();
		            }
		        }
		
    	}else{
    		int pos = 200;
    		buffer.setColor(Color.white);
	        buffer.drawString("Ranking",340,pos);
	        pos += 15;
    		for(IPlayer player: ranking){
    			try {
					buffer.setColor(player.getColor());
					buffer.drawString("Player",350,pos);
					pos += 13;
					
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
    			
    		}

    		
    		
    	}
	        g.drawImage(img, 0, 0, null);
    }

    public void paint(IPlayer player) throws RemoteException {
        buffer.fillRect(player.getLeft(), player.getTop(), player.getWidth(), player.getHeight());
    }

    public void paint(ILevel level) throws RemoteException {
        for(IBench b : level.getBenches()){
            buffer.fillRect(b.getLeft(), b.getTop(), b.getWidth(), b.getHeight());
        }
    }
}
