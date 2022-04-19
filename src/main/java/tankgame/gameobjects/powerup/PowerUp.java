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

    public abstract void giveBuff(Tank tank);

}
