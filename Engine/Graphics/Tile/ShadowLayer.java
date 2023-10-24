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
        var cameracoord = mCamera.GetCoordinates();
        var campos = mCamera.GetParent().GetPosition();
        Vector2D<Float> tilemapPos = Level.mCurrentLevel.GetBounds().GetPosition();
        
        int PosX = (int)(float)(campos.x - cameracoord.x);
        int PosY = (int)(float)(campos.y - cameracoord.y);
        Color c = new Color(0,0,0,50);
        PosX -= 10*scaleX;
        PosY -= 7*scaleY;
        g.setColor(c);
        for (int drawPointX = PosX; drawPointX < 20*scaleX; drawPointX+=scaleX ){
           for(int drawPointY = PosY; drawPointY < 11*scaleY; drawPointY+= scaleY){
                g.fillRect(drawPointX, drawPointY, scaleX, scaleY);
           }
        }
        //g.fillRect(PosX - 1000, PosY-1000, 6000, 6000);

    }

    public void setOpacity(int i){
        opacity = i;
    }
}