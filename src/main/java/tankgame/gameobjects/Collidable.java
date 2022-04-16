package tankgame.gameobjects;

import java.awt.*;

public interface Collidable {

    public void detectCollision(Collidable object);

    public boolean hasCollided();

    public Rectangle getHitBox();

    public void giveBuff(Tank tank);
}
