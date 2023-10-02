//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Gameplay.States;

import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Sprite;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Gameplay.Enemies.*;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class PlayState extends State {
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState() {
        ArrayList<String> dialogueArrayList = new ArrayList<String>();
        ArrayList<String> dialogueArrayList2 = new ArrayList<String>();
        
        dialogueArrayList.add("En un mundo muy lejano] vivia una princesa que buscaba a su \nprincipe] y para logralo] llamo a todos los principes del reino \nademas deberas vuscar todos los artefactos de las piedras para \necuperar el poder de hyrule");
        dialogueArrayList.add("Ahora embarcate en una nueva aventura junto a tu espada y tu \narco");
        dialogueArrayList2.add("Muy buenas caballero] mi nombre es Juan] y estoy aqui protegiendo \nla puerta de acceso al palacio");
        dialogueArrayList2.add("A si que abandona este lugar por favor");
        
        new TileManager("Content/TiledProject/TestRoom.tmx");
        ObjectManager.GetObjectManager().AddEntity(new Npc("Aelarion", new Sprite("Content/Animations/NPC1.png"), new Vector2D<Float>(1415.f, 725.f), dialogueArrayList, new Vector2D<Float>(50.f, 62.f)) );
        ObjectManager.GetObjectManager().AddEntity(new Npc("Juan", new Sprite("Content/Animations/NPC1.png"), new Vector2D<Float>(1200f, 725.f), dialogueArrayList2, new Vector2D<Float>(50.f, 62.f)) );
        ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link/Link.png"), new Vector2D<Float>(700.f, 400.f), new Vector2D<Float>(100.f, 100.f)));
        ObjectManager.GetObjectManager().AddEntity(new Enemy(new Spritesheet("Content/Animations/gknight.png",16,28), new Vector2D<Float>(450.f, 300.f), new Vector2D<Float>(50.f, 100.f)));
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        ObjectManager.GetObjectManager().Update();
    }
}