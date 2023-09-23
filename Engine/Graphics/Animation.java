//
//	Animation.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] mFrames;
    private int mCurrentFrame;
    private int mMaxFrames;
    private int mCount;
    private int mDelay;
    private int mTimesPlayed;

     // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Creates an animation from an array of frames
    */ //----------------------------------------------------------------------
    public Animation(BufferedImage[] frames) {
        mTimesPlayed = 0;
        SetFrames(frames);
        mDelay = 2;
    }

     // ------------------------------------------------------------------------
    /*! Default Constructor
    *
    *   Resets the time played
    */ //----------------------------------------------------------------------
    public Animation() {
        mTimesPlayed = 0;
        mDelay = 2;
    }

    // ------------------------------------------------------------------------
    /*! Set Frames
    *
    *   Sets the array of frames that we will use to animate
    */ //----------------------------------------------------------------------
    public void SetFrames(BufferedImage[] frames) {
        mFrames = frames;
        mMaxFrames = frames.length;
        mCount = 0;
        mTimesPlayed = 0;
        mCurrentFrame = 0;
    }

    // ------------------------------------------------------------------------
    /*! Set Delay
    *
    *   Sets tthe ammount of frames that separate each animation sprite
    */ //----------------------------------------------------------------------
    public void SetDelay(int i) {
        mDelay = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Frame
    *
    *   Sets the current animating frame
    */ //----------------------------------------------------------------------
    public void SetFrame(int i) {
        mCurrentFrame = i;
    }

    // ------------------------------------------------------------------------
    /*! Set Max Frames
    *
    *   Sets the ceiling of frames that we render in an animation
    */ //----------------------------------------------------------------------
    public void SetMaxFrames(int i) {
        mMaxFrames = i;
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the animation, switching the rendered sprite if necessary
    */ //----------------------------------------------------------------------
    public void Update() {
        //If we have a positive delay
        if(mDelay > 0) {
            mCount++;

            //If we have reached the maximun delay
            if(mCount == mDelay) {
                mCurrentFrame++;
                mCount = 0;
            }

            //If we have looped through the whole animation
            if(mCurrentFrame == mMaxFrames) {
                mCurrentFrame = 0;
                mTimesPlayed++;
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Delay
    *
    *   Returns the delay
    */ //----------------------------------------------------------------------
    public int GetDelay() {
        return mDelay;
    }

    // ------------------------------------------------------------------------
    /*! Get Frame
    *
    *   Returns the current frame
    */ //----------------------------------------------------------------------
    public int GetFrame() {
        return mCurrentFrame;
    }

    // ------------------------------------------------------------------------
    /*! Get Frame Count
    *
    *   Returns the ammount of frames that we are animating
    */ //----------------------------------------------------------------------
    public int GetFrameCount() {
        return mMaxFrames;
    }

    // ------------------------------------------------------------------------
    /*! Get Current Frame
    *
    *   Returns the frame in which we are at
    */ //----------------------------------------------------------------------
    public BufferedImage GetCurrentFrame() {
        return mFrames[mCurrentFrame];
    }

    // ------------------------------------------------------------------------
    /*! Has Looped Once?
    *
    *   Returns if the animation has at least done one complete loop
    */ //----------------------------------------------------------------------
    public boolean HasLoopedOnce() {
        return mTimesPlayed < 0;
    }

    // ------------------------------------------------------------------------
    /*! Has Looped N Times?
    *
    *   Returns if the animation has at least looped an n ammount of times
    */ //----------------------------------------------------------------------
    public boolean HasLoopedNTimes(int i) {
        return mTimesPlayed == i;
    }
}