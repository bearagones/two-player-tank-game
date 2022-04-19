/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import tankgame.gameobjects.Bullet;
import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;
import tankgame.gameobjects.powerup.*;
import tankgame.gameobjects.wall.BreakableWall;
import tankgame.gameobjects.wall.UnbreakableWall;
import tankgame.gameobjects.wall.Wall;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 * @author anthony-pc
 */
public class GameLoader extends JPanel implements Runnable {

    private static boolean gameOver = false;
    private BufferedImage world;
    public static BufferedImage bullet;
    private Tank tank1;
    private Tank tank2;
    private GameLauncher lf;
    private BufferedImage background = null;

    ArrayList<Wall> walls;
    ArrayList<PowerUp> powerups;
    HashMap<String, BufferedImage> gameObjects;

    public GameLoader(GameLauncher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (!gameOver) {
                this.tank1.update();
                this.tank2.update();

                this.tank1.detectCollision(tank2);
                this.tank2.detectCollision(tank1);

                this.handleCollision(tank1);
                this.handleCollision(tank2);

                if (tank1.lives == 0 || tank2.lives == 0) {
                    gameOver = true;
                }

                this.repaint();

                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
            }
            this.lf.setFrame("end");
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tank1.setX(50);
        this.tank1.setY(50);
        this.tank2.setX(GameConstants.GAME_SCREEN_WIDTH - 100);
        this.tank2.setY(GameConstants.GAME_SCREEN_HEIGHT - 100);
    }

    /**
     * Load all resources for tankgame.gameobjects.Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {

        BufferedImage t1img = null;
        BufferedImage t2img = null;

        walls = new ArrayList<>();
        powerups = new ArrayList<>();
        gameObjects = new HashMap<>();

        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT+100,
                BufferedImage.TYPE_INT_RGB);

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            background = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("Background.png")));
            t1img = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("Tank1.png")));
            t2img = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("Tank2.png")));
            bullet = read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("Bullet.gif")));

            gameObjects.put("breakable wall", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("BreakableWall.png"))));
            gameObjects.put("unbreakable wall", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("UnbreakableWall.png"))));
            gameObjects.put("armor powerup", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/Armor.gif"))));
            gameObjects.put("critical damage powerup", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/CriticalDamage.gif"))));
            gameObjects.put("fire rate powerup", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/FireRate.gif"))));
            gameObjects.put("health powerup", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/Health.png"))));
            gameObjects.put("speed powerup", read(Objects.requireNonNull(GameLoader.class.getClassLoader().getResource("PowerUpSprites/Speed.png"))));

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
                    switch (mapInfo[currentColumn]) {
                        case "0":
                            break;
                        case "1":
                            this.walls.add(new BreakableWall(currentColumn * 32, currentRow * 32, gameObjects.get("breakable wall")));
                            break;
                        case "2":
                        case "8":
                            this.walls.add(new UnbreakableWall(currentColumn * 32, currentRow * 32, gameObjects.get("unbreakable wall")));
                            break;
                        case "3":
                            this.powerups.add(new ArmorPowerUp(currentColumn * 32, currentRow * 32, gameObjects.get("armor powerup")));
                            break;
                        case "4":
                            this.powerups.add(new CriticalDamagePowerUp(currentColumn * 32, currentRow * 32, gameObjects.get("critical damage powerup")));
                            break;
                        case "5":
                            this.powerups.add(new FireRatePowerUp(currentColumn * 32, currentRow * 32, gameObjects.get("fire rate powerup")));
                            break;
                        case "6":
                            this.powerups.add(new HealthPowerUp(currentColumn * 32, currentRow * 32, gameObjects.get("health powerup")));
                            break;
                        case "7":
                            this.powerups.add(new SpeedPowerUp(currentColumn * 32, currentRow * 32, gameObjects.get("speed powerup")));
                            break;
                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //Spawning the tanks
        tank1 = new Tank(200, 200, 0, 0, 90, t1img);
        tank2 = new Tank(GameConstants.GAME_SCREEN_WIDTH - 100, GameConstants.GAME_SCREEN_HEIGHT - 100, 0, 0, 270, t2img);

        //Setting the game controls for the tanks
        GameControl tankControl1 = new GameControl(tank1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        GameControl tankControl2 = new GameControl(tank2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_CONTROL);

        //Adding a key listener for the tank controls
        this.lf.getJf().addKeyListener(tankControl1);
        this.lf.getJf().addKeyListener(tankControl2);
    }

    private int getXCoord(Tank tank) {
        int x = tank.getX();
        if (x < GameConstants.GAME_SCREEN_WIDTH / 4) {
            x = GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        if (x > world.getWidth() - GameConstants.GAME_SCREEN_WIDTH / 4) {
            x = world.getWidth() - GameConstants.GAME_SCREEN_WIDTH / 4;
        }
        return x;
    }

    private int getYCoord(Tank tank) {
        int y = tank.getY();
        if (y < GameConstants.GAME_SCREEN_HEIGHT / 2) {
            y = GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        if (y > world.getHeight() - GameConstants.GAME_SCREEN_HEIGHT / 2) {
            y = world.getHeight() - GameConstants.GAME_SCREEN_HEIGHT / 2;
        }
        return y;
    }

    private void handleCollision(Collidable object) {
        for (Iterator<Wall> wallIterator = walls.iterator(); wallIterator.hasNext();) {
            Wall wall = wallIterator.next();
            object.detectCollision(wall);
            wall.detectCollision(object);
            if (wall.hasCollided() && wall.isBreakable()) {
                wallIterator.remove();
            }
        }

        for (Iterator<PowerUp> powerUpIterator = powerups.iterator(); powerUpIterator.hasNext();) {
            PowerUp powerup = powerUpIterator.next();
            object.detectCollision(powerup);
            powerup.detectCollision(object);
            if (powerup.hasCollided()) {
                powerup.giveBuff((Tank) object);
                powerUpIterator.remove();
            }
        }
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

        //Drawing the powerups
        this.powerups.forEach(powerup -> powerup.drawImage(buffer));

        //Drawing the tanks
        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);

        //Creating the split screen
        BufferedImage leftScreen = world.getSubimage(getXCoord(tank1) - GameConstants.GAME_SCREEN_WIDTH/4, getYCoord(tank1) - GameConstants.GAME_SCREEN_HEIGHT/2, GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightScreen = world.getSubimage(getXCoord(tank2) - GameConstants.GAME_SCREEN_WIDTH/4, getYCoord(tank2) - GameConstants.GAME_SCREEN_HEIGHT/2, GameConstants.GAME_SCREEN_WIDTH/2, GameConstants.GAME_SCREEN_HEIGHT);

        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, GameConstants.GAME_SCREEN_WIDTH / 2, 0, null);

        //Creating the game hud
        g2.setColor(Color.BLACK);
        g2.fillRect(0, GameConstants.GAME_SCREEN_HEIGHT - 95, GameConstants.GAME_SCREEN_WIDTH, 85);

        //Creating the health bars
        if (tank1.health == 20) {
            g2.setColor(Color.ORANGE);
        } else if (tank1.health == 10) {
            g2.setColor(Color.RED);
        } else {
            g2.setColor(Color.GREEN);
        }
        g2.fillRect(100, GameConstants.GAME_SCREEN_HEIGHT - 75, 9 * tank1.health, 20);

        if (tank2.health == 20) {
            g2.setColor(Color.ORANGE);
        } else if (tank2.health == 10) {
            g2.setColor(Color.RED);
        } else {
            g2.setColor(Color.GREEN);
        }
        g2.fillRect(GameConstants.GAME_SCREEN_WIDTH / 2 + 200, GameConstants.GAME_SCREEN_HEIGHT - 75, 9 * tank2.health, 20);

        //Creating the lives count
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Comic Sans", Font.BOLD, 15));
        g2.drawString("Lives: " + tank1.lives, 20, GameConstants.GAME_SCREEN_HEIGHT - 60);
        g2.drawString("Lives: " + tank2.lives, GameConstants.GAME_SCREEN_WIDTH / 2 + 120, GameConstants.GAME_SCREEN_HEIGHT - 60);

        //Creating the mini map
        BufferedImage miniMap = world.getSubimage(0, 0, world.getWidth(), world.getHeight());
        g2.scale(.20, .20);
        g2.drawImage(miniMap, GameConstants.GAME_SCREEN_WIDTH*2, GameConstants.GAME_SCREEN_HEIGHT * 4 - 200, null);
    }
}
