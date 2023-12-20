package Gameplay.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Stack;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Gameplay.Enemies.Search.Pair;

/**
 * Represents a rendering component for displaying the path of an enemy.
 * Extends the Component class and implements the Renderable interface.
 */
public class PathRender extends Component implements Renderable{
    Stack<Pair> path;

    /**
     * Constructs a PathRender object for rendering the path of an enemy.
     *
     * @param parent The actor parent associated with this component.
     */
    protected PathRender(Actor parent) {
        super(parent);
        path = ((Enemy)parent).path;
    }

    /**
     * Initializes the PathRender component by adding it to the graphics pipeline for rendering.
     */
    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    /**
     * Updates the path by retrieving it from the parent enemy.
     */
    @Override
    public void Update() {
        path = ((Enemy)GetParent()).path;
    }

    /**
     * Shuts down the PathRender component by removing it from the graphics pipeline.
     */
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);;
    }

    /**
     * Renders the path on the screen.
     *
     * @param g          The graphics context used for rendering.
     * @param camerapos  The camera component providing the camera position.
     */
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        var camcoord = camerapos.GetCoordinates();
        g.setColor(Color.green);
        Stack<Pair> mPath = (Stack<Pair>)path.clone();

        while (!mPath.isEmpty()) {
            Pair x = mPath.pop();
            Pair p = Engine.ECSystem.World.GetWorldPair(x);
            g.drawRect(p.getFirst() * 64 - (int)(float)camcoord.x, p.getSecond() * 64 - (int)(float)camcoord.y, 64, 64);
        }
    }
    
}
