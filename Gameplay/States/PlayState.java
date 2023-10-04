//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Gameplay.States;

import java.util.ArrayList;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Sprite;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Gameplay.Enemies.*;
import Gameplay.Levels.TestRoom;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class PlayState extends State {
    Level mTestLevel;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState() {
        mTestLevel = new TestRoom(null, null, null, null, "Content/TiledProject/TestRoom2.tmx", new Vector2D<>(0.f, 0.f));
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        mTestLevel.Update();
        ObjectManager.GetObjectManager().Update();
    }
}