package tankgame.gameobjects.wall;

import tankgame.gameobjects.Bullet;
import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;

import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {

    public Wall(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (this.getHitBox().intersects(object.getHitBox())) {
            if (object instanceof Tank) {
                collided = false;
            } else if (object instanceof Bullet) {
                collided = true;
            }
        }
    }

    public abstract boolean isBreakable();

}
