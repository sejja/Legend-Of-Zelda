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
import java.nio.Buffer;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import Engine.Assets.Asset;
import Engine.Assets.AssetManager;
import Engine.Math.Vector2D;

public class Sprite { 
    protected Asset mSpriteSheet = null;
    protected int mWidth;
    protected int mHeight;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Sprite(String file) {
        mSpriteSheet = LoadSprite(file);
        mWidth = ((BufferedImage)mSpriteSheet.Raw()).getWidth();
        mHeight = ((BufferedImage)mSpriteSheet.Raw()).getHeight();
    }

    // ------------------------------------------------------------------------
    /*! Load Sprite
    *
    *   Loads an sprite from the fisk and uploads it as a bufferedimage
    */ //----------------------------------------------------------------------
    protected Asset LoadSprite(String file) {
        return AssetManager.Instance().GetResource(file);
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite Sheet
    *
    *   Returns the Sprite Sheet, as an Image
    */ //----------------------------------------------------------------------
    public Asset GetSprite() {
        return mSpriteSheet;
    }
}
