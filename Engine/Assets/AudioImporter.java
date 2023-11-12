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
        File audiostream = new File(file);
        
        return new Asset(audiostream);
    }
}
