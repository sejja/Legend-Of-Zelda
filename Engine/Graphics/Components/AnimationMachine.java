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
import java.util.ArrayList;

import Engine.Assets.Asset;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.Animation;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public final class AnimationMachine extends Component implements Renderable {

    private Spritesheet mSprite;
    private Animation mAnimation;

    /* Must end variables
     * These variables have a purpose of make posible to block a special kind of animation
     * must_complete is a variable thats has to be changed externally using setMust_Complete
     * Reasons:
     *          -> Externally code will be more clean because each setAnimation in each actor has to consider if it must be ended
     *          -> No change in other codes of simple actors such as normal enemies or npcs
     */
    private boolean mAnimationLocked = false;
    private BufferedImage[] mBlendFrames;
    private BufferedImage[] mBaseFrames;
    private ArrayList<AnimationEvent> mFinishedEvent;

    // ------------------------------------------------------------------------
    /*! Animation Machine
    *
    *   Creates an Animation Machine with a parent and a sprite
    */ //----------------------------------------------------------------------
    public AnimationMachine(Actor parent, Spritesheet sp) {
        super(parent);
        mSprite = sp;
        mAnimation = new Animation();
        mFinishedEvent = new ArrayList<>();
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
     *                                   If must_complete is true, then the input frame must be a must_end kind of animation
     *  if the input is a must_end kind of animation it will savbe a input frame in must_end_variable, the previus frame in previus_frame and set the input as the current frame
     *  if the current frame is a must_end one it will do nothing
     *  if the input is not a must ended one or the must ended animation has end it will set the input to the actual frame (before set it, in update(), it will be the previus animation)
     */
    public void SetFrames(BufferedImage[] frames){ 
        //System.out.println(must_complete);

        //If the animation has been locked and we don't have frames to blend with
        if (mAnimationLocked && mBlendFrames == null) {
            mBaseFrames = mAnimation.GetFrames();
            mBlendFrames = frames;
            mAnimation.SetFrames(mBlendFrames);
        
        //If we have locked blend frames, unlock the animation
        } else if (!mAnimationLocked && mBlendFrames != null) {   
            mAnimationLocked = false;
            mAnimation.SetFrames(frames);

        //Else, proceed nomally
        } else {
            mAnimationLocked = false;
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
        final int mPreviousFrame = mAnimation.GetFrame();

        //If we update and we change the frame
        if (mAnimation.Update() && mAnimationLocked){
            mAnimation.SetFrames(mBaseFrames);
            mAnimationLocked = false;
            mBlendFrames = null;
            mBaseFrames = null;

            //Trigger the Finished events
            if(mAnimation.GetFrame() == 0 && mPreviousFrame != 0)
                mFinishedEvent.stream().forEach(x -> x.OnTrigger());
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
    public void Render(Graphics2D g, CameraComponent camerapos) {
        final Vector2D<Float> camcoord = camerapos.GetCoordinates();

        if(camerapos.OnBounds(new AABB(GetParent().GetPosition(), GetParent().GetScale())))
            //g.drawImage(mAnimation.GetCurrentFrame(), (int)(float)GetParent().GetPosition().x - (int)(float)camcoord.x - (int)(scale.x / 4), (int)(float)GetParent().GetPosition().y - (int)(float)camcoord.y, (int)(float)GetParent().GetScale().x, (int)(float)GetParent().GetScale().y, null);
            g.drawImage(mAnimation.GetCurrentFrame(), (int)(float)GetParent().GetPosition().x - (int)(float)camcoord.x, (int)(float)GetParent().GetPosition().y - (int)(float)camcoord.y, (int)(float)GetParent().GetScale().x, (int)(float)GetParent().GetScale().y, null);
        }

        
    // ------------------------------------------------------------------------
    /*! Set Must Complete
    *Sets wether this animation should be completed or not*/ 
    //----------------------------------------------------------------------
    public void setMustComplete(boolean b){
        mAnimationLocked = b;
    }

    // ------------------------------------------------------------------------
    /*! Must Complete
    *
    *   Returns wether the animation must complete or not
    */ //----------------------------------------------------------------------
    public boolean MustComplete(){
        return mAnimationLocked;
    }

    // ------------------------------------------------------------------------
    /*! Add Finished Listener
    *
    *   Adds a Listener for finished animation events
    */ //----------------------------------------------------------------------
    public void AddFinishedListener(AnimationEvent e) {
        mFinishedEvent.add(e);
    }
}
