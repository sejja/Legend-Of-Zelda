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
        
        g.setColor(Color.BLUE);
        g.fillRect((int)(float)campos.x, (int)(float)campos.y, 100,100);
        g.setColor(Color.RED);
        g.drawRect((int)(float)cameracoord.x, (int)(float)cameracoord.y, 10,10);

        Color c = new Color(0,0,0,50);

        PosX -= mCamera.GetDimensions().x/2;
        PosY -= mCamera.GetDimensions().y/2;
        g.setColor(Color.magenta);
        g.drawRect(PosX, PosY, 10,10);

        int cameraSizeX = mCamera.GetDimensions().x;
        int cameraSizeY = mCamera.GetDimensions().y;


        g.setColor(c);
        for (int drawPointX = PosX; drawPointX < cameraSizeX; drawPointX+=scaleX ){
           for(int drawPointY = PosY; drawPointY < cameraSizeY; drawPointY+= scaleY){
                g.fillRect(drawPointX, drawPointY, scaleX, scaleY);
           }
        }
    }

    public void setOpacity(int i){
        opacity = i;
    }

    
}