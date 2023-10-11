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
import Gameplay.Levels.TestRoom2;
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
        var t = new TestRoom2(null, null, null, null, "Content/TiledProject/StressTest.tmx");
        mTestLevel = new TestRoom(t, null, null, null, "Content/TiledProject/TestRoom2.tmx", new Vector2D<>(0.f, 0.f));
        t.SetLeftlevel(mTestLevel);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        Level.mCurrentLevel.Update();
        ObjectManager.GetObjectManager().Update();
    }
}