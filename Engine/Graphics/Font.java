//
//	Fonts.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Engine.Math.Vector2D;

public class Font extends Spritesheet {
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Font(String file) {
        super(file);
    }

    // ------------------------------------------------------------------------
    /*! ConstructormU
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Font(String file, int w, int h) {
        super(file, w, h);
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetBearing(int width, int height) {
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
    /*! Get Sprite Sheet
    *
    *   Returns the Sprite Sheet, as an Image
    */ //----------------------------------------------------------------------
    public BufferedImage GetFontSheet() {
        return mSpriteSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite
    *
    *   Given an UV coordinate, returns the sprite located at a point in the spritesheet
    */ //----------------------------------------------------------------------
    public BufferedImage GetLetter(int x, int y) {
        return mSpriteSheet.getSubimage(x * mUCoord, y * mVCoord, mUCoord, mVCoord);
    }

    // ------------------------------------------------------------------------
    /*! Get Font
    *
    *   Returns the image of the letter from the font itself. It crops a subimage
    */ //----------------------------------------------------------------------
    public BufferedImage GetFont(char c) {
        final int value = c - (int)'A';

        return GetLetter(value % mWidth, value / mWidth);
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
    *   Draws text
    */ //----------------------------------------------------------------------
    public void Render(Graphics2D g, String word, Vector2D pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        //For every letter of the word, draw the sprites separately
        for(int i = 0; i < word.length(); i++) {

            //If it's not a white space
            if(word.charAt(i) != 32)
                g.drawImage(GetFont(word.charAt(i)), (int)x, (int)y, width, height, null);
        
            x += xOffset;
            y += yOffset;
        }
    }
}