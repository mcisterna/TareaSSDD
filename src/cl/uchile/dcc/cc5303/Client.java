package cl.uchile.dcc.cc5303;

import cl.uchile.dcc.cc5303.interfaces.IGame;
import cl.uchile.dcc.cc5303.interfaces.IPlayer;
import cl.uchile.dcc.cc5303.interfaces.IServer;
import cl.uchile.dcc.cc5303.interfaces.IServersManager;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

	private final static String TITLE = "Juego - CC5303";
	private final static int WIDTH = 800, HEIGHT = 600;


	static public IServer getCurrentServer(IServersManager serversManager) throws RemoteException, MalformedURLException, NotBoundException {
		String gameServerIp = serversManager.getCurrentServerIp();
		String gameServerUrl = "rmi://" + gameServerIp + ":1099/game_server";
		IServer gameServer = (IServer)Naming.lookup(gameServerUrl);
		return gameServer;
	}

	static public void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
		System.out.println("Starting client...");
		final boolean[] keys = new boolean[KeyEvent.KEY_LAST];
		if (args.length == 0) {
			System.out.println("Debe ingresar ip");
			return;
		}
		String serversManagerIp = args[0];
		String serversManagerUrl = "rmi://" + serversManagerIp + ":1099/servers_manager";
		IServersManager serversManager = (IServersManager) Naming.lookup(serversManagerUrl);

		IServer gameServer = getCurrentServer(serversManager);
		IPlayer player = gameServer.joinGame();


		if (player == null) {
			System.out.println("Game is full");
		} else {
			JFrame frame = new JFrame(TITLE);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Renderer renderer = new Renderer(WIDTH, HEIGHT);//width y height del game engine
			renderer.setSize(WIDTH, HEIGHT);

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

			int id = player.getId();
			IGame game;
			while (true) {
				gameServer = getCurrentServer(serversManager);
				game = gameServer.getGame();
				while (game.getPlayers().size() > 0) {
					player = game.getPlayerById(id);
					if(player != null){
						if (keys[KeyEvent.VK_UP]) {
							player.startJumping();
						} else {
							player.stopJumping();
						}
						if (keys[KeyEvent.VK_RIGHT]) {
							player.startMovingRight();
						} else {
							player.stopMovingRight();
						}
						if (keys[KeyEvent.VK_LEFT]) {
							player.startMovingLeft();
						} else {
							player.stopMovingLeft();
						}
						if (keys[KeyEvent.VK_Q]) {
							player.stopPlaying();
							System.exit(0);
						}
						if(keys[KeyEvent.VK_P]) {
							game.togglePause();
							try {
								Thread.sleep(1000);//UPDATE RATE DEL game engine
							} catch (InterruptedException ex) {

							}
						}
					}
					renderer.players = game.getPlayers();
					renderer.levels = game.getLevels();
					renderer.repaint();
					try {
						Thread.sleep(1000 / 60);//UPDATE RATE DEL game engine
					} catch (InterruptedException ex) {

					}

					frame.requestFocus();
					game = gameServer.getGame();
					gameServer = getCurrentServer(serversManager);

				}
				//TODO: Aca todavia hay un problema, si uno apreta enter rapidamente, me va a mostrar el mapa
				//viejo...ya que todavia no se crea el new game, se va a ir rapidamente al ranking denuevo, pero
				//me lo va a mostrar vacio.
				renderer.playing = false;
				renderer.ranking = game.getRanking();
				gameServer.playerIsReadyToContinue();

				while (!renderer.playing) {
					renderer.repaint();
					try {
						Thread.sleep(1000 / 60);
					} catch (InterruptedException ex) {

					}
					frame.requestFocus();

					if (keys[KeyEvent.VK_ENTER]) {
						renderer.playing = true;
					}
				}

				player = gameServer.joinGame();
				id = player.getId();

			}

		}
	}
}

