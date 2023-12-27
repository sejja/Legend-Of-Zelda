//
//	Spritesheet.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import Engine.Assets.Asset;
import Engine.Math.Vector2D;

public class Spritesheet extends Sprite {
    protected BufferedImage[][] mSpriteArray;
    protected Vector2D<Integer> mUVCoord;
    protected Vector2D<Integer> mSize;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Spritesheet(final Asset file) {
        super(file);
        mUVCoord = new Vector2D<Integer>(32, 32);
        SetUpSpriteSheet();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Spritesheet(final Asset file, final int nrow, final int ncol) {
        super(file);
        mUVCoord = new Vector2D<Integer>(((BufferedImage)mSpriteSheet.Raw()).getWidth()/nrow, ((BufferedImage)mSpriteSheet.Raw()).getHeight()/ncol); 
        SetUpSpriteSheet();
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Builds an SpriteSheet with a file and the dimensions that each frame should have
    */ //----------------------------------------------------------------------
    public Spritesheet(final Asset file, final Vector2D<Integer> dimensions) {
        super(file);
        mUVCoord = dimensions;
        SetUpSpriteSheet();
    }

    // ------------------------------------------------------------------------
    /*! Set Up Sprite Sheet
    *
    *   Given a Sprite Sheet and the UV Coords, calculate the size of each frame
    *   and populate the sprite array
    */ //----------------------------------------------------------------------
    private void SetUpSpriteSheet() {
        final BufferedImage bf = ((BufferedImage)mSpriteSheet.Raw());
        mSize = new Vector2D<Integer>(bf.getWidth() / mUVCoord.x, bf.getHeight() / mUVCoord.y);
        mSpriteArray = new BufferedImage[mSize.x][mSize.y];

        IntStream.range(0, mSize.x).forEach(i -> {
            IntStream.range(0, mSize.y).forEach(j -> {
                mSpriteArray[i][j] = GetSprite(i, j);
            });
        });
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the size of a Sprite
    */ //----------------------------------------------------------------------
    public void SetSize(final int width, final int height) {
        mUVCoord.x = width;
        mUVCoord.y = height;
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
    public Asset GetSpriteSheet() {
        return mSpriteSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite
    *
    *   Given an UV coordinate, returns the sprite located at a point in the spritesheet
    */ //----------------------------------------------------------------------
    public BufferedImage GetSprite(final int x, int y) {
        try {
            return ((BufferedImage)mSpriteSheet.Raw()).getSubimage(x * mUVCoord.x, y * mUVCoord.y, mUVCoord.x, mUVCoord.y);
        } catch (Exception e) {
            float aux = Math.max(Math.min(y, mUVCoord.y - 1), 0);
            if(y != aux) {
                y = y % (mUVCoord.y - 1); 
            }
           return ((BufferedImage)mSpriteSheet.Raw()).getSubimage(x * mUVCoord.x, y * mUVCoord.y, mUVCoord.x, mUVCoord.y);
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Sorite Array
    *
    *   Returns all the sprite array, as a one-dimensional array
    */ //----------------------------------------------------------------------
    public BufferedImage[] GetSpriteArray(final int i) {
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
    /*! Get Sprite Array 2D
    *
    *   Returns the 2D Array reference of the sprite sheet
    */ //----------------------------------------------------------------------
    public void ChangeSpriteFrames(final BufferedImage[][] mSpriteArray) {
        this.mSpriteArray = mSpriteArray;
        mSpriteSheet = null;
    }

    // ------------------------------------------------------------------------
    /*! transposeMatrix
    *
    *   Utility Function for Transposing a Matrix
    */ //----------------------------------------------------------------------
    public void Transpose(){
        final BufferedImage[][] temp = new BufferedImage[mSize.y][mSize.x];

        IntStream.range(0, mSize.x).forEach(i -> {
            IntStream.range(0, mSize.y).forEach(j -> {
                temp[j][i] = mSpriteArray[i][j];
            });
        });

        mSpriteArray = temp;

        //Can anyone guess what does this do? ;)
        mSize.x = mSize.x ^ mSize.y;
        mSize.y = mSize.x ^ mSize.y;
        mSize.x = mSize.x ^ mSize.y;
    }
}
