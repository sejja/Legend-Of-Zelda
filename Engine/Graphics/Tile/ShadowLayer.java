package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Gameplay.Enemies.Search.pPair;
import Gameplay.Link.Player;

public class ShadowLayer {
    float[][] mGradient; //Array de los gradients (width x height)
    int opacity = 230;
    public void illuminate(Vector2D<Float> at, int r, float intensity) {

    }
    public static void Render(Graphics2D g, CameraComponent mCamera) {

        final int scaleX = 64;
        final int scaleY = 64;

        Color c = new Color(0,0,0,50);
        int cameraSizeX = mCamera.GetDimensions().x;
        int cameraSizeY = mCamera.GetDimensions().y;


        for (int drawPointX = 0; drawPointX < cameraSizeX; drawPointX+=scaleX ){
           for(int drawPointY = 0; drawPointY < cameraSizeY; drawPointY+= scaleY){
                g.drawRect(drawPointX, drawPointY, scaleX, scaleY);
           }
        }

    }
    
    private static Vector2D<Float> getDrawPointPosition(CameraComponent mCamera){
        Player link = (Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
        Vector2D<Integer> blockPosition = link.getPseudoPosition().getTilePosition();
        int gapX = mCamera.GetDimensions().x/(2*64);
        int gapY = mCamera.GetDimensions().x/(2*64);
        return new Vector2D<Float>();
    }
}