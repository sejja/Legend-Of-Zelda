package Gameplay;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Gameplay.Link.Player;

public interface Interaction{
    default void INTERACTION(){
        if(playerIsLooking()){
            interaction();
        }else{shutDownInteraction();}
    }

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