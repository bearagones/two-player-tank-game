package tankgame.gameobjects.wall;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    public BreakableWall(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

}
