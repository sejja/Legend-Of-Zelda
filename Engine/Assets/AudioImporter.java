package Engine.Assets;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioImporter implements Importer{
     @Override
    public Asset ImportFromFile(String file) {
        Clip clip = null;
        try {
            AudioInputStream audiostream = AudioSystem.getAudioInputStream(new File(file));
            clip = AudioSystem.getClip(); 
            clip.open(audiostream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return new Asset(clip);
    }
}
