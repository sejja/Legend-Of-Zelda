package Gameplay.Levels;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Gameplay.Enemies.Units.GreenKnight;

public class OverWorld1Left extends World {

    public OverWorld1Left(World right, World left, World up, World down, String tiles) {
        super(right, left, up, down, new TileManager(tiles));
    }
    
}
