package cl.uchile.dcc.cc5303;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebablasko on 9/11/15.
 */
public class Board extends Canvas {

    public int width, height;

    public Player p1, p2;
    public LinkedList<Level> levels;
    public Image img;
    public Graphics buffer;

    public Board(int w, int h){
        this.width = w;
        this.height = h;
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

        buffer.setColor(Color.red);
        p1.draw(buffer);
        buffer.setColor(Color.blue);
        p2.draw(buffer);

        buffer.setColor(Color.white);
        for(Level l : levels){
            l.draw(buffer);
        }

        g.drawImage(img, 0, 0, null);
    }

    @Override
    public String toString(){
        String ret = "Tablero: dimensions " + this.width + "x" + this.height + "\n";
        ret += p1.toString() + "\n" + p2.toString();
        return ret;
    }


    public void levelsDown() {
    	levels.add(new Level(2));
        for(Level l : levels) {
        	l.moveDown();
        
        }
        levels.remove();
        
    }




}
