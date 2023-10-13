package Engine.Physics;

import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Engine.Physics.Components.ColliderManager;
import Gameplay.Link.Player;

/* Statical Object Collision with the player
 *      StaticalObject
 *          -> No movable
 *      -> The statical Object must have a hitbox
 *      -> Implement it at the end of update
 */
public interface StaticPlayerCollision {
    default void playerCollision(BoxCollider thisHitmox){
        if(!ColliderManager.GetColliderManager().getCollision(thisHitmox, Player.class,true).isEmpty()){
             Player link = (Player)ColliderManager.GetColliderManager().getMapAllCollision().get(Player.class).getFirst().GetParent();
            Vector2D<Float> currentPosition = link.GetPosition();
            Float difference = link.getVelocity()+1f;
            switch(link.getDirection()){
                case UP :
                    link.SetPosition(new Vector2D<Float>(currentPosition.x, currentPosition.y + difference));
                    return;
                case DOWN :
                    link.SetPosition(new Vector2D<Float>(currentPosition.x, currentPosition.y - difference));
                    return;
                case LEFT:
                    link.SetPosition(new Vector2D<Float>(currentPosition.x + difference, currentPosition.y));
                    return;
                case RIGHT:
                    link.SetPosition(new Vector2D<Float>(currentPosition.x - difference, currentPosition.y));
                    return;
            }
        }
    }
}
