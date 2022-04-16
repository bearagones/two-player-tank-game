/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;
import tankgame.gameobjects.powerup.*;
import tankgame.gameobjects.wall.BreakableWall;
import tankgame.gameobjects.wall.UnbreakableWall;
import tankgame.gameobjects.wall.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class GameLoader extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank tank1;
    private Tank tank2;
    private Wall wall;
    private GameLauncher lf;
    private BufferedImage background = null;
    String[] mapinfo;
    ArrayList<Wall> walls;
    ArrayList<PowerUp> powerups;

    public GameLoader(GameLauncher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tank1.update(); // update tank
                this.tank2.update();
                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(tank1.lives == 0 || tank2.lives == 0){
                    this.lf.setFrame("end");
                    return;
                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tank1.setX(300);
        this.tank1.setY(300);
        this.tank2.setX(350);
        this.tank2.setY(350);
    }


    /**
     * Load all resources for tankgame.gameobjects.Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {

        BufferedImage t1img = null;
        BufferedImage t2img = null;

        walls = new ArrayList<>();

        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        try {
            /*                  
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            background = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("Background.bmp")));
            t1img = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("BlueTankP1.png")));
            t2img = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("RedTankP2.png")));
            BufferedImage breakableWall = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("BreakableWall.gif")));
            BufferedImage unbreakableWall = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("UnbreakableWall.gif")));
            BufferedImage armorPowerUp = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/Armor.png")));
            BufferedImage criticalDamagePowerUp = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/CriticalDamage.gif")));
            BufferedImage fireRatePowerUp = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/FireRate.gif")));
            BufferedImage healthPowerUp = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/Health.png")));
            BufferedImage speedPowerUp = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/Speed.png")));

            InputStreamReader isr = new InputStreamReader(GameLoader.class.getClassLoader().getResourceAsStream("maps/map1.csv"));
            BufferedReader mapReader = new BufferedReader(isr);
            String row = mapReader.readLine();
            if (row == null) {
                throw new IOException("no data in the file");
            }

            String[] mapInfo = row.split(",");
            int numberColumns = Integer.parseInt(mapInfo[0]);
            int numberRows = Integer.parseInt(mapInfo[1]);

            for (int currentRow = 0; currentRow < numberRows; currentRow++) {
                row = mapReader.readLine();
                mapInfo = row.split(",");
                for (int currentColumn = 0; currentColumn < numberColumns; currentColumn++) {
                    switch(mapInfo[currentColumn]) {
                        case "0":
                            break;
                        case "1":
                            this.walls.add(new BreakableWall(currentColumn*32, currentRow*32, breakableWall));
                            break;
                        case "2":
                        case "8":
                            this.walls.add(new UnbreakableWall(currentColumn*32, currentRow*32, unbreakableWall));
                            break;
                        case "3":
                            this.powerups.add(new ArmorPowerUp(currentColumn*32, currentRow*32, armorPowerUp));
                            break;
                        case "4":
                            this.powerups.add(new CriticalDamagePowerUp(currentColumn*32, currentRow*32, criticalDamagePowerUp));
                            break;
                        case "5":
                            this.powerups.add(new FireRatePowerUp(currentColumn*32, currentRow*32, fireRatePowerUp));
                            break;
                        case "6":
                            this.powerups.add(new HealthPowerUp(currentColumn*32, currentRow*32, healthPowerUp));
                            break;
                        case "7":
                            this.powerups.add(new SpeedPowerUp(currentColumn*32, currentRow*32, speedPowerUp));
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //Spawning the tanks
        tank1 = new Tank(300, 300, 0, 0, 0, t1img);
        tank2 = new Tank(350, 350, 0, 0, 0, t2img);

        //Setting the game controls for the tanks
        GameControl tankControl1 = new GameControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        GameControl tankControl2 = new GameControl(tank2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_CONTROL);

        //Adding a key listener for the tank controls
        this.lf.getJf().addKeyListener(tankControl1);
        this.lf.getJf().addKeyListener(tankControl2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        //Drawing the background
        for (int i = 0; i < GameConstants.GAME_SCREEN_WIDTH; i += this.background.getWidth()) {
            for (int j = 0; j < GameConstants.GAME_SCREEN_HEIGHT; j += this.background.getHeight()) {
                buffer.drawImage(this.background, i, j, null);
            }
        }

        //Drawing the walls
        this.walls.forEach(wall -> wall.drawImage(buffer));

        //Drawing the tanks
        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);

        //Drawing the game screen
        g2.drawImage(world,0,0,null);
    }

}
