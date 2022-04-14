package tankgame.gameobjects.powerup;

import tankgame.gameobjects.Tank;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class PowerUp {

    private static Map<String, PowerUp> powerUpMap;
    static {
        powerUpMap = new HashMap<>();
        powerUpMap.put("Armor", new ArmorPowerUp());
        powerUpMap.put("Critical Damage", new CriticalDamagePowerUp());
        powerUpMap.put("Fire Rate", new FireRatePowerUp());
        powerUpMap.put("Health", new HealthPowerUp());
        powerUpMap.put("Speed", new SpeedPowerUp());
    }

    public abstract void giveBuff(Tank tank);

    public abstract void getImage();
}
