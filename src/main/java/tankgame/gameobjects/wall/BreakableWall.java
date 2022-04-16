package tankgame.gameobjects.wall;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    public BreakableWall(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void destroyWall(boolean collided) {

    }

    @Override
    public void giveBuff(Tank tank) {

    }
}
