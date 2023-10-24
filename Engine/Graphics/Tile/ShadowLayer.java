package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.ECSystem.Level;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;

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
        g.setColor(c);



        for (int drawPointX = 0; drawPointX < cameraSizeX; drawPointX+=scaleX ){
           for(int drawPointY = 0; drawPointY < cameraSizeY; drawPointY+= scaleY){
                g.drawRect(drawPointX, drawPointY, scaleX, scaleY);
           }
        }
    }
    
    
}