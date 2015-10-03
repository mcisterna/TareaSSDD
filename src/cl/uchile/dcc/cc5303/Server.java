package cl.uchile.dcc.cc5303;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.LinkedList;

/**
 * Created by jose on 10/2/15.
 */
public class Server {

    private final static String TITLE = "Juego - CC5303";
    private final static int WIDTH = 800, HEIGHT = 600;
    private final static int UPDATE_RATE = 60;
    private final static int DX = 5;
    static boolean todosJuntos = false;
    static LinkedList<Player> players;
    public static String url = "rmi://localhost:1099/game";

    public static void main(String[] args) throws MalformedURLException, RemoteException {

        System.out.println("Iniciando Juego de SSDD...");
        players = new LinkedList<Player>();

        IGameEngine gameEngine = new GameEngine(players, todosJuntos);
        Naming.rebind(url, gameEngine);

        JFrame frame;
        Board tablero;


        LinkedList<Level> levels;

        levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
            Level l = new Level(2);
            levels.add(l);
        }

        frame = new JFrame(TITLE);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tablero = new Board(WIDTH, HEIGHT, players);
        tablero.levels = levels;

        frame.add(tablero);
        tablero.setSize(WIDTH, HEIGHT);

        while (true) { // main loop
            System.out.println("loop");
            for (Player player1 : players) {

                for (Player player2 : players)
                    if (player1 != player2) {
                        if (player1.jumping) {
                            System.out.println("jumping");
                            if (!player1.topCollide(player2))
                                player1.jump();
                        }
                        if (player1.movingRight) {
                            if (!player1.rightCollide(player2))
                                player1.moveRight();
                        }
                        if (player1.movingLeft) {
                            if (!player1.leftCollide(player2))
                                player1.moveLeft();
                        }
                    }

                if (player1.posY > tablero.levels.getFirst().benches.getFirst().bottom()) {
                    player1.lifes--;
                    if (player1.lifes != 0) {
                        player1.posY = 0;
                        player1.posX = 400;
                    } else {
                        //TODO: sacar player de la lista de players
                    }
                }

                for (Player player : players)
                    player.update(DX);

            }

            //update barras
            boolean levelsDown = false;
            for (Level l : levels) {
                for (Bench barra : l.benches) {
                    for (Player player : players) {
                        if (player.topCollide(barra))
                            player.speed = 0.8;
                        else if (player.bottomCollide(barra)) {
                            player.speed = 0.01;
                            player.standUp = true;
                            if (l.id >= 4) {
                                levelsDown = true;
                            }
                        }
                    }

                }
            }

            for (Player player1 : players) {
                for (Player player2 : players) {
                    if (player1 != player2) {
                        if (player1.topCollide(player2)) {
                            player2.speed = 0.01;
                            player2.standUp = true;
                        }
                    }
                }
            }

            // Update board
            if (levelsDown) {
                tablero.levelsDown();
                for (Player player : players) {
                    player.posY += 100;
                }
            }

            tablero.repaint();


            try {
                Thread.sleep(1000 / UPDATE_RATE);
            } catch (InterruptedException ex) {

            }
        }


    }
}
