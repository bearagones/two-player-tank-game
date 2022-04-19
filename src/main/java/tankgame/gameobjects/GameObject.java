package tankgame.gameobjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject implements Collidable {
    public int x;
    public int y;
    public int angle;
    public boolean collided;
    public BufferedImage image;
    public Rectangle hitBox;

    public GameObject(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.hitBox = new Rectangle(x, y, this.image.getWidth(), this.image.getHeight());
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
    }

    public boolean hasCollided() {
        return collided;
    }

    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    public void detectCollision(Collidable object) {
        if (this.getHitBox().intersects(object.getHitBox())) {
            collided = true;
        }
    }
}
