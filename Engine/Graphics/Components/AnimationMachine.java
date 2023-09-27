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

    private Spritesheet mSprite;
    private Animation mAnimation;

    /* Must end variables
     * These variables has a purpose of make posible to block a special kind of animation
     * must_complete is a variable thats has to be changed externally using setMust_Complete
     * Reasons:
     *          -> Externally code will be more clean because each setAnimation in each actor has to consider if it must be ended
     *          -> No change in other codes of simple actors such as normal enemies or npcs
     */
    private boolean must_complete = false;
    private BufferedImage[] must_end_frames;
    private BufferedImage[] previus_frames;

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
    /* SetFrames
     *  @Param BufferedImage[] frames <- This is the input frame
     *                                   If must_complete is true, them the input frame must be a must_end kind of animation
     *  if the input is a must_end kind of animation it will savbe a input frame in must_end_variable, the previus frame in previus_frame and set the input as the current frame
     *  if the current frame is a must_end one it will do nothing
     *  if the input is not a must ended one or the must ended animation has end it will set the input to the actual frame (before set it, in update(), it will be the previus animation)
     */
    public void SetFrames(BufferedImage[] frames){ 
        //System.out.println(must_complete);
        if (must_complete && must_end_frames == null)
        {
            System.out.println("Setting must complete animation");
            previus_frames = mAnimation.GetFrames();
            must_end_frames = frames;
            mAnimation.SetFrames(must_end_frames);
        }
        else if (must_complete && must_end_frames != null)
        {   
            // It continues with the same frame
            System.out.println("Continue must complete animation");
            return;
        }
        else
        {
            System.out.println("Cambiando normal");
            must_complete = false;
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
        //System.out.println(must_complete);
        if (mAnimation.Update() && must_complete){
            mAnimation.SetFrames(previus_frames);
            must_complete = false;
            must_end_frames = null;
            previus_frames = null;
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
