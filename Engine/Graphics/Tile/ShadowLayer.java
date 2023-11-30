package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import Engine.ECSystem.Types.Entity;
import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.TileCoordinates;
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
    private boolean isOn = true;

    public ShadowLayer(int defaultOpacity){
        this.opacity = defaultOpacity;
        shadowLayer = this;
    }

    public static ShadowLayer getShadowLayer (){return shadowLayer;}

    public void illuminate(Vector2D<Integer> tilePosition, int opacity) {

        if(matrixOpacity == null){
            buildMatrix();
        }
        if(tilePosition.x >= 50 ||  tilePosition.y >= 50){
            //System.err.println("Estas intentando iluminar fuera del mapa");
            return;
        }
        try{
            matrixOpacity[tilePosition.x][tilePosition.y] += opacity;
        }catch(java.lang.ArrayIndexOutOfBoundsException e){
            System.err.println("tilePosition out of the bounds error -> " + tilePosition);
            matrixOpacity[tilePosition.x][tilePosition.y] = this.opacity;
        }
    }

    public void Render(Graphics2D g, CameraComponent mCamera) {
        //System.out.println(isOn + " " + mCamera);
        if(matrixOpacity == null){buildMatrix();}
        if(!isOn || mCamera == null){return;}
        final int scaleX = 64;
        final int scaleY = 64;

        int cameraSizeX = mCamera.GetDimensions().x;
        int cameraSizeY = mCamera.GetDimensions().y;


        Vector2D<Integer> cameraDrawPoint = getDrawPointPosition(mCamera); //Superior Izquierdo donde empieza a dibujar la camara
        Vector2D<Integer> windowShadowDrawPoint = getWindowViewedDrawPoint(cameraDrawPoint, mCamera); //Position de cada bloque de oscuridad que va dibujando con respecto a la ventana
        //System.out.println("Window shadowDrawPoint" + windowShadowDrawPoint);
        //System.out.println("Camera drawPoint" + cameraDrawPoint);
        //Primero dibuja cada columna y luego cada fila
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
        if (ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).size() == 0){return new Vector2D<Integer>(0,0);}
        Player link = (Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);

        Vector2D<Integer> blockPosition = World.GetLevelSpaceCoordinates(link.getPseudoPosition()).getTilePosition();

        //Offset de la camara con respecto a link
        int gapX = mCamera.GetDimensions().x/(2*64);
        int gapY = mCamera.GetDimensions().y/(2*64);

        int cameraDrawPointX = blockPosition.x-1 - gapX;
        int cameraDrawPointY = blockPosition.y-1 - gapY;
        int limitX = (int)(float)(World.mCurrentLevel.GetBounds().GetScale().x - mCamera.GetDimensions().x)/64;
        int limitY = (int)(float)(World.mCurrentLevel.GetBounds().GetScale().y - mCamera.GetDimensions().y)/64;
        if(cameraDrawPointX <0){cameraDrawPointX = 0;}
        else if(cameraDrawPointX > limitX){cameraDrawPointX = limitX;}
        if(cameraDrawPointY <0){cameraDrawPointY = 0;}
        else if(cameraDrawPointY > limitY){cameraDrawPointY = limitY;}

        Vector2D<Float> result =  new Vector2D<Float>((Float)(float)(cameraDrawPointX)*64, (Float)(float)(cameraDrawPointY-1)*64); // <---- quitar +1
        //To see where is the cameraDrawPoint
        //____________________________________________________________________________________________________________
        /*
        if(seeker == null){
            seeker = new BoxCollider(link, result);
            link.AddComponent(seeker);
        }else{
            seeker.GetBounds().SetPosition(result);
        }
        */
        //____________________________________________________________________________________________________________

        Vector2D<Integer> _result =  new Vector2D<Integer>((int)(float)(result.x/64), (int)(float)(result.y/64));
        return _result;
        }


    public void Clear(int opacity){
        this.opacity = opacity;
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
            //e.printStackTrace();
            return opacity;
        }
    }
    //Este es un cast a formato tile de pixeles
    private Vector2D<Integer> getWindowViewedDrawPoint (Vector2D<Integer> cameraTilePositionn, CameraComponent cameraComponent){
        //cameraComponentCoordinate = The middle of the camera
        Vector2D<Float> cameraAABBPosition = new Vector2D<Float>(cameraComponent.GetCoordinates().x , cameraComponent.GetCoordinates().y );
        cameraAABBPosition = World.GetLevelSpaceCoordinates(cameraAABBPosition);
        //System.out.println("Camera drawPoint" + cameraTilePositionn);
        //System.out.println("camera AABB position = " + cameraAABBPosition);
        Vector2D<Integer> result = new Vector2D<Integer>( (cameraTilePositionn.x*64) - (int)(float)(cameraAABBPosition.x) , (cameraTilePositionn.y*64) - (int)(float)(cameraAABBPosition.y ));
        //System.out.println("_result = " + ((cameraTilePositionn.x*64) - (int)(float)(cameraAABBPosition.x)) + " , " + ((cameraTilePositionn.y*64) - (int)(float)(cameraAABBPosition.y )));
        //System.out.println("result = " + result);
        return result;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void buildMatrix(){
        matrixOpacity = new int[(int)(float)(World.mCurrentLevel.GetBounds().GetScale().x/64f)][(int)(float)(World.mCurrentLevel.GetBounds().GetScale().y/64f)];
        //matrixOpacity = new int[50][50];
        for (int i = 0; i<matrixOpacity.length; i++){
            for(int j=0; j<matrixOpacity[0].length; j++){
                matrixOpacity[i][j] = opacity;
            }
        }
    }

    public void resetMatrix(){
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