package tankgame.gameobjects;

import tankgame.GameConstants;
import tankgame.gameobjects.powerup.PowerUp;
import tankgame.gameobjects.wall.Wall;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Moveable {

    private int vx;
    private int vy;
    private int angle;

    public Bullet(int x, int y, int angle, BufferedImage image) {
        super(x, y, image);
        this.angle = angle;
    }

    public void moveForwards() {
        int r = 3;
        int vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        int vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
    }

    @Override
    public void update() {
        moveForwards();
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
            collided = true;
            x = vx;
            y = vy;
        }
    }

}
