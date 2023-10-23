//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 21/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class CameraComponent extends Component {
    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Constructs a Camera Component with a parent object
    */ //----------------------------------------------------------------------
    public CameraComponent(Actor parent) {
        super(parent);
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {}

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {}

    // ------------------------------------------------------------------------
    /*! Bind
    *
    *   Binds this Camera to the Graphics Pipeline
    */ //----------------------------------------------------------------------
    public void Bind() {
        GraphicsPipeline.GetGraphicsPipeline().BindCamera(this);
    }

    // ------------------------------------------------------------------------
    /*! On Bounds
    *
    *   Checks if a certain AABB is within bounds of the Camera Component
    */ //----------------------------------------------------------------------
    public boolean OnBounds(AABB bounds) {
        final Vector2D<Integer> camera = GraphicsPipeline.GetGraphicsPipeline().GetDimensions();
        
        return (new AABB(GetCoordinates(), 
            new Vector2D<>((float)(int)camera.x, (float)(int)camera.y)))
            .Collides(bounds);
    }

    // ------------------------------------------------------------------------
    /*! Get Coordinates
    *
    *   Returns the Camera Coordinate Space reference
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetCoordinates() {
        final Vector2D<Float> pos = GetParent().GetPosition();
        final Vector2D<Integer> camera = GraphicsPipeline.GetGraphicsPipeline().GetDimensions();

        return new Vector2D<>(pos.x - camera.x / 2, pos.y - camera.y / 2);
    }

    // ------------------------------------------------------------------------
    /*! Get Dimensions
    *
    *   Returns the Camera View Extent
    */ //----------------------------------------------------------------------
    public Vector2D<Integer> GetDimensions() {
        return GraphicsPipeline.GetGraphicsPipeline().GetDimensions();
    }

    // ------------------------------------------------------------------------
    /*! Shut Down
    *
    *   Unbinds the Camera from the Graphics Pipeline
    */ //----------------------------------------------------------------------
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().UnbindCamera(this);
    }
}
