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
import java.util.HashMap;
import java.util.LinkedList;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
/* Hitbox Implementation
 *      -> Add a hitbox properties in the Actor
 *      -> Use BoxCollider(Actor parent,Vector2D<Float> scale, boolean itCollides) 
 *          -> set itCollides true in case of it has Collision
 *      -> Add hitBox.Update() at the end of the Actor.Update()
 *      -> Add pseudoPositionUpdate() at the end of the Actor.Update()
 * 
 */
public class BoxCollider extends Component implements Renderable{

    private AABB mBounds;
    private boolean hasCollision;

    private Color color = Color.BLUE;
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

    public BoxCollider(Actor parent,Vector2D<Float> scale, boolean hasCollides){ //This construct will bild a Hitbox
        super(parent);
        Vector2D<Float> drawnPoint = new Vector2D<Float>(parent.getPseudoPosition().x-(scale.x/2), parent.getPseudoPosition().y-(scale.y/2));
        mBounds = new AABB(drawnPoint, scale);
        color = Color.magenta;
        this.hasCollision = hasCollides;
        ColliderManager.GetColliderManager().addCollider(this, hasCollides);
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
        //mBounds.SetHeight(this.GetBounds().GetWidth()); Para que sirve esto???
        //mBounds.SetWidth(this.GetBounds().GetHeight());
        if(hasCollision){
            mBounds.SetPosition(new Vector2D<Float>(super.GetParent().getPseudoPosition().x-(mBounds.GetWidth()/2), super.GetParent().getPseudoPosition().y-(mBounds.GetHeight()/2)));
        }
    }

    public void Reset(){
        mBounds.SetBox(super.GetParent().GetPosition(), mBounds.GetScale());
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

    public void setColor (Color color ){
        this.color = color;
    }
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        var campos = camerapos.GetCoordinates();
       g.setColor(color);
       g.drawRect((int)(float)(mBounds.GetPosition().x - campos.x), (int)(float)(mBounds.GetPosition().y - campos.y), (int)mBounds.GetWidth(), (int)mBounds.GetHeight());
    }

}
