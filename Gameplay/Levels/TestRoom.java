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
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class TestRoom extends Level {

    public TestRoom(Level right, Level left, Level up, Level down, String tiles, Vector2D<Float> pos) {
        super(right, left, up, down, new TileManager(tiles));
        Init(pos);
        ArrayList<String> dialogueArrayList = new ArrayList<String>();
        ArrayList<String> dialogueArrayList2 = new ArrayList<String>();
        
        dialogueArrayList.add("En un mundo muy lejano] vivia una princesa que buscaba a su \nprincipe] y para logralo] llamo a todos los principes del reino \nademas deberas vuscar todos los artefactos de las piedras para \necuperar el poder de hyrule");
        dialogueArrayList.add("Ahora embarcate en una nueva aventura junto a tu espada y tu \narco");
        dialogueArrayList2.add("Muy buenas caballero] mi nombre es Juan] y estoy aqui protegiendo \nla puerta de acceso al palacio");
        dialogueArrayList2.add("A si que abandona este lugar por favor");
        ObjectManager.GetObjectManager().AddEntity(new Npc("Aelarion", new Sprite("Content/Animations/NPC1.png"), new Vector2D<Float>(1415.f, 725.f), dialogueArrayList, new Vector2D<Float>(50.f, 62.f)) );
        ObjectManager.GetObjectManager().AddEntity(new Npc("Juan", new Sprite("Content/Animations/NPC1.png"), new Vector2D<Float>(1200f, 725.f), dialogueArrayList2, new Vector2D<Float>(50.f, 62.f)) );
        ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link/Link.png"), new Vector2D<Float>(700.f, 400.f), new Vector2D<Float>(100.f, 100.f)));
        ObjectManager.GetObjectManager().AddEntity(new Enemy(new Spritesheet("Content/Animations/gknight.png",16,28), new Vector2D<Float>(450.f, 300.f), new Vector2D<Float>(50.f, 100.f)));
        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

        Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

        Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() * 64 - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight()  * 64.f - 760.f / 2);

        z.SetBounds(topright, bottomleft);
    }
}
