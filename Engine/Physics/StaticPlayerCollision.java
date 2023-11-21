package Engine.Physics;

import Engine.ECSystem.Types.Actor;
import Engine.Math.EuclideanCoordinates;
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
        Actor staticalObject = (Actor)this;
        if(ColliderManager.GetColliderManager().playerCollision(hitbox)){
            Player link = (Player)ColliderManager.GetColliderManager().getMapAllCollision().get(Player.class).getFirst().GetParent();
            Vector2D<Float> currentPosition = link.GetPosition();
            Float difference = 1f;
            EuclideanCoordinates linkPosition = new EuclideanCoordinates(link.getPseudoPosition());
            EuclideanCoordinates staticalObjectPosition = new EuclideanCoordinates(staticalObject.getPseudoPosition());

            switch(linkPosition.getTargetDirection(staticalObject.getPseudoPosition())){
                case UP :
                    difference += Math.abs(staticalObject.GetScale().y/2 + link.getHitbox().GetBounds().GetScale().y/2 - staticalObjectPosition.GetModulusDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x, currentPosition.y + difference));
                    return;
                case DOWN :
                    difference += Math.abs(staticalObject.GetScale().y/2 + link.getHitbox().GetBounds().GetScale().y/2 - staticalObjectPosition.GetModulusDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x, currentPosition.y - difference));
                    return;
                case LEFT:
                    difference += Math.abs(staticalObject.GetScale().x/2 + link.getHitbox().GetBounds().GetScale().x/2 - staticalObjectPosition.GetModulusDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x + difference, currentPosition.y));
                    return;
                case RIGHT:
                    difference += Math.abs(staticalObject.GetScale().x/2 + link.getHitbox().GetBounds().GetScale().x/2 - staticalObjectPosition.GetModulusDistance(link.getPseudoPosition()));
                    link.SetPosition(new Vector2D<Float>(currentPosition.x - difference, currentPosition.y));
                    return;
            }
        }
    }
}