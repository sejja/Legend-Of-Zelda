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
    private BufferedImage mFontSheet;
    private BufferedImage[][] mSpriteArray;
    private int mUcoord;
    private int mVcoord;
    private int mGlyphX;
    private int mGlyphY;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Font(String file) {
        mVcoord = mUcoord = 32;
        mFontSheet = LoadFont(file);
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Font(String file, int w, int h) {
        mUcoord = w;
        mVcoord = h;
        mFontSheet = LoadFont(file);
        mGlyphX = mFontSheet.getWidth() / mUcoord;
        mGlyphY = mFontSheet.getHeight() / mVcoord; 
        LoadSpriteArray();
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetBearing(int width, int height) {
        mUcoord = width;
        mVcoord = height;
    }

    // ------------------------------------------------------------------------
    /*! Set Width
    *
    *   Sets the Width of an sprite
    */ //----------------------------------------------------------------------
    public void SetWidth(int i) {
        mUcoord = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height of an Sprite
    */ //----------------------------------------------------------------------
    public void SetHeight(int h) {
        mVcoord = h;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width of an sprite
    */ //----------------------------------------------------------------------
    public int GetWidth() {
        return mUcoord;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height in pixels
    */ //----------------------------------------------------------------------
    public int getHeight() {
        return mVcoord;
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
        mSpriteArray = new BufferedImage[mGlyphX][mGlyphY];

        //For all the horizontal alignment
        for(int x = 0; x < mGlyphX; x++)

            //For all the vertical alignment
            for(int y = 0; y < mGlyphY; y++)
                mSpriteArray[x][y]  = GetLetter(x, y);

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
        return mFontSheet.getSubimage(x * mUcoord, y * mVcoord, mUcoord, mVcoord);
    }

    // ------------------------------------------------------------------------
    /*! Get Font
    *
    *   Returns the image of the letter from the font itself. It crops a subimage
    */ //----------------------------------------------------------------------
    public BufferedImage GetFont(char c) {
        final int value = c - (int)'A';

        int x = value % mGlyphX;
        int y  = value / mGlyphX;

        return GetLetter(x, y);
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