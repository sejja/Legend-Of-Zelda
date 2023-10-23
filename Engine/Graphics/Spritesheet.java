//
//	Spritesheet.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Engine.Assets.Asset;
import Engine.Assets.AssetManager;
import Engine.Math.Vector2D;

public class Spritesheet {
    protected Asset mSpriteSheet = null;
    protected BufferedImage[][] mSpriteArray;
    protected Vector2D<Integer> mUVCoord;
    protected int mWidth;
    protected int mHeight;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Spritesheet(Asset file) {
        mUVCoord = new Vector2D<Integer>(32, 32);
        mSpriteSheet = file;
        mWidth = ((BufferedImage)mSpriteSheet.Raw()).getWidth() / mUVCoord.x;
        mHeight = ((BufferedImage)mSpriteSheet.Raw()).getHeight() / mUVCoord.y;
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Spritesheet(Asset file, int nrow, int ncol, boolean whatever) {
        mSpriteSheet = file; 
        mUVCoord = new Vector2D<Integer>(((BufferedImage)mSpriteSheet.Raw()).getWidth()/nrow, ((BufferedImage)mSpriteSheet.Raw()).getHeight()/ncol); 

        mWidth = ((BufferedImage)mSpriteSheet.Raw()).getWidth() / mUVCoord.x;
        mHeight = ((BufferedImage)mSpriteSheet.Raw()).getHeight() / mUVCoord.y; 
        LoadSpriteArray();
    }


    public Spritesheet(Asset file, Vector2D<Integer> dimensions) {
        mUVCoord = dimensions;

        mSpriteSheet = file;
        mWidth = ((BufferedImage)mSpriteSheet.Raw()).getWidth() / mUVCoord.x;
        mHeight = ((BufferedImage)mSpriteSheet.Raw()).getHeight() / mUVCoord.y; 
        LoadSpriteArray();
    }
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs a copy of an sprite
    */ //----------------------------------------------------------------------
    public Spritesheet(Spritesheet original, String path) {
        mUVCoord = original.mUVCoord;
        mWidth = original.mWidth;
        mHeight = original.mHeight;

        mSpriteSheet = new Spritesheet(AssetManager.Instance().GetResource(path), mUVCoord).GetSpriteSheet();
        LoadSpriteArray();
    }


    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetSize(int width, int height) {
        mUVCoord.x = width;
        mUVCoord.y = height;
    }

    // ------------------------------------------------------------------------
    /*! Set Width
    *
    *   Sets the Width of an sprite
    */ //----------------------------------------------------------------------
    public void SetWidth(int i) {
        mUVCoord.x = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height of an Sprite
    */ //----------------------------------------------------------------------
    public void SetHeight(int h) {
        mUVCoord.y = h;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width of an sprite
    */ //----------------------------------------------------------------------
    public int GetWidth() {
        return mUVCoord.x;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height in pixels
    */ //----------------------------------------------------------------------
    public int GetHeight() {
        return mUVCoord.y;
    }

    // ------------------------------------------------------------------------
    /*! Load Sprite Array
    *
    *   Given a Sprite Sheet, loads every individual sprites in an array
    */ //----------------------------------------------------------------------
    private void LoadSpriteArray() {
        mSpriteArray = new BufferedImage[mWidth][mHeight];
        int x;
        for(x = 0; x < mWidth; x++){
            int y;
            for(y = 0; y < mHeight; y++){
                mSpriteArray[x][y]  = GetSprite(x, y);
                //System.out.println("x: " + x + "|" + "y: " + y );
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite Sheet
    *
    *   Returns the Sprite Sheet, as an Image
    */ //----------------------------------------------------------------------
    public Asset GetSpriteSheet() {
        return mSpriteSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite
    *
    *   Given an UV coordinate, returns the sprite located at a point in the spritesheet
    */ //----------------------------------------------------------------------
    public BufferedImage GetSprite(int x, int y) {
        return ((BufferedImage)mSpriteSheet.Raw()).getSubimage(x * mUVCoord.x, y * mUVCoord.y, mUVCoord.x, mUVCoord.y);
    }

    // ------------------------------------------------------------------------
    /*! Get Sorite Array
    *
    *   Returns all the sprite array, as a one-dimensional array
    */ //----------------------------------------------------------------------
    public BufferedImage[] GetSpriteArray(int i) {
        return mSpriteArray[i];
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite Array 2D
    *
    *   Returns the 2D Array reference of the sprite sheet
    */ //----------------------------------------------------------------------
    public BufferedImage[][] GetSpriteArray2D() {
        return mSpriteArray;
    }

    // ------------------------------------------------------------------------
    /*! Draw Array
    *
    *   Draws an entire array of images
    */ //----------------------------------------------------------------------
    public static void DrawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2D<Float> pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        //For every image
        for(int i = 0; i < img.size(); i++) {

            //If the image is not null
            if(img.get(i) != null) {
                g.drawImage(img.get(i), (int)x, (int)y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

        
    public void setmSpriteArray(BufferedImage[][] mSpriteArray) {
        this.mSpriteArray = mSpriteArray;
    }

    // ------------------------------------------------------------------------
    /*! transposeMatrix
    *
    *   Utility Function for Transposing a Matrix
    */ //----------------------------------------------------------------------
    public void flip(){
        BufferedImage[][] m = this.GetSpriteArray2D();
        BufferedImage[][] temp = new BufferedImage[m[0].length+4][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        this.setmSpriteArray(temp);
    }

}
