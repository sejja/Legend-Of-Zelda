package Engine.Audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import Engine.Assets.Asset;

public class Sound {
    private Asset mAudio;
    private boolean mIsPaused;

    public Sound(Asset audio) {
        mAudio = audio;
        mIsPaused = true;
    }

    public void SetLoopCount(int times) {
        ((Clip)(mAudio.Raw())).loop(times);
    }

    public boolean IsPaused() {
        return mIsPaused;
    }
}
