package tankgame.gameobjects;

import java.awt.*;

public interface Collidable {

    void detectCollision(Collidable object);

    boolean hasCollided();

    Rectangle getHitBox();

}
