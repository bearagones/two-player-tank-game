package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Collidable;
import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;
import tankgame.gameobjects.wall.Wall;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {

    private Tank tank;

    public PowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (this.getHitBox().intersects(object.getHitBox())) {
            boolean collided = true;
            this.giveBuff(tank);
        }
    }

    public abstract void giveBuff(Tank tank);

}
