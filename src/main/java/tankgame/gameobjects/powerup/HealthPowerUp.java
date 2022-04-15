package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;

public class HealthPowerUp extends PowerUp {

    public HealthPowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    // A tank can have a max health of 20
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.health *= 2) >= 20) {
            tank.health = 20;
        }
    }

}
