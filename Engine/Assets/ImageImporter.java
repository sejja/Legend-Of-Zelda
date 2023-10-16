package Engine.Assets;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageImporter implements Importer {
    @Override
    public Asset ImportFromFile(String file) {
        BufferedImage sprite = null;

        //Add a try/catch clause, as it might fail to get the resource in question
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return new Asset(sprite);
    }
}
