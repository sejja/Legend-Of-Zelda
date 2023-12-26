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
import Engine.Physics.ColliderManager;
/* Hitbox Implementation
 *      -> Add a hitbox properties in the Actor
 *      -> Use BoxCollider(Actor parent,Vector2D<Float> scale, boolean itCollides) 
 *          -> set itCollides true in case of it has Collision
 *      -> Add hitBox.Update() at the end of the Actor.Update()
 *      -> Add pseudoPositionUpdate() at the end of the Actor.Update()
 * 
 */
public class BoxCollider extends Component {

    private AABB mBounds;
    private boolean hasCollision;
    private Color color = Color.BLUE;
    private Vector2D<Float> size;
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

    public BoxCollider(Actor parent,Vector2D<Float> scale, boolean hasCollision){ //This construct will bild a Hitbox
        super(parent);
        Vector2D<Float> drawnPoint = new Vector2D<Float>(parent.getPseudoPosition().x-(scale.x/2), parent.getPseudoPosition().y-(scale.y/2));
        mBounds = new AABB(drawnPoint, scale);
        if(hasCollision){
            color = Color.MAGENTA;
        }
        this.hasCollision = hasCollision;
        ColliderManager.GetColliderManager().addCollider(this, hasCollision);
    }

    public BoxCollider(Actor parent, Vector2D<Float> position, int seeker){ //This construct will bild a Hitbox
        super(parent);
        mBounds = new AABB(position, new Vector2D<Float>(64f, 64f));
    }

    public void setHitboxScale(Vector2D <Float> scale){
        Vector2D<Float> drawnPoint = new Vector2D<Float>(GetParent().getPseudoPosition().x-(scale.x/2), GetParent().getPseudoPosition().y-(scale.y/2));
        mBounds = new AABB(drawnPoint, scale);
    }
    // ------------------------------------------------------------------------
    /*! Init
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the bounds of the AABB
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        //mBounds.SetHeight(this.GetBounds().GetWidth()); Para que sirve esto??? --> Idk bro
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
        try{
            ColliderManager.GetColliderManager().removeCollider(this);
        }catch(NullPointerException e){
            //System.err.print("Already removed");
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Bounds
    *
    *   Returns the Axis Aligned Bouding Box of the Collider
    */ //----------------------------------------------------------------------
    public AABB GetBounds() {
        return mBounds;
    }

    public void setPosition(Vector2D<Float> position){
        this.mBounds.SetPosition(position);
    }

    public void setColor (Color color ){
        this.color = color;
    }

    public void disable(){
        size = mBounds.GetScale();
        mBounds.SetSize(new Vector2D<>(0f,0f));
    }

    public void enable(){
        if(size == null){System.out.println("AlreadyEnabled");}
        else{this.mBounds.SetSize(size);}
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mBounds == null) ? 0 : mBounds.hashCode());
        result = prime * result + (hasCollision ? 1231 : 1237);
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((size == null) ? 0 : size.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BoxCollider){
            BoxCollider other = (BoxCollider) obj;
            return this.GetParent().equals(other.GetParent());
        }
        return false;
    }
}
