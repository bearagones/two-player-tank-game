package tankgame.gameobjects.wall;

import tankgame.gameobjects.GameObject;

import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject {

    public Wall(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    public abstract boolean isBreakable();
}
