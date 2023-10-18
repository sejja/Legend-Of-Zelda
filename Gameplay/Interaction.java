package Gameplay;

import Engine.ECSystem.ObjectManager;
import Gameplay.Link.Player;

public interface Interaction{
    public void interaction();
    default void shutDownInteraction(){
        ((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).getFirst()).removeInteraction();
    }
}
