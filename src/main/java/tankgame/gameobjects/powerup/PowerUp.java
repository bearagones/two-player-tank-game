package tankgame.gameobjects.powerup;

import tankgame.gameobjects.GameObject;
import tankgame.gameobjects.Tank;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject {

    public PowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    public abstract void giveBuff(Tank tank);

}
