//
//	BoxCollider.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Physics.Components;

import java.awt.Color;
import java.awt.Graphics2D;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class BoxCollider extends Component implements Renderable{
    private AABB mBounds;
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Box Collider from a parent actor
    */ //----------------------------------------------------------------------
    public BoxCollider(Actor parent) {
        super(parent);
        mBounds = new AABB(parent.GetPosition(), parent.GetScale());
    }

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Box Collider from a parent actor and a scale
    */ //----------------------------------------------------------------------
    public BoxCollider(Actor parent, Vector2D<Float> scale) {
        super(parent);
        mBounds = new AABB(parent.GetPosition(), scale);
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the bounds of the AABB
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        mBounds.SetHeight(this.GetBounds().GetWidth());
        mBounds.SetWidth(this.GetBounds().GetHeight());
    }

    public void Reset(){
        mBounds.SetBox(super.GetParent().GetPosition(), super.GetParent().GetScale());
    }
    // ------------------------------------------------------------------------
    /*! Shutdown
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Get Bounds
    *
    *   Returns the Axis Aligned Bouding Box of the Collider
    */ //----------------------------------------------------------------------
    public AABB GetBounds() {
        return mBounds;
    }

    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        var campos = camerapos.GetCoordinates();
       g.setColor(Color.blue);
       //g.drawRect((int)(float)(mBounds.GetPosition().x - campos.x), (int)(float)(mBounds.GetPosition().y - campos.y), (int)mBounds.GetWidth() / 2, (int)mBounds.GetHeight());
    }
}
