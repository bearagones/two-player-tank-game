package tankgame.gameobjects;

import java.awt.image.BufferedImage;

public abstract class Player extends GameObject {

    protected boolean upPressed;
    protected boolean downPressed;
    protected boolean rightPressed;
    protected boolean leftPressed;
    protected boolean shootPressed;

    public Player(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    public void toggleUpPressed() {
        this.upPressed = true;
    }

    public void toggleDownPressed() {
        this.downPressed = true;
    }

    public void toggleRightPressed() {
        this.rightPressed = true;
    }

    public void toggleLeftPressed() {
        this.leftPressed = true;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    public void unToggleUpPressed() {
        this.upPressed = false;
    }

    public void unToggleDownPressed() {
        this.downPressed = false;
    }

    public void unToggleRightPressed() {
        this.rightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.leftPressed = false;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }
}
