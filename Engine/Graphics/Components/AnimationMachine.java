//
//	AnimationMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import java.awt.Graphics2D;

import Engine.ECSystem.Component;
import Engine.ECSystem.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;

public final class AnimationMachine extends Component implements Renderable {
    private Spritesheet mSprite;
    private Animation mAnimation;

    // ------------------------------------------------------------------------
    /*! Animation Machine
    *
    *   Creates an Animation Machine with a parent and a sprite
    */ //----------------------------------------------------------------------
    public AnimationMachine(Actor parent, Spritesheet sprite) {
        super(parent);
        mSprite = sprite;
        mAnimation = new Animation();
    }

    // ------------------------------------------------------------------------
    /*! Get Animation
    *
    *   Returns the animation class
    */ //----------------------------------------------------------------------
    public Animation GetAnimation() {
        return mAnimation;
    }

    // ------------------------------------------------------------------------
    /*! Get Spritesheet
    *
    *   Returns the spritesheet that we are using for animating
    */ //----------------------------------------------------------------------
    public Spritesheet GetSpriteSheet() {
        return mSprite;
    }

    // ------------------------------------------------------------------------
    /*! Set Sprite
    *GraphicsPipeline
    *   Sets the Sprite that we are going to animate
    */ //----------------------------------------------------------------------
    public void SetAnimationSprite(Spritesheet sp) {
        mSprite = sp;
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Adds the Animation to the Graphics Pipeline
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the Animation every frame
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        mAnimation.Update();
    }

    // ------------------------------------------------------------------------
    /*! Shut Down
    *
    *   Removes the Animation from the Rendering Pipeline
    */ //----------------------------------------------------------------------
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the animation
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g) {
        g.drawImage(mAnimation.GetCurrentFrame(), (int)(float)GetParent().GetPosition().x, (int)(float)GetParent().GetPosition().y, (int)(float)GetParent().GetScale().x, (int)(float)GetParent().GetScale().y, null);
    }
    
}
