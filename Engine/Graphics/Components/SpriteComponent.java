//
//	AnimationMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Assets.Asset;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Vector2D;

public final class SpriteComponent extends Component implements Renderable {
    private Asset mSprite;

    // ------------------------------------------------------------------------
    /*! Sprite Component
    *
    *   Creates an SpriteComponent with a parent and a sprite
    */ //----------------------------------------------------------------------
    public SpriteComponent(final Actor parent, final Asset sprite) {
        super(parent);
        mSprite = sprite;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite
    *
    *   Returns the rendered sprite
    */ //----------------------------------------------------------------------
    public Asset GetSprite() {
        return mSprite;
    }

    // ------------------------------------------------------------------------
    /*! Set Sprite
    *
    *   Sets the Sprite that we are going to animate
    */ //----------------------------------------------------------------------
    public void SetSprite(final Asset sp) {
        mSprite = sp;
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Adds the Sprite to the Graphics Pipeline
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {}

    // ------------------------------------------------------------------------
    /*! Shut Down
    *
    *   Removes the Sprite from the Rendering Pipeline
    */ //----------------------------------------------------------------------
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the Sprite
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g, CameraComponent camera) {
        final Actor parent = GetParent();
        final Vector2D<Float> pos = parent.GetPosition();
        final Vector2D<Float> sca = parent.GetScale();
        final Vector2D<Float> camcoord = camera.GetCoordinates();
        g.drawImage((BufferedImage)mSprite.Raw(), (int)(float)pos.x - (int)(float)camcoord.x, (int)(float)pos.y - (int)(float)camcoord.y, (int)(float)sca.x, (int)(float)sca.y, null);
    }
}
