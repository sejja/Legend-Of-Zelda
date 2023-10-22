package Gameplay.Levels;

import java.util.ArrayList;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.Normblock;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Gameplay.AnimatedObject.Torch;
import Gameplay.Enemies.Enemy;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Blocks.Rock;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;



public class TestRoom extends Level {
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT= 2;
    private final int UP = 3;

    private final int squareMovement = 4;
    private final int xLineMovement = 5;
    private final int yLineMovement = 6;
    private final int stop = 7;
    
    public TestRoom(Level right, Level left, Level up, Level down, String tiles, Vector2D<Float> pos) {
        super(right, left, up, down, new TileManager(tiles));
        Init(pos);
        ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link/Link.png"), new Vector2D<Float>(1420f, 800.f), new Vector2D<Float>(100.f, 100.f)));
        ArrayList<String> dialogueArrayList = new ArrayList<String>();
        ArrayList<String> dialogueArrayList2 = new ArrayList<String>();
        
        dialogueArrayList.add("En un mundo muy lejano] vivia una princesa que buscaba a su \nprincipe] y para logralo] llamo a todos los principes del reino \nademas deberas vuscar todos los artefactos de las piedras para \necuperar el poder de hyrule");
        dialogueArrayList.add("Ahora embarcate en una nueva aventura junto a tu espada y tu \narco");
        dialogueArrayList2.add("Muy buenas caballero] mi nombre es Juan] y estoy aqui protegiendo \nla puerta de acceso al palacio");
        dialogueArrayList2.add("A si que abandona este lugar por favor");

        ObjectManager.GetObjectManager().AddEntity(new Npc("Aelarion", new Spritesheet("Content/Animations/NPC/NPC_old.png", 68, 72), new Vector2D<Float>(1330.f, 800.f), dialogueArrayList, new Vector2D<Float>(78.f, 78.f), UP, yLineMovement) );
        ObjectManager.GetObjectManager().AddEntity(new Npc("Juan", new Spritesheet("Content/Animations/NPC/NPC_boy.png", 64, 64), new Vector2D<Float>(1580.f, 1550.f), dialogueArrayList2, new Vector2D<Float>(78.f, 78.f),4, stop) );
        
        ObjectManager.GetObjectManager().Update();

        ObjectManager.GetObjectManager().AddEntity(new Rock(new Vector2D<>(448f, 448f)));
        ObjectManager.GetObjectManager().AddEntity(new Rock(new Vector2D<>(8*64f, 8*64f)));
        ObjectManager.GetObjectManager().AddEntity(new Rock(new Vector2D<>(8*64f, 8*64f)));

        ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Float>(1300.f, 1670.f)));
        ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Float>(1400.f, 1670.f)));
        ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Float>(1500.f, 1670.f)));

        ObjectManager.GetObjectManager().AddEntity(new Torch(new Vector2D<Float>(1260.f, 1550.f)));
        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

        Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

        Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);

        z.SetBounds(topright, bottomleft);
        //System.out.println(((Normblock)TileManager.sLevelObjects.GetBlockAt(7,7)).isBlocked());
    }
}
