package Gameplay.Link;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;

public class Player extends Actor {
    
    /*  Animation types
        The difference between the direction and attack is 5
        The difference between the direction and stop is 9
     */
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int DOWN = 2;
    private final int UP = 3;

    private final int FALL = 4;
    //----------------------------------------------------------------------

    /*  Movement Boolean
        Used to confirm the direction
     */
    //private boolean up = false;
    public DIRECTION direction;
    private boolean attack = false;
    private boolean stop = true;
    private boolean bow = false;
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
    private static int nArrows = 10;
    private static int nbombs = 10;
    //----------------------------------------------------------------------

    /* Player Stats
     * 
     */
    protected AtomicInteger healthPoints = new AtomicInteger(10);
    private ZeldaCameraComponent mCamera;
    protected BoxCollider mCollider;
    final private  int damage = 2;
    private int velocity = 0;
    final int default_velocity = 10;
    //----------------------------------------------------------------------

    //Methods______________________________________________________________________________________________________________________________________________________________________________

    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Player(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);
        this.direction = DIRECTION.RIGHT;
        //Lets transpose the Sprite Matrix
        sprite.setmSpriteArray(completeAnimationSet(sprite.GetSpriteArray2D()));
        //---------------------------------------------------------------------
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        mCamera = AddComponent(new ZeldaCameraComponent(this));
        mCamera.Bind();
        mCamera.SetBounds(new Vector2D<>(800.f, 600.f), new Vector2D<>(75 * 32.f, 75 * 32.f));
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), delay);
        implementsActions();
        this.SetName("Player");

        mCollider = (BoxCollider)AddComponent(new BoxCollider(this));
    }
    // ------------------------------------------------------------------------

    /* This function only implements actionlisteners
     * 
     */
    private void implementsActions (){ // it can be coptimazed
        //RUN______________________________________________________________________________________________
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
        //STOP______________________________________________________________________________________________
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
        //ATTACK_____________________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_J, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = true;
                bow = false;
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_J, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
                bow = false;
            }
        });
        //BOW________________________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_K, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
                bow =true;
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_K, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
                bow =false;
            }
        });
    }

    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        //System.out.println(i);
        mCurrentAnimation = i;
        mAnimation.SetFrames(frames);
        mAnimation.GetAnimation().SetDelay(delay);
    }

    public Animation GetAnimation() {
        return mAnimation.GetAnimation();
    }

    public void Animate() {
        //System.out.println(actionToString());
        if (mAnimation.getMust_Complete()){return;} //Early return, if it is a animation thats has to be complete, do not animaate


        if (stop)
        {
            setMovement(Action.STOP);
            if (attack)
            {
                setMovement(Action.ATTACK);
            }
            else
            {
                if (bow)
                {
                    setMovement(Action.BOW);
                }
                else
                {
                    setMovement(Action.STOP);
                }
            }
        }
        else
        {
            if(attack){
                setMovement(Action.STOP);
                setMovement(Action.ATTACK);
            }
            else
            {
                if(bow)
                {
                    setMovement(Action.STOP);
                    setMovement(Action.BOW);
                    //System.out.println(mAnimatioT0String());
                }
                else
                {
                    setMovement(Action.RUN);
                }
            }
        }
        //System.out.println(mAnimatioT0String());
    }
    // ------------------------------------------------------------------------

    /*! Update
    *
    *   Adds Behavior to the Player
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {  //Falta hacer que link termine un ataque completo antes de emoezar otro 
        super.Update();
        if (!mAnimation.getMust_Complete())
        {
            Move();
        }
        else if (nArrows != 0 && mAnimation.finised_Animation && bow) //Spawn Arrow
        {
            shootArrow();
            bow = false;
            mAnimation.finised_Animation = false;
        }
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
        switch (direction){
            case UP:pos.y -= velocity;return;
            case DOWN:pos.y += velocity;return;
            case LEFT:pos.x -= velocity;return;
            case RIGHT:pos.x += velocity;return;
        }
        SetPosition(pos);
    }
    // ------------------------------------------------------------------------

    /* Transpose
    *       @Param  -> BufferedImage 2D Matrix
    *       ret     -> Transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] completeAnimationSet(BufferedImage [][] m){
        BufferedImage[][] temp = transposeMatrix(m);
        setSetopAnimationSet(temp, m[0].length);
        setBowAnimaitonSet(temp, m[0].length);
        return (temp);
    }
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length + 8][m.length]; //Modificar este +8 para casos generales 
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }
    private void setSetopAnimationSet(BufferedImage[][] m, int size){
        for (int i = 0; i < 4; i++){
            for (int j = 0;  j < m[0].length; j++){
                m[size+i][j] = m[i][0];
            }
        }
    }
    private void setBowAnimaitonSet(BufferedImage[][] temp, int size){
        Spritesheet Bow = new Spritesheet("Content/Animations/LinkArco.png", 30, 30);
        BufferedImage[][] animation = transposeMatrix(Bow.GetSpriteArray2D());
        //System.out.println(animation.length + "|" + animation[0].length);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 8; j++ ){
                //System.out.println("i = " + i + " | j =" + j );
                if (j >= 3)
                {
                    temp[size+4+i][j] = animation[i][2];
                }
                else
                {
                    temp[size+4+i][j] = animation[i][j];
                }
            }
        }
    }
    // ------------------------------------------------------------------------
    
    /* Info. functions
     * 
     */
    public String directionToString() {
        return "Player direction = " + direction;
    }
    public String actionToString(){
        return("mCurrentAnimation=" +mCurrentAnimation + ", stop=" + stop + ", attack =" + attack  + ", bow = " + bow);
    }
    public String mAnimatioT0String(){
        return ("My current animation: " + mCurrentAnimation);
    }
    //------------------------------------------------------------------------

    public void activateAction(int action){
        if (action < 4){
            switch(action){
                case(0):
                    this.direction = DIRECTION.RIGHT;
                    setVelocity(default_velocity);
                    break;
                case(1):
                    this.direction = DIRECTION.LEFT;
                    setVelocity(default_velocity);
                    break;
                case(2):
                    this.direction = DIRECTION.DOWN;
                    setVelocity(default_velocity);
                    break;
                case(3):
                    this.direction = DIRECTION.UP;
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
    public int getDelay() {return delay;}
    public boolean isHaveArc() {return haveArc;}
    public boolean isHaveLighter() {return haveLighter;}
    public boolean isHaveBomb() {return HaveBomb;}
    public int getVelocity() {return velocity;}
    public int Attack(){return (this.damage);}
    public DIRECTION getDirection(){return this.direction;}
    //------------------------------------------------------------------------

    /* Setters
     * 
     */
    public void setHealthPoints(AtomicInteger healthPoints) {this.healthPoints = healthPoints;}
    public void setVelocity(int velocity) {this.velocity = velocity;}
    public void setAttack(boolean attack) {this.attack = attack;}
    private void setMovement(Action type){

        if (type == Action.ATTACK || type == Action.BOW){ //Activate must-end sequence
            mAnimation.setMust_Complete();
        }

        int i = type.getID();
        switch(direction){
            case UP:
                if(mCurrentAnimation != UP+i || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP+i, mAnimation.GetSpriteSheet().GetSpriteArray(UP+i), delay);
                }
                return;
            case DOWN:
                if(mCurrentAnimation != DOWN+i || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(DOWN+i, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN+i), delay);
                }
                return;
            case RIGHT:
                if(mCurrentAnimation != RIGHT+i || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT+i, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT+i), delay);
                }
                return;
            case LEFT:
                if(mCurrentAnimation != LEFT+i|| mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT+i, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT+i), delay);
                }
                return;
        }
    }
    //------------------------------------------------------------------------

    /* Spawn a Arrow object
     *  !this function it is called in Update() not in keylistener
     */
    private void shootArrow(){
        nArrows--;
        if(nArrows == 0){
            System.out.println("0 Arrows in quiver");
        }
        ObjectManager.GetObjectManager().AddEntity(new Arrow(this));
    }
    //------------------------------------------------------------------------
}