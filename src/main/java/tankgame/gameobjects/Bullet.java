package tankgame.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {
    private int x;
    private int y;
    private BufferedImage image;
    private Rectangle hitBox;
    private boolean collided;

    public Bullet(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.image, x, y, null);
    }

    @Override
    public void detectCollision(Collidable object) {
        if (object instanceof Bullet) {
            if (this.getHitBox().intersects(object.getHitBox())) {
                collided = true;
            }
        }
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
}
