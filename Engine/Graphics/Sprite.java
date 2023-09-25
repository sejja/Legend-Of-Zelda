package Engine.Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Engine.Math.Vector2D;

public class Sprite {
    protected BufferedImage mSpriteSheet = null;
    protected BufferedImage[][] mSpriteArray;
    protected int mWidth;
    protected int mHeight;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Sprite(String file) {
        mSpriteSheet = LoadSprite(file);
        mWidth = mSpriteSheet.getWidth();
        mHeight = mSpriteSheet.getHeight();
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Consntructs an sprite from the filename, the width, and the height
    */ //----------------------------------------------------------------------
    public Sprite(String file, int w, int h) {
        mSpriteSheet = LoadSprite(file);
        mWidth = mSpriteSheet.getWidth();
        mHeight = mSpriteSheet.getHeight(); 
    }

    // ------------------------------------------------------------------------
    /*! Load Sprite
    *
    *   Loads an sprite from the fisk and uploads it as a bufferedimage
    */ //----------------------------------------------------------------------
    protected BufferedImage LoadSprite(String file) {
        BufferedImage sprite = null;

        //Add a try/catch clause, as it might fail to get the resource in question
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return sprite;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite Sheet
    *
    *   Returns the Sprite Sheet, as an Image
    */ //----------------------------------------------------------------------
    public BufferedImage GetSpriteSheet() {
        return mSpriteSheet;
    }

    // ------------------------------------------------------------------------
    /*! Get Sprite Array 2D
    *
    *   Returns the 2D Array reference of the sprite sheet
    */ //----------------------------------------------------------------------
    public BufferedImage[][] GetSpriteArray2D() {
        return mSpriteArray;
    }

    // ------------------------------------------------------------------------
    /*! Draw Array
    *
    *   Draws an entire array of images
    */ //----------------------------------------------------------------------
    public static void DrawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2D<Float> pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        //For every image
        for(int i = 0; i < img.size(); i++) {

            //If the image is not null
            if(img.get(i) != null) {
                g.drawImage(img.get(i), (int)x, (int)y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

        
    public void setmSpriteArray(BufferedImage[][] mSpriteArray) {
        this.mSpriteArray = mSpriteArray;
    }
}
