package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Search.pPair;
import Gameplay.Link.Player;

public class ShadowLayer {
    public static int[][] matrixOpacity; //Array de los gradients (width x height)
    int opacity = 230;
    static BoxCollider seeker;

    public void ShadowLayer(){
    }


    public void illuminate(Vector2D<Float> at, int r, float intensity) {

    }

    public void Render(Graphics2D g, CameraComponent mCamera) {

        if(matrixOpacity == null){
            matrixOpacity = new int[(int)(float)Level.mCurrentLevel.GetBounds().GetScale().x/64][(int)(float)Level.mCurrentLevel.GetBounds().GetScale().y/64];
            for (int i = 0; i<matrixOpacity.length; i++){
                for(int j=0; j<matrixOpacity[0].length; j++){
                    matrixOpacity[i][j] = 0;
                }
            }
        }

        final int scaleX = 64;
        final int scaleY = 64;

        int cameraSizeX = mCamera.GetDimensions().x;
        int cameraSizeY = mCamera.GetDimensions().y;


        Vector2D<Integer> cameraDrawPoint = getDrawPointPosition(mCamera);
        //System.out.println(cameraDrawPoint);
        for (int drawPointX = 0; drawPointX < cameraSizeX; drawPointX+=scaleX ){

            Color c = new Color(0,0,0, getOpacity(cameraDrawPoint));
            g.setColor(c);

            if(getOpacity(cameraDrawPoint) > 0){
                System.out.println("Wakala");
            }

            cameraDrawPoint.x++;
            int temp = cameraDrawPoint.y;

           for(int drawPointY = 0; drawPointY < cameraSizeY; drawPointY+= scaleY){
                cameraDrawPoint.y++;

                g.fillRect(drawPointX, drawPointY, scaleX, scaleY);
           }

           cameraDrawPoint.y = temp; 
        }
    }
    
    /* To get the tile that is in the drawpoint
     * 
     */
    private Vector2D<Integer> getDrawPointPosition(CameraComponent mCamera){
        Player link = (Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
        Vector2D<Integer> blockPosition = link.getPseudoPosition().getTilePosition();
        int gapX = mCamera.GetDimensions().x/(2*64);
        int gapY = mCamera.GetDimensions().x/(2*64);

        int cameraDrawPointX = blockPosition.x - gapX;
        int cameraDrawPointY = blockPosition.y+4 - gapY;

        int limitX = (int)(float)(Level.mCurrentLevel.GetBounds().GetScale().x - mCamera.GetDimensions().x)/64;
        int limitY = (int)(float)(Level.mCurrentLevel.GetBounds().GetScale().y - mCamera.GetDimensions().y)/64;

        if(cameraDrawPointX <0){cameraDrawPointX = 0;}
        else if(cameraDrawPointX > limitX){cameraDrawPointX = limitX;}
        if(cameraDrawPointY <0){cameraDrawPointY = 0;}
        else if(cameraDrawPointY > limitY){cameraDrawPointY = limitY;}

        //To see where is the cameraDrawPoint
        Vector2D<Float> result =  new Vector2D<Float>((Float)(float)cameraDrawPointX*64, (Float)(float)cameraDrawPointY*64);
        if(seeker == null){
            seeker = new BoxCollider(link, result, 1);
            link.AddComponent(seeker);
        }else{
            seeker.GetBounds().SetPosition(result);
        }

        Vector2D<Integer> _result =  new Vector2D<Integer>((int)(result.x/64), (int)(result.y/64));
        return _result;
    }

    private int getOpacity (Vector2D<Integer> cameraDrawPoint){
        return matrixOpacity[cameraDrawPoint.x][cameraDrawPoint.y];
    }
}