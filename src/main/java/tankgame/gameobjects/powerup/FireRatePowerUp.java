package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;

public class FireRatePowerUp extends PowerUp {

    public FireRatePowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    // A tank can have a max fire rate of 4
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.fireRate *= 2) >= 4) {
            tank.fireRate = 4;
        }
    }

}
