package tankgame.gameobjects.wall;

import tankgame.gameobjects.Bullet;
import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {

    private boolean collided;

    public Wall(int x, int y, BufferedImage image) {
        super(x, y, image);
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

    @Override
    public boolean hasCollided() {
        return collided;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public abstract void destroyWall(boolean collided);
}
