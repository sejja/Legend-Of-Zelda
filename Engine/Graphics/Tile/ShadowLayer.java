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

        System.out.println(campos);
        System.out.println(cameracoord);
        
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
    /* 
    public static void Render(Graphics2D g, CameraComponent camerapos) {
        //if(true){return;}
        var cameracoord = camerapos.GetCoordinates();
        int mTileHeight = 64;
        int mTileWidth = 64;

        Vector2D<Float> tilemappos = Level.mCurrentLevel.GetBounds().GetPosition();

        int x = (int) ((cameracoord.x - tilemappos.x) / mTileWidth);
        int y = (int) ((cameracoord.y - tilemappos.y) / mTileHeight);

        System.out.println(camerapos.GetDimensions().x);
        System.out.println(camerapos.GetDimensions().y);
        
        for(int i = x; i < x + (camerapos.GetDimensions().x) + 1; i+=mTileWidth) {
            for(int j = y; j < y + (camerapos.GetDimensions().y) + 2; j+=mTileHeight) {
                    int blockx = i ;
                    int blocky = j ;

                    g.setColor(new Color(0, 0, 0, 100));
                    g.fillRect(blockx, blocky, mTileWidth, mTileHeight);
                }
        }
        
    }
    */
}