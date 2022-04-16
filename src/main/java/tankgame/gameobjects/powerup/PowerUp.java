package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {

    private Tank tank;
    private boolean collided;

    public PowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, x, y, null);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (object instanceof Tank) {
            if (this.getHitBox().intersects(object.getHitBox())) {
                object.giveBuff(tank);
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

    public abstract void giveBuff(Tank tank);

}
