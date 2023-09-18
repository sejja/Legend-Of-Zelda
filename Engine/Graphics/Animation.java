//
//	Animation.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Animation {
    private BufferedImage[] mFrames;
    private int mCurrentFrame;
    private int mMaxFrames;

    private int mCount;
    private int mDelay;

    private int mTimesPlayed;

    public Animation(BufferedImage[] frames) {
        mTimesPlayed = 0;
        SetFrames(frames);
    }

    public Animation() {
        mTimesPlayed = 0;
    }

    public void SetFrames(BufferedImage[] frames) {
        mFrames = frames;
        mMaxFrames = frames.length;
        mCount = 0;
        mDelay = 2;
        mTimesPlayed = 0;
        mCurrentFrame = 0;
    }

    public void SetDelay(int i) {
        mDelay = i;
    }

    public void SetFrame(int i) {
        mCurrentFrame = i;
    }

    public void SetMaxFrames(int i) {
        mMaxFrames = i;
    }

    public void update() {
        if(mDelay > 0) {
            mCount++;

            if(mCount == mDelay) {
                mCurrentFrame++;
                mCount = 0;
            }

            if(mCurrentFrame == mMaxFrames) {
                mCurrentFrame = 0;
                mTimesPlayed++;
            }
        }
    }

    public int GetDelay() {
        return mDelay;
    }

    public int GetFrame() {
        return mCurrentFrame;
    }

    public int GetFrameCount() {
        return mMaxFrames;
    }

    public BufferedImage GetCurrentFrame() {
        return mFrames[mCurrentFrame];
    }

    public boolean HasLoopedOnce() {
        return mTimesPlayed < 0;
    }

    public boolean HasLoopedNTimes(int i) {
        return mTimesPlayed == i;
    }
}
