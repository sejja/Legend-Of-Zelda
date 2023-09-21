//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.StateMachine.States;

import java.awt.Graphics2D;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.TileManager;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Engine.StateMachine.StateMachine;
import Gameplay.Player;

public class PlayState extends State {
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState(StateMachine superm) {
        super(superm);
        ObjectManager.GetObjectManager().AddEntity(new TileManager("Content/TiledProject/StressTest.tmx"));
        FontObject mFont = (FontObject)ObjectManager.GetObjectManager().AddEntity(new FontObject("Content/Fonts/ZeldaFont.png", "THE LEGEND OF ANDONI"));
        mFont.SetPosition(new Vector2D<>(100.f, 100.f));
        mFont.SetScale(new Vector2D<>(32.f, 32.f));
        ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link.png"), new Vector2D<Float>(300.f, 300.f), new Vector2D<Float>(100.f, 100.f)));
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

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Input(InputManager inputmanager) {
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders onto the screen
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g) {
        GraphicsPipeline.GetGraphicsPipeline().Render(g);
    }
}
