package Gameplay.Levels;

import java.util.ArrayList;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
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
        ArrayList<String> dialogueArrayList = new ArrayList<String>();
        ArrayList<String> dialogueArrayList2 = new ArrayList<String>();
        
        dialogueArrayList.add("En un mundo muy lejano] vivia una princesa que buscaba a su \nprincipe] y para logralo] llamo a todos los principes del reino \nademas deberas vuscar todos los artefactos de las piedras para \necuperar el poder de hyrule");
        dialogueArrayList.add("Ahora embarcate en una nueva aventura junto a tu espada y tu \narco");
        dialogueArrayList2.add("Muy buenas caballero] mi nombre es Juan] y estoy aqui protegiendo \nla puerta de acceso al palacio");
        dialogueArrayList2.add("A si que abandona este lugar por favor");
        ObjectManager.GetObjectManager().AddEntity(new Npc("Aelarion", new Spritesheet("Content/Animations/NPC/NPC_old.png", 68, 72), new Vector2D<Float>(1415.f, 725.f), dialogueArrayList, new Vector2D<Float>(78.f, 78.f), DOWN, squareMovement) );
        ObjectManager.GetObjectManager().AddEntity(new Npc("Juan", new Spritesheet("Content/Animations/NPC/NPC_boy.png", 64, 64), new Vector2D<Float>(1200f, 900.f), dialogueArrayList2, new Vector2D<Float>(78.f, 78.f),DOWN, yLineMovement) );        ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link/Link.png"), new Vector2D<Float>(700.f, 400.f), new Vector2D<Float>(100.f, 100.f)));
        ObjectManager.GetObjectManager().Update();
        ObjectManager.GetObjectManager().AddEntity(new Rock(new Vector2D<>(500f, 500f)));
        ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Float>(450.f, 300.f)));
        ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Float>(550.f, 300.f)));
        ObjectManager.GetObjectManager().AddEntity(new GreenKnight(new Vector2D<Float>(650.f, 300.f)));
        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

        Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

        Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);

        z.SetBounds(topright, bottomleft);
    }
}
