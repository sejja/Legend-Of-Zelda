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
    default void playerCollision(){
        Interactive staticalObject = (Interactive)this;
        if(!ColliderManager.GetColliderManager().getCollision(staticalObject.getmCollision(), Player.class,true).isEmpty()){
            Player link = (Player)ColliderManager.GetColliderManager().getMapAllCollision().get(Player.class).getFirst().GetParent();
            Vector2D<Float> currentPosition = link.GetPosition();
            Float difference = 5f;
            switch(link.getPseudoPosition().getTargetDirection(staticalObject.getPseudoPosition())){
                case UP :
                    difference += Math.abs(staticalObject.GetScale().y/2 + link.getHitbox().GetBounds().GetScale().y/2 - staticalObject.getPseudoPosition().getModuleDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x, currentPosition.y + difference));
                    return;
                case DOWN :
                    difference += Math.abs(staticalObject.GetScale().y/2 + link.getHitbox().GetBounds().GetScale().y/2 - staticalObject.getPseudoPosition().getModuleDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x, currentPosition.y - difference));
                    return;
                case LEFT:
                    difference += Math.abs(staticalObject.GetScale().x/2 + link.getHitbox().GetBounds().GetScale().x/2 - staticalObject.getPseudoPosition().getModuleDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x + difference, currentPosition.y));
                    return;
                case RIGHT:
                    difference += Math.abs(staticalObject.GetScale().x/2 + link.getHitbox().GetBounds().GetScale().x/2 - staticalObject.getPseudoPosition().getModuleDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x - difference, currentPosition.y));
                    return;
            }
        }
    }
}