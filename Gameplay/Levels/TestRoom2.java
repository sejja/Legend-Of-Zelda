package Gameplay.Levels;

import java.util.ArrayList;

import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Gameplay.Enemies.Enemy;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class TestRoom2 extends World {

    public TestRoom2(World right, World left, World up, World down, String tiles) {
        super(right, left, up, down, new TileManager(tiles));

    }
}
