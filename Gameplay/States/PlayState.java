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
    World entrada;
    World pasilloDer;
    World pasilloDerArriba;
    World pasilloDerAbajo;
    World pasilloIzq;
    World pasilloIzqArriba;
    World pasilloIzqAbajo;
    World entradaArriba;
    World finalRoom;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState() {
        var t = new TestRoom2(null, null, null, null, "Content/TiledProject/TestRoom2.tmx");
        var pasilloDer = new TestRoom2(null, entrada, pasilloDerArriba, pasilloDerAbajo, "Content/TiledProject/pasilloDer.tmx");
        var pasilloDerArriba = new TestRoom2(null, entradaArriba, null, entrada, "Content/TiledProject/pasilloDerArriba.tmx");
        var pasilloDerAbajo = new TestRoom2(null, null, pasilloDer, null, "Content/TiledProject/pasilloDerAbajo.tmx");
        var pasilloIzq = new TestRoom2(entrada, null, pasilloIzqArriba, pasilloIzqAbajo, "Content/TiledProject/pasilloIzq.tmx");
        var pasilloIzqArriba = new TestRoom2(entradaArriba, null, null, pasilloIzq, "Content/TiledProject/pasilloIzqArriba.tmx");
        var pasilloIzqAbajo = new TestRoom2(null, null, pasilloIzq, null, "Content/TiledProject/pasilloIzqAbajo.tmx");
        var entradaArriba = new TestRoom2(pasilloDerArriba, pasilloIzqArriba, finalRoom, entrada, "Content/TiledProject/entradaArriba.tmx");
        var finalRoom = new TestRoom2(null, null, null, entradaArriba, "Content/TiledProject/finalRoom.tmx");
        var entrada = new TestRoom2(pasilloDer, pasilloIzq, entradaArriba, mTestLevel, "Content/TiledProject/entrada.tmx");


        var t2 = new OverWorld1Left(null, null, entrada, null, "Content/TiledProject/OverWorld1Left.tmx");
        mTestLevel = new TestRoom(pasilloDer, t2, entrada, null, "Content/TiledProject/Overworld1.tmx", new Vector2D<>(0.f, 0.f));
        
        t2.SetRightLevel(mTestLevel);
       

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
        ObjectManager.GetObjectManager().Update();
    }
}