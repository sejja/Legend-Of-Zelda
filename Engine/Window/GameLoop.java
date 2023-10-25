package Engine.Window;

import Engine.ECSystem.Level;
import Engine.Input.InputManager;
import Engine.StateMachine.StateMachine;

public class GameLoop extends Thread {
    private boolean mRunning;
    private StateMachine mStateManager;
    private PresentBuffer mTargetBuffer;
    static private boolean mPause;

    public GameLoop(PresentBuffer target) {
        mTargetBuffer = target;
        mPause = false;
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   Creates the FrameBuffer (a BufferedImage)
    */ //----------------------------------------------------------------------
    public void Init() {
        mRunning = true;
        mStateManager = new StateMachine();
        new InputManager(mTargetBuffer);
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
                Level.mCurrentLevel.Update();
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
                    Thread.sleep((int)Math.min(now - lastRenderTime, now - lastUpdateTime));
                } catch (Exception e) {
                    System.out.println("ERROR: yielding thread");
                }

                now = System.nanoTime();
            }

        }
    }
}
