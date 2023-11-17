//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Gameplay.States;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Engine.Physics.Components.ColliderManager;
import Engine.StateMachine.State;
import Gameplay.Enemies.*;
import Gameplay.Levels.OverWorld1Left;
import Gameplay.Levels.TestRoom;
import Gameplay.Levels.TestRoom2;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Blocks.Rock;
import Gameplay.Link.Arrow;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class PlayState extends State {
    World mTestLevel;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState() {
        var t = new TestRoom2(null, null, null, null, "Content/TiledProject/TestRoom2.tmx");
<<<<<<< HEAD
        var t2 = new TestRoom2(null, null, null, null, "Content/TiledProject/OverWorld1Left.tmx");
        var t3 = new TestRoom2(null, null, null, null, "Content/TiledProject/overworldleftup.tmx");

        mTestLevel = new TestRoom(t, t2, null, null, "Content/TiledProject/OverWorld1.tmx", new Vector2D<>(0.f, 0.f));
        
        t2.SetUpperLevel(t3);
        t3.SetLowerLevel(t2);
        t2.SetRightLevel(mTestLevel);
        t.SetLeftlevel(mTestLevel);
=======
        var pasilloDerAbajo3 = new TestRoom2(null, null, null, null, "Content/TiledProject/pasilloDerAbajo3.tmx");
        var pasilloIzqArriba3 = new TestRoom2(null, null, null, null, "Content/TiledProject/pasilloIzqArriba3.tmx");
        var pasilloIzqAbajo3 = new TestRoom2(null, null, null, null, "Content/TiledProject/pasilloIzqAbajo3.tmx");
        var entrada = new TestRoom2(null, null, null, null, "Content/TiledProject/entrada.tmx");
        var pasilloDer3 = new TestRoom2(null, null, null, null, "Content/TiledProject/pasilloDer3.tmx");
        var pasilloIzq3 = new TestRoom2(null, null, null, null, "Content/TiledProject/pasilloIzq3.tmx");
        var t2 = new TestRoom2(null, null, null, null, "Content/TiledProject/OverWorld1Left.tmx");
        var OverWorld = new TestRoom2(t, t2, entrada, null, "Content/TiledProject/overworld1.tmx");
        var t3 = new TestRoom2(null, null, null, null, "Content/TiledProject/OverlWorld_-1_1.tmx");
        var finalRoom3 = new TestRoom2(null, null, null, null, "Content/TiledProject/finalRoom3.tmx");
        var entradaArriba3 = new TestRoom2(null, null, null, null, "Content/TiledProject/entradaArriba3.tmx");
        var pasilloDerArriba3 = new TestRoom2(null, null, null, null, "Content/TiledProject/pasilloDerArriba3.tmx");
        
        mTestLevel = new TestRoom(pasilloDer3, t2, entradaArriba3, null, "Content/TiledProject/overworld1.tmx", new Vector2D<>(0.f, 0.f));
>>>>>>> main


        
        pasilloDer3.SetLeftLevel(mTestLevel);
        pasilloDer3.SetUpperLevel(pasilloDerArriba3);
        pasilloDer3.SetLowerLevel(pasilloDerAbajo3);
        pasilloIzq3.SetRightLevel(mTestLevel); 
        pasilloIzq3.SetUpperLevel(pasilloIzqArriba3);
        pasilloIzq3.SetLowerLevel(pasilloIzqAbajo3);
        entradaArriba3.SetUpperLevel(finalRoom3);
        finalRoom3.SetLowerLevel(entradaArriba3);
        entradaArriba3.SetLowerLevel(entrada);
        entradaArriba3.SetRightLevel(pasilloDerArriba3);
        pasilloDerArriba3.SetLowerLevel(pasilloDer3);
        pasilloDerArriba3.SetLeftLevel(entradaArriba3);
        pasilloIzqArriba3.SetLowerLevel(pasilloIzq3);
        pasilloIzqArriba3.SetRightLevel(entradaArriba3);
        pasilloDerAbajo3.SetUpperLevel(pasilloDer3);
        pasilloIzqAbajo3.SetUpperLevel(pasilloIzq3);
        //t2.SetRightLevel(OverWorld);
        t2.SetUpperLevel(t3);
        t3.SetLowerLevel(t2);


        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
        Vector2D<Float> topright = new Vector2D<>(mTestLevel.GetBounds().GetPosition().x + 1280.f / 2, mTestLevel.GetBounds().GetPosition().y + 720.f / 2);
        Vector2D<Float> bottomleft = new Vector2D<>(mTestLevel.GetBounds().GetPosition().x + mTestLevel.GetBounds().GetWidth() - 1280.f / 2, mTestLevel.GetBounds().GetPosition().y + mTestLevel.GetBounds().GetHeight() - 760.f / 2);
        z.SetBounds(topright, bottomleft); 
    }
    private void Spawn(Entity e){
        ObjectManager.GetObjectManager().AddEntity(e);
    }
    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        ShadowLayer.getShadowLayer().setOn(false);
        ObjectManager.GetObjectManager().Update();
    }
}