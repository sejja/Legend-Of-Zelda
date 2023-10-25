package Gameplay.Levels;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Gameplay.Enemies.Units.GreenKnight;

public class OverWorld1Left extends Level {

    public OverWorld1Left(Level right, Level left, Level up, Level down, String tiles) {
        super(right, left, up, down, new TileManager(tiles));
    }
    
}
