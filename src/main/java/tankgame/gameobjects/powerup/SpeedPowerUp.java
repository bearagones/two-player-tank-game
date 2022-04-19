package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;

public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    // A tank can have a max speed of x2
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.speed += 1) >= 2) {
            tank.speed = 2;
        } else {
            tank.speed += 1;
        }
    }

}
