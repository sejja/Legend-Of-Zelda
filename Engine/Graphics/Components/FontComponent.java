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

public class FontComponent extends Component implements Renderable {
    private Font mFont;
    private String mString;
    private Transform mOffset;
    private Vector2D<Integer> mGlyph;

    // ------------------------------------------------------------------------
    /*! Animation Machine
    *
    *   Creates an Animation Machine with a parent and a sprite
    */ //----------------------------------------------------------------------
    public FontComponent(Actor parent, Asset fontsheet) {
        super(parent);
        mFont = new Font(fontsheet, 16, 16);
        mOffset = new Transform();
        mOffset.mPosition = new Vector2D<Float>(0.f, 0.f);
        mOffset.mScale = new Vector2D<Float>(1.f, 1.f);
        mGlyph = new Vector2D<>(56, 0);
    }

    // ------------------------------------------------------------------------
    /*! Set String
    *
    *   Sets the String to be displayed by the Font Component
    */ //----------------------------------------------------------------------
    public void SetString(String string) {
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
    public void SetPosition(Vector2D<Float> vec) {
        mOffset.mPosition = vec;
    }

    public void SetScale(Vector2D<Float> vec) {
        mOffset.mScale = vec;
    }

    public Vector2D<Float> GetPosition() {
        return mOffset.mPosition;
    }

    public Vector2D<Float> GetScale() {
        return mOffset.mScale;
    }

    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        final float posx = GetParent().GetPosition().x + mOffset.mPosition.x;
        final float posy = GetParent().GetPosition().y + mOffset.mPosition.y;
        final float scax = GetParent().GetScale().x * mOffset.mScale.x;
        final float scay = GetParent().GetScale().y * mOffset.mScale.y;
        Vector2D<Float> camcoord = camerapos.GetCoordinates();

        if(camerapos.OnBounds(new AABB(GetParent().GetPosition(), GetParent().GetScale())))
            mFont.Render(g, mString, new Vector2D<>(posx - camcoord.x, posy - camcoord.y), (int)scax, (int)scay, mGlyph.x, mGlyph.y);
    }

    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    @Override
    public void Update() {
    }

    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }
}
