package Engine.Assets;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioImporter implements Importer{
     @Override
    public Asset ImportFromFile(String file) {
        AudioInputStream audiostream = null;
        try {
            audiostream = AudioSystem.getAudioInputStream(new File(file));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        
        return new Asset(audiostream);
    }
}
