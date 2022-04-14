package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;

public class CriticalDamagePowerUp extends PowerUp {

    // A tank can give a max of 2x damage
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.damage *= 2) >= 2) {
            tank.damage = 2;
        }
    }

    @Override
    public void getImage() {

    }
}
