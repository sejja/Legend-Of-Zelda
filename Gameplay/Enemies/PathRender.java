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
public class PathRender extends Component {
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
    }
}
