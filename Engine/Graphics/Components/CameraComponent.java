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
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class CameraComponent extends Component {
    protected AffineTransform mViewMatrix;
    protected AffineTransform mCameraMatrix;
    protected Vector2D<Float> mPanning;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Constructs a Camera Component with a parent object
    */ //----------------------------------------------------------------------
    public CameraComponent(Actor parent) {
        super(parent);
        mPanning = new Vector2D<Float>(0.f, 0.f);
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {
        mViewMatrix = mCameraMatrix = new AffineTransform();
    }

    public void SetPanning(final Vector2D<Float> pan) {
        mPanning = pan;
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        final Vector2D<Float> pos = GetCoordinates();
        mCameraMatrix = AffineTransform.getTranslateInstance(pos.x, pos.y);
        mCameraMatrix.concatenate(AffineTransform.getTranslateInstance(mPanning.x, mPanning.y));

        //Try to invert the CameraMatrix, else, set it to the identity \_(-_-)_/
        try {
            mViewMatrix = mCameraMatrix.createInverse();
        } catch (NoninvertibleTransformException e) {
            mViewMatrix.setToIdentity();
        }
    }

    // ------------------------------------------------------------------------
    /*! Bind
    *
    *   Binds this Camera to the Graphics Pipeline
    */ //----------------------------------------------------------------------
    public void Bind() {
        GraphicsPipeline.GetGraphicsPipeline().BindCamera(this);
    }

    // ------------------------------------------------------------------------
    /*! Get View Matrix
    *
    *   Returns the Camera View Matrix, which is the inverse of the transform
    */ //----------------------------------------------------------------------
    public AffineTransform GetViewMatrix() {
        return mViewMatrix;
    }

    // ------------------------------------------------------------------------
    /*! Get Camera Matrix
    *
    *   Returns the Camera Matrix, which is a matrix of the transform
    */ //----------------------------------------------------------------------
    public AffineTransform GetCameraMatrix() {
        return mCameraMatrix;
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

        return new Vector2D<>( pos.x - (camera.x/2f), pos.y - (camera.y/2f) );
    }

    public Vector2D<Float> GetPanning() {
        return mPanning;
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
    
    public Vector2D<Float> getDrawPoint(){
        //return  new Vector2D<Float>(this.GetCoordinates().x - this.GetDimensions().x/2, this.GetCoordinates().y - this.GetDimensions().y/2);
        return this.GetParent().GetPosition();
    }
}
