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
import Engine.Math.Vector2D;

public class Spritesheet {
    protected BufferedImage mSpriteSheet = null;
    protected BufferedImage[][] mSpriteArray;
    protected int mUCoord;
    protected int mVCoord;
    protected int mWidth;
    protected int mHeight;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Spritesheet(String file) {
        mVCoord = mUCoord = 32;
        mSpriteSheet = LoadSprite(file);
        mWidth = mSpriteSheet.getWidth() / mUCoord;
        mHeight = mSpriteSheet.getHeight() / mVCoord;
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Spritesheet(String file, int nrow, int ncol, boolean whatever) {
        System.out.println("DFBJDH");
        mSpriteSheet = LoadSprite(file);  
        mUCoord = mSpriteSheet.getWidth()/nrow;
        mVCoord = mSpriteSheet.getHeight()/ncol;

        mWidth = mSpriteSheet.getWidth() / mUCoord;
        mHeight = mSpriteSheet.getHeight() / mVCoord; 
        LoadSpriteArray();
    }


    public Spritesheet(String file, int w, int h) {
        mUCoord = w;
        mVCoord = h;

        mSpriteSheet = LoadSprite(file);
        mWidth = mSpriteSheet.getWidth() / mUCoord;
        mHeight = mSpriteSheet.getHeight() / mVCoord; 
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs a copy of an sprite
    */ //----------------------------------------------------------------------
    public Spritesheet(Spritesheet original, String path) {
        mUCoord = original.mUCoord;
        mVCoord = original.mVCoord;
        mWidth = original.mWidth;
        mHeight = original.mHeight;

        mSpriteSheet = new Spritesheet(path, mUCoord, mVCoord).GetSpriteSheet();
        LoadSpriteArray();
    }


    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetSize(int width, int height) {
        mUCoord = width;
        mVCoord = height;
    }

    // ------------------------------------------------------------------------
    /*! Set Width
    *
    *   Sets the Width of an sprite
    */ //----------------------------------------------------------------------
    public void SetWidth(int i) {
        mUCoord = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height of an Sprite
    */ //----------------------------------------------------------------------
    public void SetHeight(int h) {
        mVCoord = h;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width of an sprite
    */ //----------------------------------------------------------------------
    public int GetWidth() {
        return mUCoord;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height in pixels
    */ //----------------------------------------------------------------------
    public int getHeight() {
        return mVCoord;
    }

    // ------------------------------------------------------------------------
    /*! Load Sprite
    *
    *   Loads an sprite from the fisk and uploads it as a bufferedimage
    */ //----------------------------------------------------------------------
    protected BufferedImage LoadSprite(String file) {
        BufferedImage sprite = null;

        //Add a try/catch clause, as it might fail to get the resource in question
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return sprite;
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
    public BufferedImage GetSpriteSheet() {
        return mSpriteSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite
    *
    *   Given an UV coordinate, returns the sprite located at a point in the spritesheet
    */ //----------------------------------------------------------------------
    public BufferedImage GetSprite(int x, int y) {
        return mSpriteSheet.getSubimage(x * mUCoord, y * mVCoord, mUCoord, mVCoord);
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
