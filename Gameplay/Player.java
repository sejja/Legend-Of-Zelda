package Gameplay;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle.Control;
import java.util.concurrent.atomic.AtomicInteger;

import Engine.ECSystem.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class Player extends Actor {
    
    /*  Animation types
        The difference betwwen the direction and attack is 5
     */
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int DOWN = 2;
    private final int UP = 3;
    private final int FALL = 4;
    private final int ATTACK_RIGHT  = 5;
    private final int ATTACK_LEFT  = 6;
    private final int ATTACK_DOWN  = 7;
    private final int ATTACK_UP  = 8;
    //----------------------------------------------------------------------

    /*  Movement Boolean
        Used to confirm the direction
     */
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private boolean attack = false;
    //----------------------------------------------------------------------

    /* Animation
     */
    private int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    final private int delay = 3; //This is the delay of the all animations
    //----------------------------------------------------------------------

    /*  Enable skills
     */
    private boolean haveArc;
    private boolean haveLighter;
    private boolean HaveBomb;
    //----------------------------------------------------------------------
    
    /* Player Stats
     * 
     */
    protected AtomicInteger healthPoints = new AtomicInteger(10);
    final private  int damage = 2;
    private int velocity = 0;
    //----------------------------------------------------------------------

    private ThreadPlayer controls;

    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Player(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);

        //Lets transpose the Sprite Matrix
        sprite.setmSpriteArray(transposeMatrix(sprite.GetSpriteArray2D()));
        //---------------------------------------------------------------------

        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), delay);

        controls = new ThreadPlayer(this);
        controls.start();

        implementsActions();
    }

    /* This function only implements actionlisteners
     * 
     */
    private void implementsActions (){ //Esto es una mierda hay que mejorarlo en cuando se pueda
        
        InputManager.SubscribePressed(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {
                activateThread(UP);
             }
        });
        InputManager.SubscribePressed(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {
                activateThread(DOWN);
             }
        });
        InputManager.SubscribePressed(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {
                activateThread(LEFT);
             }
        });
        InputManager.SubscribePressed(KeyEvent.VK_D, new InputFunction() {
            @Override
            public void Execute() {
                activateThread(RIGHT);
             }
        });

        InputManager.SubscribeReleased(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {
                up = true;
                down = false;
                left = false;
                right = false;
                setVelocity(0);
             }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {
                up = false;
                down = true;
                left = false;
                right = false;
                setVelocity(0);
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {
               up = false;
                down = false;
                left = true;
                right = false;
                setVelocity(0);
            } 
        });
        InputManager.SubscribeReleased(KeyEvent.VK_D, new InputFunction() {
            @Override
            public void Execute() {
                up = false;
                down = false;
                left = false;
                right = true;
                setVelocity(0);
            }
        });
    }

    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        mCurrentAnimation = i;
        mAnimation.GetAnimation().SetFrames(frames);
        mAnimation.GetAnimation().SetDelay(delay);
    }

    public Animation GetAnimation() {
        return mAnimation.GetAnimation();
    }

    public void Animate() {
        if (!attack){
            if(up) {
            if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), delay);
            }
            } else if(down) {
                if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), delay);
                }
            } else if(right) {
                if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), delay);
                }
            } else if(left){
                if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), delay);
                }
            }
        }else{
            if(up) {
            if(mCurrentAnimation != UP+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                SetAnimation(UP+5, mAnimation.GetSpriteSheet().GetSpriteArray(UP+5), delay);
            }
            } else if(down) {
                if(mCurrentAnimation != DOWN+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(DOWN+5, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN+5), delay);
                }
            } else if(right) {
                if(mCurrentAnimation != RIGHT+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT+5, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT+5), delay);
                }
            } else if(left){
                if(mCurrentAnimation != LEFT+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT+5, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT+5), delay);
                }
            }
        }
    }
    // ------------------------------------------------------------------------

    /*! Update
    *
    *   Adds Behavior to the Player
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        super.Update();
        Move();
        Animate();
        mAnimation.GetAnimation().SetDelay(delay);
    }
    // ------------------------------------------------------------------------

    /*! Move
    *
    *   Moves the sprite on a certain direction
    */ //----------------------------------------------------------------------
    public void Move() {
        Vector2D<Float> pos = GetPosition();
        //System.out.println(directionToString());
        if(up) {
            pos.y -= velocity;
        }

        if(down) {
            pos.y += velocity;
        }

        if(left) {
            pos.x -= velocity;
        }

        if(right) {
            pos.x += velocity;
        }

        SetPosition(pos);
    }
    // ------------------------------------------------------------------------

    /*! Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
    // ------------------------------------------------------------------------
    
    /* Info. functions
     * 
     */
    public String directionToString() {
        return "Player [up=" + up + ", down=" + down + ", right=" + right + ", left=" + left + ", mCurrentAnimation="
                + mCurrentAnimation + "]" + "Thread isAlive :" + controls.isAlive();
    }
    //------------------------------------------------------------------------

    public void activateThread(int i){
        System.out.println(controls.execute);
        if (!controls.execute){
            controls.setAction(i);
            controls.execute = true;
            System.out.println(directionToString());
        }
    }

    /* Getters
     * 
     */
    public boolean isUp() {
        return up;
    }
    public boolean isDown() {
        return down;
    }
    public boolean isRight() {
        return right;
    }
    public boolean isLeft() {
        return left;
    }
    public int getmCurrentAnimation() {
        return mCurrentAnimation;
    }
    public int getDelay() {
        return delay;
    }
    public boolean isHaveArc() {
        return haveArc;
    }
    public boolean isHaveLighter() {
        return haveLighter;
    }
    public boolean isHaveBomb() {
        return HaveBomb;
    }
    public int getVelocity() {
        return velocity;
    }
    //------------------------------------------------------------------------

    /* Setters
     * 
     */
    public void setHealthPoints(AtomicInteger healthPoints) {
        this.healthPoints = healthPoints;
    }
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setAttack(boolean attack) {
        this.attack = attack;
    }
    //------------------------------------------------------------------------
}
