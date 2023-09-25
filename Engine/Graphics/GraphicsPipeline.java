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
import java.util.Vector;

import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;

public class GraphicsPipeline {
    private ArrayList<Renderable> mRenderables;
    static private GraphicsPipeline sPipe = new GraphicsPipeline();
    private CameraComponent mCamera;
    private Vector2D<Integer> mDimensions;

    // ------------------------------------------------------------------------
    /*! Get Graphics Pipeline
    *
    *   Returns a singleton instance of the graphics pipeline
    */ //----------------------------------------------------------------------
    static public GraphicsPipeline GetGraphicsPipeline() {
        return sPipe;
    }

    public void SetDimensions(Vector2D<Integer> dim) {
        mDimensions = dim;
    }

    public Vector2D<Integer> GetDimensions() {
        return mDimensions;
    }

    // ------------------------------------------------------------------------
    /*! Default constructor
    *
    *   creates an arraylist of renderables
    */ //----------------------------------------------------------------------
    private GraphicsPipeline() {
        mRenderables = new ArrayList<>();
        mCamera = null;
    }

    public void BindCamera(CameraComponent c) {
        mCamera = c;
    }

    // ------------------------------------------------------------------------
    /*! Add Renderable
    *
    *   Adds a Renderable to the pipeline
    */ //----------------------------------------------------------------------
    public void AddRenderable(Renderable r) {
        mRenderables.add(r);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders every component
    */ //----------------------------------------------------------------------
    public void Render(Graphics2D g) {
        //Renderable
        for(Renderable x : mRenderables)
            x.Render(g, mCamera == null ? new Vector2D<Float>() : mCamera.GetCoordinates());
    }

    // ------------------------------------------------------------------------
    /*! Remove Renderable
    *
    *   Removes one renderable
    */ //----------------------------------------------------------------------
    public void RemoveRenderable(Renderable r) {
        mRenderables.remove(r);
    }
}
