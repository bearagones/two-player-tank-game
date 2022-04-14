package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;

public class SpeedPowerUp extends PowerUp {

    // A tank can have a max speed of 4
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.speed *= 2) >= 4) {
            tank.speed = 4;
        }
    }
}
