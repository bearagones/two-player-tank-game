package tankgame.gameobjects;

import tankgame.gameobjects.wall.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private boolean collided;

    public Bullet(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, x, y, null);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (object instanceof Tank || object instanceof Wall) {
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
        tank.health -= 5;
    }
}
