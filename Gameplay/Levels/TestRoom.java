//
//	TestRoom.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 27/11/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Gameplay.Levels;

import java.util.ArrayList;
import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.Vector2D;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class TestRoom extends World {
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT= 2;
    private final int UP = 3;

    private final int squareMovement = 4;
    private final int xLineMovement = 5;
    private final int yLineMovement = 6;
    private final int stop = 7;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Creates a new level with the DB id, which contains it's info
    */ //----------------------------------------------------------------------
    public TestRoom(final Integer id) {
        super(id);
        ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/Link/Link.png")), new Vector2D<Float>(1350.f, 800.f), new Vector2D<Float>(100.f, 100.f)));
        Init(new Vector2D<Float>(0.f, 0.f));
        
        ArrayList<String> dialogueArrayList = new ArrayList<String>();
        ArrayList<String> dialogueArrayList2 = new ArrayList<String>();
        ShadowLayer.getShadowLayer().setOn(true);
        ShadowLayer.getShadowLayer().buildMatrix();
        dialogueArrayList.add("En un mundo muy lejano] vivia una princesa que buscaba a su \nprincipe] y para logralo] llamo a todos los principes del reino \nademas deberas vuscar todos los artefactos de las piedras para \necuperar el poder de hyrule");
        dialogueArrayList.add("Ahora embarcate en una nueva aventura junto a tu espada y tu \narco");
        dialogueArrayList2.add("Muy buenas caballero] mi nombre es Juan] y estoy aqui protegiendo \nla puerta de acceso al palacio");
        dialogueArrayList2.add("A si que abandona este lugar por favor");
        ObjectManager.GetObjectManager().AddEntity(new Npc("Aelarion", new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/NPC/NPC_old.png"), new Vector2D<Integer>(68, 72)), new Vector2D<Float>(1330.f, 800.f), new Vector2D<Float>(78.f, 78.f), UP, yLineMovement) );
        ObjectManager.GetObjectManager().AddEntity(new Npc("Juan", new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/NPC/NPC_boy.png"), new Vector2D<Integer>(64, 64)), new Vector2D<Float>(1580.f, 1550.f), new Vector2D<Float>(78.f, 78.f),0, stop) );
        
        
      /* 
        for(int i = 0; i < 1; i++){
            ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Integer>(10, 10)));
            ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Integer>(11, 11)));
            ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Integer>(23, 23)));
        }
        */
        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();

        Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);
        Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);

        z.SetBounds(topright, bottomleft);
        //Sound bg = new Sound(AssetManager.Instance().GetResource("Content/Audio/overworld.wav"));
        //Audio.Instance().Play(bg);
        //Audio.Instance().SetLoopCount(bg, -1);
    }
}
