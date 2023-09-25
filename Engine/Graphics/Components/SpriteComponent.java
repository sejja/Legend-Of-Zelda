package Engine.Graphics.Components;

import java.awt.Graphics2D;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Math.Vector2D;

public class SpriteComponent extends Component implements Renderable {
    

    protected SpriteComponent(Actor parent) {
        super(parent);
    }

    @Override
    public void Init() {
    }

    @Override
    public void Update() {
    }

    @Override
    public void ShutDown() {
    }

    @Override
    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        
    }
    
}
