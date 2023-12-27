//
//	PresentBuffer.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 14/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Vector2D;

public class PresentBuffer extends JPanel {
    private static int mWidth;
    private static int mHeight;
    private BufferedImage mFrameBuffer;
    private Graphics2D mGFX;
    private static Color mClearColor;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   As a JPanel, which behaves as a FrameBuffer, request the focus and sets the size
    */ //----------------------------------------------------------------------
    public PresentBuffer(final int width, final int height) {
        setPreferredSize(new Dimension(mWidth = width, mHeight = height));
        setFocusable(true);
        requestFocus();
        mClearColor = Color.BLACK;
        mGFX = (Graphics2D)(mFrameBuffer = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_ARGB)).getGraphics();
    }

    // ------------------------------------------------------------------------
    /*! Set Clear Color
    *
    *   Sets the color of the clear frame buffer
    */ //----------------------------------------------------------------------
    static public void SetClearColor(final Color c) {
        mClearColor = c;
    }

    // ------------------------------------------------------------------------
    /*! Add Notify
    *
    *   Starts the Game Thread, in case there is none. This happens on the start
    */ //----------------------------------------------------------------------
    public void addNotify() {
        super.addNotify();
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the game
    */ //----------------------------------------------------------------------
    public void Render() {
        final Rectangle rect = getBounds();
        final GraphicsPipeline gfxpip = GraphicsPipeline.GetGraphicsPipeline();
        gfxpip.SetDimensions(new Vector2D<>(rect.width, rect.height));
        mGFX.setColor(mClearColor);
        mGFX.fillRect(0, 0, mWidth, mHeight);
        gfxpip.Render(mGFX);
    }

    // ------------------------------------------------------------------------
    /*! Present
    *
    *   Presents the game
    */ //----------------------------------------------------------------------
    public void Present() {
        final Graphics d2g = (Graphics) getGraphics();
        d2g.drawImage(mFrameBuffer, 0, 0, mWidth, mHeight, null);
        d2g.dispose();
    }
}
    