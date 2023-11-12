package Engine.Audio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
    private static Audio sInstance = new Audio();
    private HashMap<Sound, Clip> mSounds;

    private Audio() {
        mSounds = new HashMap<Sound, Clip>();
    }

    public static Audio Instance() {
        return sInstance;
    }

    public void Play(Sound sound) {
        try {
            if(!mSounds.containsKey(sound)) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream((File)sound.GetWaveformat().Raw()));
                mSounds.put(sound, clip);
            } else {
                mSounds.get(sound).open(AudioSystem.getAudioInputStream((AudioInputStream)sound.GetWaveformat().Raw()));
            }

            sound.SetIsPaused(false);
            mSounds.get(sound).setMicrosecondPosition(sound.GetCurrentFrame());
            mSounds.get(sound).start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void SetLoopCount(Sound sound, int count) {
        mSounds.get(sound).loop(count);
    }

    public void Pause(Sound sound) {
        sound.SetCurrentFrame((mSounds.get(sound)).getMicrosecondPosition());
        mSounds.get(sound).stop();
        sound.SetIsPaused(true);
    }

    public void Restart(Sound sound) { 
        try {
            mSounds.get(sound).stop(); 
            mSounds.get(sound).close();
            mSounds.get(sound).open(AudioSystem.getAudioInputStream((AudioInputStream)sound.GetWaveformat().Raw()));
            sound.SetCurrentFrame(0L);
            mSounds.get(sound).setMicrosecondPosition(0); 
        } catch (LineUnavailableException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            Play(sound);
        }
    } 
      
    public void Stop(Sound sound) { 
        sound.SetCurrentFrame(0L); 
        mSounds.get(sound).stop(); 
        mSounds.get(sound).close(); 
    } 
      
    public void Forward(Sound sound, long c) { 
        if (c > 0 && c < ((Clip)sound.GetWaveformat().Raw()).getMicrosecondLength())   { 
            mSounds.get(sound).stop();  
            sound.SetCurrentFrame(c); 
            mSounds.get(sound).setMicrosecondPosition(c);
            Play(sound); 
        } 
    } 
}
