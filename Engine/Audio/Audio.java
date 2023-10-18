package Engine.Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
    private static Audio sInstance = new Audio();

    private Audio() {

    }

    public static Audio Instance() {
        return sInstance;
    }

    public void Play(Sound sound) {
        if(sound.GetCurrentFrame() != 0 ) {
            ((Clip)sound.GetWaveformat().Raw()).setMicrosecondPosition(sound.GetCurrentFrame());
        }
        ((Clip)sound.GetWaveformat().Raw()).start();
        sound.SetIsPaused(false);
    }

    public void Pause(Sound sound) {
        sound.SetCurrentFrame(((Clip)sound.GetWaveformat().Raw()).getMicrosecondPosition());
        ((Clip)sound.GetWaveformat().Raw()).stop();
        sound.SetIsPaused(true);
    }

    public void Restart(Sound sound) { 
        ((Clip)sound.GetWaveformat().Raw()).stop(); 
        sound.SetCurrentFrame(0L); 
        Play(sound);
    } 
      
    public void Stop(Sound sound) { 
        sound.SetCurrentFrame(0L); 
        ((Clip)sound.GetWaveformat().Raw()).stop();
    } 
      
    public void Forward(Sound sound, long c) { 
        if (c > 0 && c < ((Clip)sound.GetWaveformat().Raw()).getMicrosecondLength())   { 
            ((Clip)sound.GetWaveformat().Raw()).stop();  
            sound.SetCurrentFrame(c); 
            ((Clip)sound.GetWaveformat().Raw()).setMicrosecondPosition(c);
            Play(sound); 
        } 
    } 
}
