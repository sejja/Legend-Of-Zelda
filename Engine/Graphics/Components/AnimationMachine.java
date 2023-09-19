package Engine.Graphics.Components;

import java.awt.Graphics2D;

import Engine.ECSystem.Base;
import Engine.ECSystem.Component;
import Engine.ECSystem.Entity;
import Engine.Graphics.Animation;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;

public class AnimationMachine extends Component implements Renderable {
    protected Sprite mSprite;
    protected Animation mAnimation;

    AnimationMachine(Entity parent, Sprite sprite) {
        super(parent);
        mSprite = sprite;
        mAnimation = new Animation();
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    @Override
    public void Init() {
        
    }

    @Override
    public void Update() {
        mAnimation.update();
    }

    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    @Override
    public void Render(Graphics2D g) {
        g.drawImage(mAnimation.GetCurrentFrame(), (int)GetParent().GetPosition().x, (int)GetParent().GetPosition().y, (int)GetParent().GetScale().x, (int)GetParent().GetScale().y, null);
    }
    
}
