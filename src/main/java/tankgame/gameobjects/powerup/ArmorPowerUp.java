package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;

public class ArmorPowerUp extends PowerUp {

    public ArmorPowerUp(int x, int y, BufferedImage image) {
        super(x, y, image);
    }

    // A tank can have a max of 2x armor
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.armor *= 2) >= 2) {
            tank.armor = 2;
        }
    }

}
