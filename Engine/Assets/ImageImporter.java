package Engine.Assets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;

public class ImageImporter implements Importer {
    @Override
    public Asset ImportFromFile(String file) {
        BufferedImage sprite = null;

        //Add a try/catch clause, as it might fail to get the resource in question
        try {
            Log v = Logger.Instance().GetLog("Assets");
            Logger.Instance().Log(v, "Imported: " + file, Level.INFO, 2, Color.CYAN);
            sprite = MatchColorPalette(ImageIO.read(getClass().getClassLoader().getResourceAsStream(file)));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return new Asset(sprite);
    }

    private BufferedImage MatchColorPalette(BufferedImage image) {
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
        getLocalGraphicsEnvironment().getDefaultScreenDevice().
        getDefaultConfiguration();

        /*
        * if image is already compatible and optimized for current system 
        * settings, simply return it
        */
        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage newImage = gfxConfig.createCompatibleImage(
            image.getWidth(), image.getHeight(), image.getTransparency());

        newImage = new BufferedImage(newImage.getWidth(), newImage.getHeight(), newImage.getType()==0?BufferedImage.TYPE_INT_ARGB_PRE:newImage.getType());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = newImage.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return newImage; 
    }
}
