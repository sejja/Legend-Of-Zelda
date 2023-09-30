package Gameplay.Link;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle.Control;
import java.util.concurrent.atomic.AtomicInteger;

import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.FontComponent;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;

public class PlayerAntiguo extends Actor {
    
    /*  Animation types
        The difference between the direction and attack is 5
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
    private boolean right = true;
    private boolean left = false;
    private boolean attack = false;
    private boolean stop = true;
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

    /* CoolDowns
     */
    private static int attack_cooldown = 8;
    private static int attack_counter = 0;
    //----------------------------------------------------------------------

    /* Player Stats
     * 
     */
    protected AtomicInteger healthPoints = new AtomicInteger(10);
    private ZeldaCameraComponent mCamera;
    protected BoxCollider mCollider;
    final private  int damage = 2;
    private int velocity = 0;
    //----------------------------------------------------------------------

    //Methods______________________________________________________________________________________________________________________________________________________________________________

    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public PlayerAntiguo(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);

        //Lets transpose the Sprite Matrix
        sprite.setmSpriteArray(transposeMatrix(sprite.GetSpriteArray2D()));
        //---------------------------------------------------------------------

        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        mCamera = AddComponent(new ZeldaCameraComponent(this));
        mCamera.Bind();
        mCamera.SetBounds(new Vector2D<>(800.f, 600.f), new Vector2D<>(75 * 32.f, 75 * 32.f));
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), delay);

        //controls = new ThreadPlayer(this);
        //controls.start();

        implementsActions();

        mCollider = (BoxCollider)AddComponent(new BoxCollider(this));
    }
    // ------------------------------------------------------------------------

    /* This function only implements actionlisteners
     * 
     */
    private void implementsActions (){ //Esto es una mierda hay que mejorarlo en cuando se pueda
        
        InputManager.SubscribePressed(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(UP);
             }
        });
        InputManager.SubscribePressed(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(DOWN);
             }
        });
        InputManager.SubscribePressed(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(LEFT);
             }
        });
        InputManager.SubscribePressed(KeyEvent.VK_D, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(RIGHT);
             }
        });

        InputManager.SubscribeReleased(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
             }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
            } 
        });
        InputManager.SubscribeReleased(KeyEvent.VK_D, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
            }
        });

        InputManager.SubscribePressed(KeyEvent.VK_J, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = true;
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_J, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
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
        //System.out.println("stop = " + stop + " | " + "attack = " + attack);
        if (stop)
        {
            if (attack)
            {
                setAttackAnimation();
            }
            else
            {
                //System.out.println(mAnimatioT0String());
                setSetopAnimation();
            }
        }
        else
        {
            if(attack){
                
                setSetopAnimation();
            }
            {
                setRunAnimation();
            }
        }
    }
    // ------------------------------------------------------------------------

    /*! Update
    *
    *   Adds Behavior to the Player
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {  //Falta hacer que link termine un ataque completo antes de emoezar otro 
        super.Update();
        Move();
        /*
        if (attack){
            if(attack_counter == 0){
                Animate();
            }
            attack_counter++;
            System.out.println(attack_counter);
            if (attack_counter == attack_cooldown){
                System.out.println("Ha terminado");
                attack = false;
                attack_counter=0;
            }
        }
        else
        {
            Animate();
        }
        */
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
            if(!mCollider.GetBounds().collisionTile(0, -velocity))
                pos.y -= velocity;
        }

        if(down) {
            if(!mCollider.GetBounds().collisionTile(0, velocity))
                pos.y += velocity;
        }

        if(left) {
            if(!mCollider.GetBounds().collisionTile(-velocity, 0))
                pos.x -= velocity;
        }

        if(right) {
            if(!mCollider.GetBounds().collisionTile(velocity, 0))
                pos.x += velocity;
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
        BufferedImage[][] temp = new BufferedImage[m[0].length + 4][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 0;  j < temp[0].length; j++){
                temp[m[0].length+i][j] = temp[i][0];
            }
        }
        return temp;
    }
    // ------------------------------------------------------------------------
    
    /* Info. functions
     * 
     */
    public String directionToString() {
        return "Player [up=" + up + ", down=" + down + ", right=" + right + ", left=" + left + ", mCurrentAnimation="
                + mCurrentAnimation + ", stop=" + stop + ", attack =" + attack + "]" ;
    }
    public String mAnimatioT0String(){
        return ("My current animation: " + mCurrentAnimation);
    }
    //------------------------------------------------------------------------

    public void activateAction(int action){
        final int default_velocity = 5;
        if (action < 4){
            switch(action){
                case(0):
                    setUp(false);
                    setDown(false);
                    setLeft(false);
                    setRight(true);
                    setVelocity(default_velocity);
                    break;
                case(1):
                    setUp(false);
                    setDown(false);
                    setLeft(true);
                    setRight(false);
                    setVelocity(default_velocity);
                    break;
                case(2):
                    setUp(false);
                    setDown(true);
                    setLeft(false);
                    setRight(false);
                    setVelocity(default_velocity);
                    break;
                case(3):
                    setUp(true);
                    setDown(false);
                    setLeft(false);
                    setRight(false);
                    setVelocity(default_velocity);
                    break;
            }
        }else if (action >= 5 && action <= 8){
            setVelocity(0);
            setAttack(true);
        }
        stop = false; 
        attack = false;
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
    public void setSetopAnimation(){
                if(up) {
                if(mCurrentAnimation != UP+9 || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP+9, mAnimation.GetSpriteSheet().GetSpriteArray(UP+9), delay);
                }
                } else if(down) {
                    if(mCurrentAnimation != DOWN+9 || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(DOWN+9, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN+9), delay);
                    }
                } else if(right) {
                    if(mCurrentAnimation != RIGHT+9 || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(RIGHT+9, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT+9), delay);
                    }
                } else if(left){
                    if(mCurrentAnimation != LEFT+9|| mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(LEFT+9, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT+9), delay);
                    }
                }
    }
    public void setAttackAnimation(){
                if(up) {
                if(mCurrentAnimation != UP+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP+5, mAnimation.GetSpriteSheet().GetSpriteArray(UP+5), delay-2);
                }
                } else if(down) {
                    if(mCurrentAnimation != DOWN+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(DOWN+5, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN+5), delay-2);
                    }
                } else if(right) {
                    if(mCurrentAnimation != RIGHT+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(RIGHT+5, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT+5), delay-2);
                    }
                } else if(left){
                    if(mCurrentAnimation != LEFT+5 || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(LEFT+5, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT+5), delay-2);
                    }
                }
    }
    public void setRunAnimation(){
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
    }
    //------------------------------------------------------------------------
}