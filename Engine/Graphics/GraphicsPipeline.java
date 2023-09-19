package Engine.Graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Graphics.Components.Renderable;

public class GraphicsPipeline {
    private ArrayList<Renderable> mRenderables;
    static private GraphicsPipeline sPipe = new GraphicsPipeline();

    static public GraphicsPipeline GetGraphicsPipeline() {
        return sPipe;
    }

    private GraphicsPipeline() {
        mRenderables = new ArrayList<>();
    }

    public void AddRenderable(Renderable r) {
        mRenderables.add(r);
    }

    public void Render(Graphics2D g) {
        for(Renderable x : mRenderables)
            x.Render(g);
    }

    public void RemoveRenderable(Renderable r) {
        mRenderables.remove(r);
    }
}
