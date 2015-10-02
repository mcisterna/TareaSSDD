package cl.uchile.dcc.cc5303;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by franchoco on 9/20/15.
 */
public class MainThread extends Thread {
    public boolean[] keys;
    private final static String TITLE = "Juego - CC5303";
    private final static int WIDTH = 800, HEIGHT = 600;
    private final static int UPDATE_RATE = 60;
    private final static int DX = 5;
    private final static double DV = 0.1;
    private final static int framesToNewBench = 100;
    private final double vy = 0.3;

    private JFrame frame;
    private Board tablero;
    private Player player1, player2;

    int frames = new Random().nextInt(2 * framesToNewBench);
    
    LinkedList<Level> levels;

    private Bench[] benches = {
            new Bench(0, 800, 0),
            new Bench(100, 200, 1),
            new Bench(400, 200, 1),
            new Bench(300, 100, 2),
            new Bench(600, 200, 2),
            new Bench(150, 200, 3),
            new Bench(700, 100, 3),
            new Bench(75, 200, 4),
            new Bench(350, 350, 4),
            new Bench(200, 200, 5),
            new Bench(400, 400, 6),
            new Bench(200, 400, 7),
            new Bench(150, 200, 8),
            new Bench(75, 100, 9),
            new Bench(50, 100, 10)
    };

    public MainThread() {
        keys = new boolean[KeyEvent.KEY_LAST];
        levels = new LinkedList<Level>();
        for (int i = 0; i < 6; i++) {
			Level l = new Level(2);
			levels.add(l);
		}

        //Jugadores
        player1 = new Player(WIDTH/3, 550);
        player2 = new Player(2*WIDTH/3, 550);

        frame = new JFrame(TITLE);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tablero = new Board(WIDTH, HEIGHT);
        tablero.p1 = player1;
        tablero.p2 = player2;
        tablero.bases = benches;

        frame.add(tablero);
        tablero.setSize(WIDTH, HEIGHT);

        frame.pack();
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });

    }

    @Override
    public void run() {
        while (true) { // Main loop
            //Check controls
            if (keys[KeyEvent.VK_UP]) {
            	if(!tablero.p1.topCollide(tablero.p2))
            		tablero.p1.jump();
            }
            if (keys[KeyEvent.VK_RIGHT]) {
            	if(!tablero.p1.rightCollide(tablero.p2))
            		tablero.p1.moveRight();
            }
            if (keys[KeyEvent.VK_LEFT]) {
            	if(!tablero.p1.leftCollide(tablero.p2))
            		tablero.p1.moveLeft();
            }

            if (keys[KeyEvent.VK_W]) {
            	if(!tablero.p2.topCollide(tablero.p1))
            		tablero.p2.jump();
            }
            if (keys[KeyEvent.VK_D]) {
            	if(!tablero.p2.rightCollide(tablero.p1))
            		tablero.p2.moveRight();
            }
            if (keys[KeyEvent.VK_A]) {
            	if(!tablero.p2.leftCollide(tablero.p1))
            		tablero.p2.moveLeft();
            }

            //update players
            tablero.p1.update(DX);
            tablero.p2.update(DX);

            //update barras
            boolean levelsDown = false;
            for (Bench barra : tablero.bases) {
                if (tablero.p1.hit(barra))
                    tablero.p1.speed = 0.8;
                else if (tablero.p1.collide(barra)) {
                    tablero.p1.speed = 0.01;
                    tablero.p1.standUp = true;
                    if (barra.getLevel() == 5){
                        levelsDown = true;
                    }
                }

                if (tablero.p2.hit(barra))
                    tablero.p2.speed = 0.8;
                else if (tablero.p2.collide(barra)) {
                    tablero.p2.speed = 0.01;
                    tablero.p2.standUp = true;
                    if (barra.getLevel() == 5){
                        levelsDown = true;
                    }
                }
            }
            
            if(tablero.p1.topCollide(tablero.p2)){
            	tablero.p2.speed = 0.01;
                tablero.p2.standUp = true;
            }
            
            if(tablero.p2.topCollide(tablero.p1)){
            	tablero.p1.speed = 0.01;
                tablero.p1.standUp = true;
            }
            

            // Update board
            if (levelsDown) {
                tablero.levelsDown();
                tablero.p1.posY -= 25;
                tablero.p2.posY += 5;
            }

            tablero.repaint();

            try {
                this.sleep(1000 / UPDATE_RATE);
            } catch (InterruptedException ex) {

            }
        }


    }
}