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

    public AnimationMachine(Entity parent, Sprite sprite) {
        super(parent);
        mSprite = sprite;
        mAnimation = new Animation();
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    public Animation GetAnimation() {
        return mAnimation;
    }

    public Sprite GetSpriteSheet() {
        return mSprite;
    }

    // ------------------------------------------------------------------------
    /*! Set Sprite
    *
    *   Sets the Sprite that we are going to animate
    */ //----------------------------------------------------------------------
    public void SetAnimationSprite(Sprite sp) {
        mSprite = sp;
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
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    @Override
    public void Render(Graphics2D g) {
        g.drawImage(mAnimation.GetCurrentFrame(), (int)GetParent().GetPosition().x, (int)GetParent().GetPosition().y, (int)GetParent().GetScale().x, (int)GetParent().GetScale().y, null);
    }
    
}
