package Gameplay;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Gameplay.Link.Player;
/** This interface allows actors to have interaction withe the player, overriding interaction()
 *  @author LFenome | Lingfeng
 */
public interface Interaction{
    default void INTERACTION(){
        if(playerIsLooking()){
            interaction();
        }else{shutDownInteraction();}
    }
    /** This function will be call only if the player has press the interact buttom and is looking at the player
     * 
     */
    public void interaction();

    default void shutDownInteraction(){
        ((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0)).removeInteraction();
    }
    default boolean playerIsLooking(){
        Actor thisActor = (Actor)this;
        Player player = (Player) ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
        if(player.getPseudoPosition().getTargetDirection(thisActor.getPseudoPosition()) == player.getDirection()){
            return true;
        }
        return false;
    }
}