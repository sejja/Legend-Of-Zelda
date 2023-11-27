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

public class TestRoom extends World {

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
        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
        Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);
        Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);

        z.SetBounds(topright, bottomleft);
        Sound bg = new Sound(AssetManager.Instance().GetResource("Content/Audio/overworld.wav"));
        Audio.Instance().Play(bg);
        Audio.Instance().SetLoopCount(bg, -1);
    }
}
