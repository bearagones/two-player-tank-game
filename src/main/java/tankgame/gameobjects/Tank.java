package tankgame.gameobjects;

import tankgame.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Player {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private float angle;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    public int armor;
    public int damage;
    public int fireRate;
    public int health;
    public int speed;
    public int lives;

    public boolean isLoser;
    public boolean collided;

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage image) {
        super(x, y, image);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;

        this.armor = 0;
        this.damage = 1;
        this.fireRate = 1;
        this.health = 20;
        this.lives = 3;
        this.speed = 1;
        this.isLoser = false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update() {
        if (this.upPressed) {
            this.moveForwards();
        }

        if (this.downPressed) {
            this.moveBackwards();
        }

        if (this.leftPressed) {
            this.rotateLeft();
        }

        if (this.rightPressed) {
            this.rotateRight();
        }

        if (this.shootPressed) {
            this.shoot();
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void shoot() {
        System.out.println("pew");
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 88) {
            x = GameConstants.GAME_SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.GAME_SCREEN_HEIGHT - 80) {
            y = GameConstants.GAME_SCREEN_HEIGHT - 80;
        }
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x, y, this.image.getWidth(), this.image.getHeight());
    }

    @Override
    public void detectCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.getHitBox().intersects(object.getHitBox())) {
                collided = true;
            }
        }
    }

    @Override
    public boolean hasCollided() {
        return collided;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void giveBuff(Tank tank) {
    }
}
