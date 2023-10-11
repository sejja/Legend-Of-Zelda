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

public class TestRoom2 extends Level {

    public TestRoom2(Level right, Level left, Level up, Level down, String tiles) {
        super(right, left, up, down, new TileManager(tiles));
    }

    @Override
    public void Init(Vector2D<Float> position) {
        super.Init(position);

        var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

        Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

        Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);
        z.SetBounds(topright, bottomleft);

        System.out.println("Created level");
    }
}
