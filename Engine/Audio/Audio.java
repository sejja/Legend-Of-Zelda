package Engine.Audio;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
    private static Audio sInstance = new Audio();
    private TreeMap<Sound, Clip> mSounds;

    private Audio() {
        mSounds = new TreeMap<Sound, Clip>(new Comparator<Sound>() {

            @Override
            public int compare(Sound o1, Sound o2) {
                return o1.mAudio.compareTo(o2.mAudio);
            }
            
        });
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
                Clip s = mSounds.get(sound);
                if(s.isActive()) {
                    return;
                } else if(s.isOpen()) {
                    s.stop();
                    s.close();
                }
                s.open(AudioSystem.getAudioInputStream((File)sound.GetWaveformat().Raw()));
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
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            Play(sound);
        }
    } 
      
    public void Stop(Sound sound) { 
        if(mSounds.containsKey(sound)) {
            sound.SetCurrentFrame(0L); 
            mSounds.get(sound).stop(); 
            mSounds.get(sound).close(); 
        }
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
