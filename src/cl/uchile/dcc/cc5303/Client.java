package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    static public void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        final boolean[] keys = new boolean[KeyEvent.KEY_LAST];
        boolean playing = true;

        IServer server = (IServer) Naming.lookup(Server.url);
        IPlayer player  = server.joinGame();
        Color color = player.getColor();

        if(player == null) {
            System.out.println("Game is full");
        }
        else {
            IGame game = server.getGame();

            JFrame frame = new JFrame("CLIENT");
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Renderer renderer = new Renderer(800, 600);//width y height del game engine
            renderer.setSize(800, 600);

            frame.add(renderer);
            frame.pack();
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    keys[e.getKeyCode()] = true;
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    keys[e.getKeyCode()] = false;
                }
            });

            while(true){
            	renderer.playing = true;
	            while (game.getPlayers().size()>0) {
	
	                if (keys[KeyEvent.VK_UP]) {
	                    player.startJumping();
	                }
	                else {
	                    player.stopJumping();
	                }
	                if (keys[KeyEvent.VK_RIGHT]) {
	                    player.startMovingRight();
	                }
	                else {
	                    player.stopMovingRight();
	                }
	                if (keys[KeyEvent.VK_LEFT]) {
	                    player.startMovingLeft();
	                }
	                else {
	                    player.stopMovingLeft();
	                }
	                renderer.players = game.getPlayers();
	                renderer.levels = game.getLevels();
	                renderer.repaint();
	                try {
	                    Thread.sleep(1000 / 60);//UPDATE RATE DEL game engine
	                } catch (InterruptedException ex) {
	
	                }
	
	                frame.requestFocus();
	            }
	            renderer.playing = false;
	            renderer.ranking = game.getRanking();
	            
	            while(!keys[KeyEvent.VK_ENTER]){
	            	renderer.repaint();
	            	 try {
		                    Thread.sleep(1000 / 60);//UPDATE RATE DEL game engine
		                } catch (InterruptedException ex) {
		
		                }
	            	 frame.requestFocus();
	            }
	            while(game.equals(server.getGame())){
	            	try {
	                    Thread.sleep(1000);//UPDATE RATE DEL game engine
	                } catch (InterruptedException ex) {
	
	                }
	            }
	            game = server.getGame();
	            player = server.joinGame();
	            player.setColor(color);
	            
            }
            
        }
    }
}
