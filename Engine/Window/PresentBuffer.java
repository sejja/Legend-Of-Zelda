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
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.StateMachine;

public class PresentBuffer extends JPanel implements Runnable {
    public static int mWidth;
    public static int mHeight;
    private Thread mLogicThread;
    private BufferedImage mFrameBuffer;
    private Graphics2D mGFX;
    private boolean mRunning;
    private StateMachine mStateManager;

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
        if(mLogicThread == null)
            (mLogicThread = new Thread(this, "LogicThread")).start();   ;
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        mRunning = true;
        mStateManager = new StateMachine();
        mFrameBuffer = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_ARGB);
        mGFX = (Graphics2D)mFrameBuffer.getGraphics();
        new InputManager(this);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the game
    */ //----------------------------------------------------------------------
    public void Update() {
        final Rectangle rect = getBounds();
        mStateManager.Update();
        GraphicsPipeline.GetGraphicsPipeline().SetDimensions(new Vector2D<>(rect.width, rect.height));
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the game
    */ //----------------------------------------------------------------------
    public void Render() {
        mGFX.setColor(Color.black);
        mGFX.fillRect(0, 0, mWidth, mHeight);
        mStateManager.Render(mGFX);
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

    // ------------------------------------------------------------------------
    /*! Run
    *
    *   The game loop.
    */ //----------------------------------------------------------------------
    public void run() {
        Init();

        final double FPS = 60.f;
        final double TBU = 1000000000 / FPS;

        //While the window is present
        for(long frametime = System.nanoTime(); mRunning; frametime = System.nanoTime()) {
            Update();
            Render();
            Present();
            final long lastRenderTime = System.nanoTime();

            //If we have time to sleep until the next frame, sleep
            for(Thread.yield(); frametime - lastRenderTime < TBU; frametime = System.nanoTime()) {
                //Sleeping the tread is not safe, add a try/catch
                try {
                    Thread.sleep(((long)TBU - (frametime - lastRenderTime)) / 1000000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
    