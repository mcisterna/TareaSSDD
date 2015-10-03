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

    public List<Player> players;
    public LinkedList<Level> levels;
    public Color[] playerColors = {Color.red, Color.blue, Color.green, Color.cyan};
    public Image img;
    public Graphics buffer;

    public Board(int w, int h, List players){
        this.width = w;
        this.height = h;
        this.players = players;
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

        for(int i = 0; i<players.size(); i++) {

            buffer.setColor(playerColors[i]);
            players.get(i).draw(buffer);

        }

        buffer.setColor(Color.white);
        for(Level l : levels){
            l.draw(buffer);
        }

        g.drawImage(img, 0, 0, null);
    }

    public void levelsDown() {
        levels.add(new Level(2));
        for(Level l : levels) {
        	l.moveDown();
        }
        levels.remove();
    }




}
