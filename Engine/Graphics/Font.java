//
//	Fonts.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Font {
    private BufferedImage mFontSheet = null;
    private BufferedImage[][] mSpriteArray;
    private final int SIZE = 32;
    public int u;
    public int v;
    private int mXBearing;
    private int mYBearing;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Font(String file) {
        u = SIZE;
        v = SIZE;
        
        mFontSheet = LoadFont(file);
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Font(String file, int w, int h) {
        u = w;
        v = h;

        mFontSheet = LoadFont(file);
        mXBearing = mFontSheet.getWidth() / u;
        mYBearing = mFontSheet.getHeight() / v; 
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetBearing(int width, int height) {
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
    private BufferedImage LoadFont(String file) {
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
        mSpriteArray = new BufferedImage[mXBearing][mYBearing];

        //For all the horizontal alignment
        for(int x = 0; x < mXBearing; x++) {

            //For all the vertical alignment
            for(int y = 0; y < mYBearing; y++) {
                mSpriteArray[x][y]  = GetLetter(x, y);
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite Sheet
    *
    *   Returns the Sprite Sheet, as an Image
    */ //----------------------------------------------------------------------
    public BufferedImage GetFontSheet() {
        return mFontSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite
    *
    *   Given an UV coordinate, returns the sprite located at a point in the spritesheet
    */ //----------------------------------------------------------------------
    public BufferedImage GetLetter(int x, int y) {
        return mFontSheet.getSubimage(x * u, y * v, u, v);
    }

    // ------------------------------------------------------------------------
    /*! Get Font
    *
    *   Returns the image of the letter from the font itself. It crops a subimage
    */ //----------------------------------------------------------------------
    public BufferedImage GetFont(char c) {
        int value = c - 65;

        int x = value % mXBearing;
        int y  = value / mXBearing;

        return GetLetter(x, y);
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
}