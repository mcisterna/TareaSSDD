package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    static public void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        boolean[] keys = new boolean[KeyEvent.KEY_LAST];

        IServer gameEngine = (IServer) Naming.lookup(GameEngine.url);
        IPlayer player  = gameEngine.joinGame();

        if(player == null) {
            System.out.println("Game is full (4 players max)");
        }
        else {
            IGame game = gameEngine.getGame();



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


            while (true) {

                if (keys[KeyEvent.VK_UP]) {
                    System.out.println("jump!");
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
        }
    }
}
