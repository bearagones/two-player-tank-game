package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Collidable {
    public int x;
    public int y;
    public BufferedImage image;
    public Rectangle hitBox;

    public GameObject(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.hitBox = new Rectangle(x, y, this.image.getWidth(), this.image.getHeight());
    }

    public abstract void drawImage(Graphics g);

    public abstract void detectCollision(Collidable object);

}
