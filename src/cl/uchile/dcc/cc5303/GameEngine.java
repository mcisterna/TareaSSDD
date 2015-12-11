package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.elements.Bench;
import cl.uchile.dcc.cc5303.elements.Level;
import cl.uchile.dcc.cc5303.elements.Player;

import java.rmi.RemoteException;
import java.util.List;

public class GameEngine {

    private final static int UPDATE_RATE = 60;
    private final static int DX = 5;
    private List<Player> players;
    private Game game;
    private List<Level> levels;
    private List<Player> ranking;
    private boolean running;

    public GameEngine(Game game) throws RemoteException {
        this.players = game.getLocalPlayers();
        this.levels = game.getLocalLevels();
        this.ranking = game.getLocalRanking();
        this.game = game;
        running = true;
    }

    public void stop() {
        running = false;
    }
    
    public boolean isRunning(){
    	return running;
    }

    public void runGame() throws RemoteException {

        while ((ranking.size() == 0 || players.size() != 1) && running) { // main loop
            while (!game.isPaused()) {
                for (int j = 0; j < players.size(); j++) {
                    Player player = players.get(j);

                    if (player.wantsToJump && !player.isTopCollidingWith(players)) player.jump();
                    if (player.wantsToMoveRight &&
                            !player.isRightCollidingWith(players) &&
                            !player.isRightCollidingBenches(levels)) player.moveRight();
                    if (player.wantsToMoveLeft &&
                            !player.isLeftCollidingWith(players) &&
                            !player.isLeftCollidingBenches(levels)) player.moveLeft();

                    if (player.isRightCollidingWith(players)) {
                        for (int i = 0; i < 10; i++)
                            player.moveLeft();

                    }

                    if (player.isLeftCollidingWith(players)) {
                        for (int i = 0; i < 10; i++)
                            player.moveRight();
                    }


                    if (player.getTop() > game.levels.getFirst().getBenches().get(0).getBottom()) {
                        player.looseLife();
                        if (player.getLives() != 0) {
                            player.setPosY(220);
                            player.setPosX(400);
                        } else {
                            players.remove(player);
                            j--;
                            ranking.add(0, player);
                        }
                    }
                }
                game.exit();

                for (Player player : players)
                    player.update(DX);

                boolean needNextLevel = false;
                for (Level l : levels) {
                    for (Bench barra : l.getLocalBenches()) {
                        for (Player player : players) {
                            if (player.isTopCollidingWith(barra))
                                player.setSpeed(0.8);
                            else if (player.isBottomCollidingWith(barra)) {
                                player.setSpeed(0.01);
                                player.isStandingUp = true;
                                if (l.id == 4) {
                                    needNextLevel = true;
                                }
                            }
                            if (player.isBottomCollidingWith(players)) {
                                player.setSpeed(0.01);
                                player.isStandingUp = true;
                            }
                        }

                    }
                }

                if (needNextLevel) game.moveStageDown();

                try {
                    Thread.sleep(1000 / UPDATE_RATE);
                } catch (InterruptedException ex) {

                }

            }
        }
        
        if(running) {
            ranking.add(0, players.get(0));
            players.remove(players.get(0));
        }
    }
}
