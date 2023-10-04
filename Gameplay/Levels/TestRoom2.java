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

    public TestRoom2(Level right, Level left, Level up, Level down, String tiles, Vector2D<Float> pos) {
        super(right, left, up, down, new TileManager(tiles));
        Init(pos);
    }
}
