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

import Engine.Graphics.Components.Renderable;

public class GraphicsPipeline {
    private ArrayList<Renderable> mRenderables;
    static private GraphicsPipeline sPipe = new GraphicsPipeline();

    // ------------------------------------------------------------------------
    /*! Get Graphics Pipeline
    *
    *   Returns a singleton instance of the graphics pipeline
    */ //----------------------------------------------------------------------
    static public GraphicsPipeline GetGraphicsPipeline() {
        return sPipe;
    }

    // ------------------------------------------------------------------------
    /*! Default constructor
    *
    *   creates an arraylist of renderables
    */ //----------------------------------------------------------------------
    private GraphicsPipeline() {
        mRenderables = new ArrayList<>();
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
            x.Render(g);
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
