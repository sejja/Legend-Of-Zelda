//
//	Spritesheet.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics;

import Engine.Assets.Asset;
import Engine.Assets.AssetManager;

public class Sprite { 
    protected Asset mSpriteSheet;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Sprite(final String file) {
        mSpriteSheet = AssetManager.Instance().GetResource(file);
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
