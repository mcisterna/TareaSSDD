package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    static public void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
    //    boolean[] keys;
    //    keys = new boolean[KeyEvent.KEY_LAST];

        IServer gameEngine = (IServer) Naming.lookup(GameEngine.url);
        IPlayer player  = gameEngine.joinGame();
        IGame game = gameEngine.getGame();
        player.startJumping();

        Renderer renderer = new Renderer(800, 600);//width y height del game engine


        JFrame frame = new JFrame("CLIENT");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(renderer);

        renderer.setSize(800, 600);

        while(true) {
            renderer.players = game.getPlayers();
            System.out.println(renderer.players.size());
            renderer.levels = game.getLevels();
            System.out.println(renderer.levels.size());
            renderer.repaint();
            try {
                Thread.sleep(1000 / 60);//UPDATE RATE DEL game engine
            } catch (InterruptedException ex) {

            }

        }
    }
}
