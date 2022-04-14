package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;

public class HealthPowerUp extends PowerUp {

    // A tank can have a max health of 20
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.health *= 2) >= 20) {
            tank.health = 20;
        }
    }
}
