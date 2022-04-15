package tankgame.gameobjects.wall;

import tankgame.gameobjects.Bullet;
import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {
    private int x;
    private int y;
    private BufferedImage image;
    private Rectangle hitBox;
    private boolean collided;

    public Wall(int x, int y, BufferedImage image) {
        super(x, y, image);
        this.hitBox = new Rectangle(x, y, this.image.getWidth(), this.image.getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, x, y, null);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.getHitBox().intersects(object.getHitBox())) {
                collided = true;
            }
        }
    }

    public abstract void destroyWall(boolean collided);
}
