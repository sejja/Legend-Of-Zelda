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

public class PathRender extends Component implements Renderable{
    Stack<Pair> path;
    protected PathRender(Actor parent) {
        super(parent);
        path = ((Enemy)parent).path;
    }

    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    @Override
    public void Update() {
        path = ((Enemy)GetParent()).path;
    }

    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);;
    }

    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        var camcoord = camerapos.GetCoordinates();
        g.setColor(Color.green);
        Stack<Pair> mPath = (Stack<Pair>)path.clone();

        while (!mPath.isEmpty()) {
            Pair x = mPath.pop();
            Pair p = Engine.ECSystem.World.GetLevelPair(x);
            g.drawRect(p.getFirst() * 64 - (int)(float)camcoord.x, p.getSecond() * 64 - (int)(float)camcoord.y, 64, 64);
        }
    }
    
}
