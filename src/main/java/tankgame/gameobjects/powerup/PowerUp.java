package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Bullet;
import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {

    public PowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (this.getHitBox().intersects(object.getHitBox())) {
            if (object instanceof Tank) {
                collided = true;
            } else if (object instanceof Bullet) {
                collided = false;
            }
        }
    }

    public abstract void giveBuff(Tank tank);

}
