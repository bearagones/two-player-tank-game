package tankgame.gameobjects.wall;

import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {

    public UnbreakableWall(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void destroyWall(boolean collided) {

    }
}