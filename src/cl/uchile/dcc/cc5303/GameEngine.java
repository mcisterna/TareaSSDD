package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.elements.Bench;
import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;
import cl.uchile.dcc.cc5303.interfaces.IServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class GameEngine {

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

        LinkedList<Level> levels;
        levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
            Level l = new Level(2);
            levels.add(l);
        }

        Game game = new Game(players);
        game.levels = levels;

        IServer gameEngine = new Server(players, todosJuntos, game);
        Naming.rebind(url, gameEngine);

        while (true) { // main loop
            for (Player player1 : players) {
                for (Player player2 : players){
                    if (player1.jumping) {
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

                if (player1.getPosY() > game.levels.getFirst().getBenches().getFirst().bottom()) {
                    player1.looseLife();;
                    if (player1.getLives() != 0) {
                        player1.setPosY(0);
                        player1.setPosX(400);
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
                for (Bench barra : l.getBenches()) {
                    for (Player player : players) {
                        if (player.topCollide(barra))
                            player.setSpeed(0.8);
                        else if (player.bottomCollide(barra)) {
                            player.setSpeed(0.01);
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
                            player2.setSpeed(0.01);
                            player2.standUp = true;
                        }
                    }
                }
            }

            // Update board
            if (levelsDown) {
                game.levelsDown();
                for (Player player : players) {
                    player.setPosY(player.getPosY() + 100);
                }
            }

            try {
                Thread.sleep(1000 / UPDATE_RATE);
            } catch (InterruptedException ex) {

            }
        }


    }
}
