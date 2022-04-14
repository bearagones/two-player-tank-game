package tankgame.gameobjects;

import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Tank extends Player {
    private int xPosition;
    private int yPosition;
    private float rotationAngle;
    private BufferedImage img;

    private final float ROTATIONSPEED = 2.5f;

    public int armor;
    public int damage;
    public int fireRate;
    public int health;
    public int speed;
    private int lives;

    private boolean isLoser;

    public Tank(int xPosition, int yPosition, float rotationAngle, BufferedImage img) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.rotationAngle = rotationAngle;
        this.img = img;
    }

    private void standard() {
        this.armor = 0;
        this.damage = 1;
        this.fireRate = 1;
        this.health = 20;
        this.lives = 3;
        this.speed = 1;
        isLoser = false;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }


}
