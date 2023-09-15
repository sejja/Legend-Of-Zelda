//
//	Sprite.java
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

public class Sprite {
    private BufferedImage mSpriteSheet = null;
    private BufferedImage[][] mSpriteArray;
    private final int SIZE = 32;
    public int u;
    public int v;
    private int mWidth;
    private int mHeight;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Sprite(String file) {
        u = SIZE;
        v = SIZE;
        
        mSpriteSheet = LoadSprite(file);
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Sprite(String file, int w, int h) {
        u = w;
        v = h;

        mSpriteSheet = LoadSprite(file);
        mWidth = mSpriteSheet.getWidth() / u;
        mHeight = mSpriteSheet.getHeight() / v; 
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetSize(int width, int height) {
        SetWidth(width);
        SetHeight(height);
    }

    // ------------------------------------------------------------------------
    /*! Set Width
    *
    *   Sets the Width of an sprite
    */ //----------------------------------------------------------------------
    public void SetWidth(int i) {
        u = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height of an Sprite
    */ //----------------------------------------------------------------------
    public void SetHeight(int h) {
        v = h;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width of an sprite
    */ //----------------------------------------------------------------------
    public int GetWidth() {
        return u;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height in pixels
    */ //----------------------------------------------------------------------
    public int getHeight() {
        return v;
    }

    // ------------------------------------------------------------------------
    /*! Load Sprite
    *
    *   Loads an sprite from the fisk and uploads it as a bufferedimage
    */ //----------------------------------------------------------------------
    private BufferedImage LoadSprite(String file) {
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

        for(int x = 0; x < mWidth; x++) {
            for(int y = 0; y < mHeight; y++) {
                mSpriteArray[x][y]  = GetSprite(x, y);
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
        return mSpriteSheet.getSubimage(x * u, y * v, u, v);
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
    public static void DrawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2D pos, int width, int height, int xOffset, int yOffset) {
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
}
