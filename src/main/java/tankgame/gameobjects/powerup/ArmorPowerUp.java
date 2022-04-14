package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;

public class ArmorPowerUp extends PowerUp {

    // A tank can have a max of 2x armor
    @Override
    public void giveBuff(Tank tank) {
        if ((tank.armor *= 2) >= 2) {
            tank.armor = 2;
        }
    }

    @Override
    public void getImage() {
        
    }
}
