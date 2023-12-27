package Engine.Audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import Engine.Assets.Asset;

public class Sound {
    public Asset mAudio;
    private boolean mIsPaused;
    private Long mCurrentFrame;

    public Sound(Asset audio) {
        mAudio = audio;
        mIsPaused = true;
        mCurrentFrame = 0L;
    }

    public void SetCurrentFrame(Long frame) {
        mCurrentFrame = frame;
    }

    public Long GetCurrentFrame() {
        return mCurrentFrame;
    }

    public boolean IsPaused() {
        return mIsPaused;
    }

    public void SetIsPaused(boolean isPaused) {
        mIsPaused = isPaused;
    }

    public Asset GetWaveformat() {
        return mAudio;
    }
}
