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
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.ZeldaCameraComponent;
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
        var t = new TestRoom2(null, null, null, null, "Content/TiledProject/TestRoom2.tmx");
        mTestLevel = new TestRoom(t, null, null, null, "Content/TiledProject/TestRoom.tmx", new Vector2D<>(0.f, 0.f));
        t.SetLeftlevel(mTestLevel);

        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();
        Vector2D<Float> topright = new Vector2D<>(mTestLevel.GetBounds().GetPosition().x + 1280.f / 2, mTestLevel.GetBounds().GetPosition().y + 720.f / 2);
        Vector2D<Float> bottomleft = new Vector2D<>(mTestLevel.GetBounds().GetPosition().x + mTestLevel.GetBounds().GetWidth() - 1280.f / 2, mTestLevel.GetBounds().GetPosition().y + mTestLevel.GetBounds().GetHeight() - 760.f / 2);
        z.SetBounds(topright, bottomleft); 
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