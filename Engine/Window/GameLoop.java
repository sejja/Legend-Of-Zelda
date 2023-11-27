//
//	GameLoop.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 27/11/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Window;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Engine.Developer.DataBase.Database;
import Engine.Developer.Logger.Logger;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
import Engine.Graphics.GraphicsPipeline;
import Engine.Input.InputManager;
import Engine.Physics.ColliderManager;
import Engine.StateMachine.StateMachine;

public class GameLoop extends Thread {
    static private boolean mRunning;
    static private boolean mShouldRestart;
    static private boolean mPause;
    static private StateMachine mStateManager;
    private PresentBuffer mTargetBuffer;
    private ExecutorService mThreadPool;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public GameLoop(final PresentBuffer target) {
        mTargetBuffer = target;
        mPause = false;
        mShouldRestart = false;
        mThreadPool = Executors.newFixedThreadPool(1);
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        Logger.Instance().Log(Logger.Instance().GetLog("GameLoop"), 
        "Engine Started!", java.util.logging.Level.INFO);
        Database.Instance().InitConnection("game.db");
        mRunning = true;
        mStateManager = new StateMachine();
        InputManager.Instance().SetSpeakingBuffer(mTargetBuffer);
    }

    // ------------------------------------------------------------------------
    /*! Quit
    *
    *   Queues the Game Loop from closure, and closes the Data Base.
    */ //----------------------------------------------------------------------
    public static void Quit() {
        mRunning = false;
        Database.Instance().cerrarBD();
    }

    // ------------------------------------------------------------------------
    /*! Restart
    *
    *   Schedules the GameLoop to restart the game next frame
    */ //----------------------------------------------------------------------
    public static void Restart() {
        mShouldRestart = true;
    }

    // ------------------------------------------------------------------------
    /*! Private Restart
    *
    *   Restarts the state of the graphics pipeline and the object managers
    */ //----------------------------------------------------------------------
    private void ClearsPipelineState() {
        PresentBuffer.SetClearColor(Color.BLACK);
        ObjectManager.GetObjectManager().Clear();
        GraphicsPipeline.GetGraphicsPipeline().RemoveAllRenderables();
        ColliderManager.GetColliderManager().Clear();
        World.Reset();
        InputManager.Instance().Clear();
        mStateManager.Restart();
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the game
    */ //----------------------------------------------------------------------
    public void Update() {
        mStateManager.Update();
    }

    // ------------------------------------------------------------------------
    /*! Is Paused
    *
    *   Return wether we are paused or not
    */ //----------------------------------------------------------------------
    static public boolean IsPaused() {
        return mPause;
    }

    // ------------------------------------------------------------------------
    /*! Set Paused
    *
    *   Returns wether we pause the game or not
    */ //----------------------------------------------------------------------
    static public void SetPaused(final boolean b) {
        mPause = b;
    }

    // ------------------------------------------------------------------------
    /*! Run
    *
    *   The game loop.
    */ //----------------------------------------------------------------------
    @Override
    public void run() {
        Init();

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (mRunning) {

            double now = System.nanoTime();

            mThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    mTargetBuffer.Render();
                    mTargetBuffer.Present();
                }
                
            });

            while (((now - lastUpdateTime) > TBU)) {
                World.mCurrentLevel.Update();
                if(!mPause) Update();
                lastUpdateTime += TBU;
            }

            lastUpdateTime = (now - lastUpdateTime) > TBU ? now - TBU : lastUpdateTime;
            
            lastRenderTime = now;

            final int thisSecond = (int) (lastUpdateTime / 1000000000);

            lastSecondTime = thisSecond > lastSecondTime ? thisSecond : lastSecondTime;

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();
                try {
                    mThreadPool.wait();
                    Thread.sleep((int)((1 / GAME_HERTZ) * 1000 - ((System.nanoTime() - now) / 1000000.f)));
                } catch (Exception e) {

                }

                now = System.nanoTime();
            }

            if(mShouldRestart) {
                ClearsPipelineState();
                mShouldRestart = false;
            }
        }

        Logger.Instance().Log(Logger.Instance().GetLog("GameLoop"),
         "Bye bye", java.util.logging.Level.FINE);
    }
}
