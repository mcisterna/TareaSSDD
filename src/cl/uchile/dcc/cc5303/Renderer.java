package cl.uchile.dcc.cc5303;


import cl.uchile.dcc.cc5303.elements.Bench;
import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class Renderer extends Canvas{

    public Image img;
    public Graphics buffer;
    public int width, height;
    public List<IPlayer> players;
    public List<Level> levels;

    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(Graphics g) { paint(g); }

    @Override
    public void paint(Graphics g){

        if(buffer==null){
            img = createImage(getWidth(),getHeight() );
            buffer = img.getGraphics();
        }

        buffer.setColor(Color.black);
        buffer.fillRect(0, 0, getWidth(), getHeight());;

        for(IPlayer player : players) {
            try {
                buffer.setColor(player.getColor());
                paint(player);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        buffer.setColor(Color.white);
        for(Level l : levels){
            paint(l);
        }

        buffer.setColor(Color.CYAN);

        g.drawImage(img, 0, 0, null);
    }

    public void paint(IPlayer player) throws RemoteException {
        buffer.fillRect(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
    }

    public void paint(Level level) {
        for(Bench b : level.getBenches()){
            buffer.fillRect(b.getPosX(), b.getPosY(), b.getWidth(), b.getHeight());
        }
    }
}
