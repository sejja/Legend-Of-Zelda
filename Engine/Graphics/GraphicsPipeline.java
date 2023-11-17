//
//	GraphicsPipeline.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import Engine.Developer.Logger.Logger;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.Vector2D;

public class GraphicsPipeline {
    private ArrayList<Renderable> mRenderables;
    private ArrayList<Renderable> mNewRenderables = new ArrayList<>();
    private ArrayList<Renderable> mOldRenderables = new ArrayList<>();

    static private GraphicsPipeline sPipe = new GraphicsPipeline();
    private CameraComponent mCamera;
    private Vector2D<Integer> mDimensions;

    private ShadowLayer shadowLayer;

    // ------------------------------------------------------------------------
    /*! Get Graphics Pipeline
    *
    *   Returns a singleton instance of the graphics pipeline
    */ //----------------------------------------------------------------------
    static public GraphicsPipeline GetGraphicsPipeline() {
        return sPipe;
    }

    // ------------------------------------------------------------------------
    /*! Set Dimensions
    *
    *   Sets the rendering dimensions
    */ //----------------------------------------------------------------------
    public void SetDimensions(final Vector2D<Integer> dim) {
        mDimensions = dim;
    }

    // ------------------------------------------------------------------------
    /*! Get Dimensions
    *
    *   Retrieve the Rendering Dimensions
    */ //----------------------------------------------------------------------
    public Vector2D<Integer> GetDimensions() {
        return mDimensions;
    }

    // ------------------------------------------------------------------------
    /*! Default constructor
    *
    *   creates an arraylist of renderables
    */ //----------------------------------------------------------------------
    private GraphicsPipeline() {
        mDimensions = new Vector2D<>(0, 0);
        mRenderables = new ArrayList<>();
        mCamera = null;
        shadowLayer = new ShadowLayer(170);
    }

    // ------------------------------------------------------------------------
    /*! Bind Camera
    *
    *   Binds the rendering camera
    */ //----------------------------------------------------------------------
    public void BindCamera(final CameraComponent c) {
        mCamera = c;
    }

    // ------------------------------------------------------------------------
    /*! Add Renderable
    *
    *   Adds a Renderable to the pipeline
    */ //----------------------------------------------------------------------
    public void AddRenderable(final Renderable r) {
        mNewRenderables.add(r);
    }

    // ------------------------------------------------------------------------
    /*! Add Renderable Bottom
    *
    *   Adds a Renderable to the botttom of the rendering pipeline
    */ //----------------------------------------------------------------------
    public void AddRenderableBottom(final Renderable r) {
        mRenderables.add(0, r);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders every component
    */ //----------------------------------------------------------------------
    public void Render(final Graphics2D g) {
        mRenderables.addAll(mNewRenderables);
        mNewRenderables.clear();

        //If there is indeed a Binded Camera
        if(mCamera != null)
            mRenderables.stream().forEach(r -> r.Render(g, mCamera));

        mOldRenderables.stream().forEach(r -> mRenderables.remove(r));
        mOldRenderables.clear();
        shadowLayer.Render(g, mCamera);
        Logger.Render(g);
    }

    // ------------------------------------------------------------------------
    /*! Remove Renderable
    *
    *   Removes one renderable
    */ //----------------------------------------------------------------------
    public void RemoveRenderable(final Renderable r) {
        mOldRenderables.add(r);
        
    }

    // ------------------------------------------------------------------------
    /*! Remove All Renderables
    *
    *   Removes all renderables from the renderable list
    */ //----------------------------------------------------------------------
    public void RemoveAllRenderables() {
        mRenderables.forEach(x -> mOldRenderables.add(x));
    }

    // ------------------------------------------------------------------------
    /*! Get Camera
    *
    *   Returns the Binded Camera
    */ //----------------------------------------------------------------------
    public CameraComponent GetBindedCamera() {
        return mCamera;
    }

    // ------------------------------------------------------------------------
    /*! Unbind Camera
    *
    *   Unbinds the current rendering camera, if there is any
    */ //----------------------------------------------------------------------
    public void UnbindCamera(final CameraComponent c) {
        mCamera = (mCamera == c) ? null : mCamera;
    }
}
