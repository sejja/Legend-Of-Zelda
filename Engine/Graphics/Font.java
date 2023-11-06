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
import java.util.function.IntConsumer;
import Engine.Assets.Asset;
import Engine.Math.Vector2D;

public class Font extends Spritesheet {
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Font(final Asset file) {
        super(file);
    }

    // ------------------------------------------------------------------------
    /*! ConstructormU
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Font(final Asset file, final Vector2D<Integer> dimensions) {
        super(file, dimensions);
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetBearing(final Vector2D<Integer> dimensions) {
        mUVCoord = dimensions;
    }

    // ------------------------------------------------------------------------
    /*! Set Width
    *
    *   Sets the Width of an sprite
    */ //----------------------------------------------------------------------
    public void SetWidth(final int i) {
        mUVCoord.x = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height of an Sprite
    */ //----------------------------------------------------------------------
    public void SetHeight(final int h) {
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
    /*! Get Sprite Sheet
    *
    *   Returns the Sprite Sheet, as an Image
    */ //----------------------------------------------------------------------
    public Asset GetFontSheet() {
        return mSpriteSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Font
    *
    *   Returns the image of the letter from the font itself. It crops a subimage
    */ //----------------------------------------------------------------------
    private BufferedImage GetFont(final char c) {
        final int value = c - (int)'A';

        return ((BufferedImage)mSpriteSheet.Raw()).getSubimage((value % mWidth) * mUVCoord.x, 
            (value / mWidth) * mUVCoord.y, mUVCoord.x, mUVCoord.y);
    }

    // ------------------------------------------------------------------------
    /*! Draw Array
    *
    *   Draws text
    */ //----------------------------------------------------------------------
    public void Render(final Graphics2D g, final String string, Vector2D<Float> pos, final Vector2D<Integer> scale, final Vector2D<Integer> offset) {
        string.chars().forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                if(value != ' ')
                    g.drawImage(GetFont((char)value), (int)(float)pos.x, (int)(float)pos.y, scale.x, scale.y, null);
            
                pos.x += offset.x;
                pos.y += offset.y;
            }
        });
    }
}