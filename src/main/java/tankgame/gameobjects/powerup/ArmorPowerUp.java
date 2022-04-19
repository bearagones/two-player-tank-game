package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;

public class ArmorPowerUp extends PowerUp {

    public ArmorPowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    // A tank can have a max of 1x armor
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.armor *= 2) >= 1) {
            tank.armor = 1;
        } else {
            tank.armor += 1;
        }
    }

}
