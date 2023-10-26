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
//Pendiente de documentacion y de refactorizacion
public class ShadowLayer {

    private static ShadowLayer shadowLayer;
    private int[][] matrixOpacity; //Array de los gradients (width x height)
    public int opacity;
    private BoxCollider seeker;
    private boolean isOn = false;

    public ShadowLayer(int defaultOpacity){
        this.opacity = defaultOpacity;
        shadowLayer = this;
    }

    public static ShadowLayer getShadowLayer (){return shadowLayer;}

    public void illuminate(Vector2D<Integer> tilePosition, int opacity) {

        if(matrixOpacity == null){
            buildMatrix();
        }

        try{
            matrixOpacity[tilePosition.x][tilePosition.y] += opacity;
        }catch(java.lang.ArrayIndexOutOfBoundsException e){
            matrixOpacity[tilePosition.x][tilePosition.y] = this.opacity;
        }
    }

    public void Render(Graphics2D g, CameraComponent mCamera) {

        if(matrixOpacity == null){
            buildMatrix();
        }

        if(!isOn){return;}

        final int scaleX = 64;
        final int scaleY = 64;

        int cameraSizeX = mCamera.GetDimensions().x;
        int cameraSizeY = mCamera.GetDimensions().y;


        Vector2D<Integer> cameraDrawPoint = getDrawPointPosition(mCamera);
        Vector2D<Integer> windowShadowDrawPoint = getWindowViewedDrawPoint(cameraDrawPoint, mCamera);


        //______________________________________________________________________________________________________
        for (int drawPointX = windowShadowDrawPoint.x; drawPointX < cameraSizeX; drawPointX+=scaleX ){

            int temp = cameraDrawPoint.y;

           for(int drawPointY = windowShadowDrawPoint.y; drawPointY < cameraSizeY; drawPointY+= scaleY){

                Color c = new Color(0,0,0, getOpacity(cameraDrawPoint));
                g.setColor(c);
    
                g.fillRect(drawPointX, drawPointY, scaleX, scaleY);
                cameraDrawPoint.y++;
           }

           cameraDrawPoint.y = temp; 
           cameraDrawPoint.x++;
        }
        //______________________________________________________________________________________________________
    }
    
    /* To get the tile that is in the drawpoint
     * 
     */
    private Vector2D<Integer> getDrawPointPosition(CameraComponent mCamera){
        Player link = (Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
        Vector2D<Integer> blockPosition = link.getPseudoPosition().getTilePosition();
        int gapX = mCamera.GetDimensions().x/(2*64);
        int gapY = mCamera.GetDimensions().x/(2*64);

        int cameraDrawPointX = blockPosition.x-1 - gapX;
        int cameraDrawPointY = blockPosition.y+3 - gapY;

        //Cap the limit
        int limitX = (int)(float)(Level.mCurrentLevel.GetBounds().GetScale().x - mCamera.GetDimensions().x)/64;
        int limitY = (int)(float)(Level.mCurrentLevel.GetBounds().GetScale().y - mCamera.GetDimensions().y)/64;
        if(cameraDrawPointX <0){cameraDrawPointX = 0;}
        else if(cameraDrawPointX > limitX){cameraDrawPointX = limitX;}
        if(cameraDrawPointY <0){cameraDrawPointY = 0;}
        else if(cameraDrawPointY > limitY){cameraDrawPointY = limitY;}

        Vector2D<Float> result =  new Vector2D<Float>((Float)(float)cameraDrawPointX*64, (Float)(float)cameraDrawPointY*64);
        //To see where is the cameraDrawPoint
        /*
        if(seeker == null){
            seeker = new BoxCollider(link, result, 1);
            link.AddComponent(seeker);
        }else{
            seeker.GetBounds().SetPosition(result);
        }
        */
        Vector2D<Integer> _result =  new Vector2D<Integer>((int)(result.x/64), (int)(result.y/64));
        return _result;
    }

    private int getOpacity (Vector2D<Integer> cameraDrawPoint){
        try{
            if(matrixOpacity[cameraDrawPoint.x][cameraDrawPoint.y] > opacity){
                return opacity;
            }else if(matrixOpacity[cameraDrawPoint.x][cameraDrawPoint.y] < 0 ){
                return 0;
            }

            return matrixOpacity[cameraDrawPoint.x][cameraDrawPoint.y];
        }catch(java.lang.ArrayIndexOutOfBoundsException e){


            return opacity;
        }
    }

    private Vector2D<Integer> getWindowViewedDrawPoint (Vector2D<Integer> cameraTilePositionn, CameraComponent cameraComponent){
        Vector2D<Float> cameraAABBPosition = new Vector2D<Float>(cameraComponent.GetCoordinates().x - cameraComponent.GetDimensions().x/2, cameraComponent.GetCoordinates().y - cameraComponent.GetDimensions().y/2);
        return new Vector2D<Integer>( (cameraTilePositionn.x*64) - (int)(float)cameraAABBPosition.x - 640, (cameraTilePositionn.y*64) - (int)(float)cameraAABBPosition.y - 360);
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void buildMatrix(){
        matrixOpacity = new int[(int)(float)Level.mCurrentLevel.GetBounds().GetScale().x/64][(int)(float)Level.mCurrentLevel.GetBounds().GetScale().y/64];
        for (int i = 0; i<matrixOpacity.length; i++){
            for(int j=0; j<matrixOpacity[0].length; j++){
                matrixOpacity[i][j] = opacity;
            }
        }
    }

    public int getOpacityAt (Vector2D<Integer> tilePosition){
        return matrixOpacity[tilePosition.x][tilePosition.y];
    }
}