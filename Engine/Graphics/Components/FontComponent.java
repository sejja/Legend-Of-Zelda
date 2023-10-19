//
//	AnimationMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 21/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import java.awt.Graphics2D;

import Engine.Assets.Asset;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Transform;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public final class FontComponent extends Component implements Renderable {
    private Font mFont;
    private String mString;
    private Transform mOffset;
    private Vector2D<Integer> mGlyph;

    // ------------------------------------------------------------------------
    /*! Animation Machine
    *
    *   Creates an Animation Machine with a parent and a sprite
    */ //----------------------------------------------------------------------
    public FontComponent(final Actor parent, final Asset fontsheet) {
        super(parent);
        mFont = new Font(fontsheet, new Vector2D<>(16, 16));
        mOffset = new Transform(new Vector2D<Float>(0.f, 0.f), new Vector2D<Float>(1.f, 1.f));
        mGlyph = new Vector2D<>(56, 0);
    }

    // ------------------------------------------------------------------------
    /*! Set String
    *
    *   Sets the String to be displayed by the Font Component
    */ //----------------------------------------------------------------------
    public void SetString(final String string) {
        mString = string;
    } 

    // ------------------------------------------------------------------------
    /*! Get String
    *
    *   Gets the current string that will be displayed by the Font Component
    */ //----------------------------------------------------------------------
    public String GetString() {
        return mString;
    }

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Unbinds the Camera from the Graphics Pipeline
    */ //----------------------------------------------------------------------
    public void SetPosition(final Vector2D<Float> vec) {
        mOffset.mPosition = vec;
    }

    // ------------------------------------------------------------------------
    /*! Set Scale
    *
    *   Sets the offset scale
    */ //----------------------------------------------------------------------
    public void SetScale(final Vector2D<Float> vec) {
        mOffset.mScale = vec;
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Retrieves the  offset position of the font component
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetPosition() {
        return mOffset.mPosition;
    }

    // ------------------------------------------------------------------------
    /*! Gets Scale
    *
    *   Retrieves the relative scale
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetScale() {
        return mOffset.mScale;
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the Font Component
    */ //----------------------------------------------------------------------
    @Override
    public void Render(final Graphics2D g, final CameraComponent camerapos) {
        final Actor parent = GetParent();
        final Vector2D<Float> pos = parent.GetPosition();
        final Vector2D<Float> sca = parent.GetScale();
        final Vector2D<Float> camcoord = camerapos.GetCoordinates();

        //If it's within camera bounds, render
        if(camerapos.OnBounds(new AABB(pos, sca)))
            mFont.Render(g, mString, new Vector2D<>(pos.x + mOffset.mPosition.x - camcoord.x, pos.y + mOffset.mPosition.y - camcoord.y), new Vector2D<Integer>((int)(sca.x * mOffset.mScale.x), (int)(sca.y * mOffset.mScale.y)), mGlyph);
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Adds the renderable to the graphics pipeline
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
    /*! ShutDown
    *
    *   Removes the Renderable from the graphics pipeline
    */ //----------------------------------------------------------------------
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }
}
