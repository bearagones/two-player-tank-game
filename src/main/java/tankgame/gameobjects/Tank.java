package tankgame.gameobjects;

import tankgame.GameConstants;
import tankgame.GameLoader;
import tankgame.gameobjects.powerup.PowerUp;
import tankgame.gameobjects.wall.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Player implements Moveable {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private final int R = 1;
    private final float ROTATIONSPEED = 2.0f;

    public int armor, damage, fireRate, health, speed, lives;

    public boolean isLoser;

    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage image) {
        super(x, y, image);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.hitBox = new Rectangle(x, y, this.image.getWidth(), this.image.getHeight());

        this.armor = 0;
        this.damage = 1;
        this.fireRate = 1;
        this.health = 30;
        this.lives = 3;
        this.speed = 1;
        this.isLoser = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void shoot() {
        Bullet b = new Bullet(x, y, angle, GameLoader.bullet);
        bullets.add(b);
        shootPressed = false;
    }

    private void hit() {
        if (this.health - 10 <= 0) {
            this.health = 0;
            death();
        } else {
            this.health -= 10;
        }
    }

    private void death() {
        this.lives -= 1;
        if (this.lives == 0) {
            this.isLoser = true;
        } else {
            this.health = 30;
        }
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
        for (Bullet b : bullets) {
            b.drawImage(g);
        }
    }

    @Override
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

        for (Iterator<Bullet> bulletIterator = bullets.iterator(); bulletIterator.hasNext();) {
            Bullet bullet = bulletIterator.next();
            if (bullet.hasCollided()) {
                bulletIterator.remove();
            } else {
                bullet.update();
            }
        }
    }

    @Override
    public void checkBorder() {
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
    public void detectCollision(Collidable object) {
        if (this.getHitBox().intersects(object.getHitBox())) {
            if (object instanceof Bullet) {
                this.hit();
                collided = true;
            }

            if (object instanceof PowerUp) {
                collided = true;
            }

            if (this.upPressed) {
                this.moveBackwards();
            } else if (this.downPressed) {
                this.moveForwards();
            }
        }
        this.bullets.forEach(bullet -> {
            bullet.detectCollision(object);
            object.detectCollision(bullet);
        });
    }

}
