package Engine.Window;

import java.awt.Color;
import java.awt.Graphics;

import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Input.InputManager;
import Engine.Physics.Components.ColliderManager;
import Engine.StateMachine.StateMachine;

public class GameLoop extends Thread {
    static private boolean mRunning;
    static private boolean mShouldRestart;
    static private StateMachine mStateManager;
    private PresentBuffer mTargetBuffer;
    static private boolean mPause;

    public GameLoop(PresentBuffer target) {
        mTargetBuffer = target;
        mPause = false;
        mShouldRestart = false;
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        Log v = Logger.Instance().GetLog("GameLoop");

        Logger.Instance().Log(v, "Engine Started!", java.util.logging.Level.INFO);

        mRunning = true;
        mStateManager = new StateMachine();
        new InputManager(mTargetBuffer);
    }

    public static void Quit() {
        mRunning = false;
    }

    public static void Restart() {
        mShouldRestart = true;
    }

    private void _restart() {
        PresentBuffer.SetClearColor(Color.BLACK);
        ObjectManager.GetObjectManager().Clear();
        GraphicsPipeline.GetGraphicsPipeline().RemoveAllRenderables();
        ColliderManager.GetColliderManager().Clear();
        World.Reset();
        InputManager.Clear();
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
    static public void SetPaused(boolean b) {
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

        final int MUBR = 3;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 1000;
        final double TTBR = 1000000000 / TARGET_FPS;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (mRunning) {

            double now = System.nanoTime();
            int updateCount = 0;
            while (((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
                World.mCurrentLevel.Update();
                if(!mPause) Update();
                lastUpdateTime += TBU;
                updateCount++;
            }

            if ((now - lastUpdateTime) > TBU) {
                lastUpdateTime = now - TBU;
            }

            mTargetBuffer.Render();
            mTargetBuffer.Present();
            lastRenderTime = now;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();

                try {
                    Thread.sleep((int)(16.66666666666666666666666 - ((System.nanoTime() - now) / 1000000.f)));
                } catch (Exception e) {

                }

                now = System.nanoTime();
            }

            if(mShouldRestart) {
                _restart();
                mShouldRestart = false;
            }
        }

        Log v = Logger.Instance().GetLog("GameLoop");
        Logger.Instance().Log(v, "Bye bye", java.util.logging.Level.FINE);
    }
}
