//
//	AnimationMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Animation;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;

public final class AnimationMachine extends Component implements Renderable {

    private BufferedImage[] previus_frames;
    private Spritesheet mSprite;
    private Animation mAnimation;
    private boolean must_complete = false;

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
    public void SetFrames(BufferedImage[] frames){
        /* This function will save a whole animation set when must_complete is true and it won`t save the next frames
         * 
         */
        if (must_complete){
            previus_frames = mAnimation.GetFrames();
        }else{
            mAnimation.SetFrames(frames);
        }
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
        if (mAnimation.Update() && must_complete){
            must_complete = false;
            mAnimation.SetFrames(previus_frames);
        }
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
    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        g.drawImage(mAnimation.GetCurrentFrame(), (int)(float)GetParent().GetPosition().x - (int)(float)camerapos.x, (int)(float)GetParent().GetPosition().y - (int)(float)camerapos.y, (int)(float)GetParent().GetScale().x, (int)(float)GetParent().GetScale().y, null);
    }
    
    public void setMust_Complete(){
        must_complete = true;
    }
    public boolean getMust_Complete(){
        return must_complete;
    }
}
