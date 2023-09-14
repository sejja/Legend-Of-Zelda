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
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PresentBuffer extends JPanel implements Runnable {
    public static int mWidth;
    public static int mHeight;
    private Thread mLogicThread;
    private BufferedImage mFrameBuffer;
    private Graphics2D mGFX;
    private boolean mRunning;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   As a JPanel, which behaves as a FrameBuffer, request the focus and sets the size
    */ //----------------------------------------------------------------------
    public PresentBuffer(int width, int height) {
        mWidth = width;
        mHeight = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    // ------------------------------------------------------------------------
    /*! Add Notify
    *
    *   Starts the Game Thread, in case there is none. This happens on the start
    */ //----------------------------------------------------------------------
    public void addNotify() {
        super.addNotify();

        //If we had no runing thread
        if(mLogicThread == null) {
            mLogicThread = new Thread(this, "LogicThread");   
            mLogicThread.start();
        }
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        mRunning = true;
        mFrameBuffer = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_ARGB);
        mGFX = (Graphics2D)mFrameBuffer.getGraphics();
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public void Update() {
        
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public void Render() {
        if(mGFX != null) {
            mGFX.setColor(Color.black);
            mGFX.fillRect(0, 0, mWidth, mHeight);
        }
    }

    // ------------------------------------------------------------------------
    /*! Present
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public void Present() {
        Graphics d2g = (Graphics) getGraphics();
        d2g.drawImage(mFrameBuffer, 0, 0, mWidth, mHeight, null);
        d2g.dispose();
    }

    public void Input() {

    }

    // ------------------------------------------------------------------------
    /*! Run
    *
    *   The game loop.
    */ //----------------------------------------------------------------------
    public void run() {
        Init();

        final double FPS = 60.f;
        final double TBU = 1000000000 / FPS;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        int lastSecondTime = (int) (lastUpdateTime / 1000000000); 

        //While the window is present
        while(mRunning) {
            long now = System.nanoTime();
            Input();
            Update();
            Render();
            Present();
            lastRenderTime = System.nanoTime();

            while(now - lastUpdateTime < TBU) {
                Thread.yield();

                try {
                    Thread.sleep(10);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                now = System.nanoTime();
            }
        }
    }
}
