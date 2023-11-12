package Engine.Physics;

import Engine.ECSystem.Types.Actor;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Engine.Physics.Components.ColliderManager;
import Gameplay.Interactives.Interactive;
import Gameplay.Link.Player;

/* Statical Object Collision with the player
 *      StaticalObject
 *          -> No movable
 *      -> The statical Object must have a hitbox
 *      -> Implement it at the end of update
 */
public interface StaticPlayerCollision {
    default void playerCollision(BoxCollider hitbox){
        if(ColliderManager.GetColliderManager().playerCollision(hitbox)){
            Player link = (Player)ColliderManager.GetColliderManager().getMapAllCollision().get(Player.class).getFirst().GetParent();
            Vector2D<Float> currentPosition = link.GetPosition();
            System.out.println(link.getPreviusPosition().getModuleDistance(currentPosition));
            link.SetPosition(link.getPreviusPosition());
        }
    }
}