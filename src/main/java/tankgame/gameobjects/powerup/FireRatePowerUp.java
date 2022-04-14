package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;

public class FireRatePowerUp extends PowerUp {

    // A tank can have a max fire rate of 4
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.fireRate *= 2) >= 4) {
            tank.fireRate = 4;
        }
    }
}
