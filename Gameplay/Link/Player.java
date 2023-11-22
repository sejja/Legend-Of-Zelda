package Gameplay.Link;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;
import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.Animation;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.CollisionResult;
import Engine.Physics.Components.BoxCollider;
import Engine.Window.GameLoop;
import Engine.Physics.Components.ColliderManager;
import Gameplay.Interaction;
import Gameplay.AnimatedObject.Bomb;
import Gameplay.Enemies.Enemy;
import Gameplay.Interactives.Interactive;
import Gameplay.LifeBar.LifeBar;
import Gameplay.NPC.Npc;

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
    private DIRECTION direction = DIRECTION.RIGHT;
    private boolean movable = true;
    private boolean attack = false;
    private boolean stop = true;
    private boolean bow = false;
    public boolean dash = false;
    private boolean falling = false;
    StackActioner stackActioner;
    
    private Vector2D<Float> previusPosition;
    //----------------------------------------------------------------------

    /* Animation
     */
    private int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    private int delay = 3; //This is the delay of the all animations
    //----------------------------------------------------------------------

    /*  Enable skills
     */
    private boolean haveDash;
    private boolean haveArc;
    private boolean haveLighter;
    private boolean HaveBomb;
    protected boolean able_to_takeDamage=true;
    //----------------------------------------------------------------------

    /* CoolDowns
     */
    private static int nArrows = 10;
    private static int nbombs = 3;
    private static int dashCooldawn = 30;
    //----------------------------------------------------------------------

    /* Player Stats
     * 
     */
    protected int healthPoints = 10;
    private ZeldaCameraComponent mCamera;
    protected BoxCollider mCollider;
    protected BoxCollider hitbox;
    protected BoxCollider terrainCollider;
    public BoxCollider seeker;

    final int dashDelay = 30;
    final private  int damage = 999;
    private int velocity = 0;
    final int default_velocity = 10;
    private LifeBar lifeBar;
    //----------------------------------------------------------------------

    /* NPC
     */
    private Interaction currentNPCinteraction;
    //----------------------------------------------------------------------
    public Float getVelocity;

    //Methods______________________________________________________________________________________________________________________________________________________________________________

    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Player(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);
        this.direction = DIRECTION.RIGHT;
        //Lets transpose the Sprite Matrix and add all extra Animations
        sprite.setmSpriteArray(this.completeAnimationSet(sprite.GetSpriteArray2D()));
        //---------------------------------------------------------------------
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        mCamera = AddComponent(new ZeldaCameraComponent(this));
        mCamera.Bind();
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), delay);
        stackActioner = new StackActioner(this);
        implementsActions();
        this.SetName("Player");
        //---------------------------------------------------------------------
        lifeBar = new LifeBar(getPlayer(), getHealthPoints());
        //---------------------------------------------------------------------
        mCollider = (BoxCollider)AddComponent(new BoxCollider(this));
        setPseudoPosition(50f, 50f);
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(55f, 60f), true));
        terrainCollider = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(55f, 20f), false));
        terrainCollider.setPosition(new Vector2D<>(getPseudoPosition().x - terrainCollider.GetBounds().GetWidth()/2, getPseudoPosition().y+20));
        terrainCollider.setColor(Color.RED);
        previusPosition = position;
        ColliderManager.GetColliderManager().addCollider(hitbox, true);
        ObjectManager.GetObjectManager().SetPawn(this);
        GetPosition().y = GetPosition().y-10;
    }
    // ------------------------------------------------------------------------

    /* This function only implements actionlisteners
     * 
     */
    private void implementsActions (){ // it can be coptimazed
        int[] stop_run = new int[]{KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_W};
        for (int i = 0; i<stop_run.length; i++){
            InputManager.SubscribeReleased(stop_run[i], new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                attack = false;
                stackActioner.pop(direction,Action.RUN);
            }
            });
        }
        //RUN______________________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(UP);
                stackActioner.push(direction, Action.RUN);
            }
        });
        InputManager.SubscribePressed(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(DOWN);
                stackActioner.push(direction, Action.RUN);
            }
        });
        InputManager.SubscribePressed(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(LEFT);
                stackActioner.push(direction, Action.RUN);}
        });
        InputManager.SubscribePressed(KeyEvent.VK_D, new InputFunction() {
            @Override
            public void Execute() {
                activateAction(RIGHT);
                stackActioner.push(direction, Action.RUN);}
        });
        InputManager.SubscribePressed(KeyEvent.VK_ESCAPE, new InputFunction() {
            @Override
            public void Execute() {GameLoop.Quit();}
        });
        //ATTACK_____________________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_J, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                bow = false;
                Random r = new Random();

                if(!attack) {
                    if(r.nextBoolean()) {
                        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/fighter-sword.wav"));
                        Audio.Instance().Play(sound);   
                    } else {
                        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/sword-2.wav"));
                        Audio.Instance().Play(sound);
                    }
                }

                stop = true;
                attack = true;
                stackActioner.push(direction, Action.ATTACK);
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_J, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                bow = false;
            }
        });
        //BOW________________________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_K, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                if(!attack){ //If not attacking, then shoot
                    bow = true;
                    stackActioner.push(direction,Action.BOW);
                }
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_K, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
            }
        });
        //DASH_______________________________________________________________________________________________
        InputManager.SubscribeReleased(KeyEvent.VK_SHIFT, new InputFunction() {
            @Override
            public void Execute() {
                if (dashCooldawn >= dashDelay){
                    dash = true;
                    able_to_takeDamage = false;
                }
                attack = false;
                bow =false;
            }
        });

        InputManager.SubscribeReleased(KeyEvent.VK_B, new InputFunction() {
            @Override
            public void Execute() {
                if(nbombs >= 0){
                    new Bomb(new Vector2D<Float>(GetPosition().x, GetPosition().y));
                    nbombs--;
                    Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/lay-bomb.wav"));
                    Audio.Instance().Play(sound);
                }else{
                    System.out.println("Bombs run out");}
                
            }
        });
        //Show LifeBar_______________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_M, new InputFunction() {
            @Override
            public void Execute() {lifeBar.setVisible(true);}
        });
        InputManager.SubscribeReleased(KeyEvent.VK_M, new InputFunction() {
            @Override
            public void Execute() {lifeBar.setVisible(false);}
        });
        InputManager.SubscribePressed(KeyEvent.VK_P, new InputFunction() { //Pause
            @Override
            public void Execute() {Pause();}
        });
        ///Interaction_______________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_E, new InputFunction() {
            @Override
            public void Execute() {interact();}
        });
        var player = this; //<-----------------------------quitar
        mAnimation.AddFinishedListener(new AnimationEvent() {

            @Override
            public void OnTrigger() {
                //System.out.println(player.actionToString());
                if (falling) //Finished falling animation
                {
                    linkHasFalled();
                }
                else if (bow) //Spawn Arrow
                {
                    shootArrow();
                    bow = false;
                    stackActioner.pop(direction,Action.BOW);
                    //System.out.println("Se popea arco");
                }
                else if (attack) //Finished Attack (attack && !bow)
                {
                    Attack();
                    bow = false;
                    attack = false;
                    stackActioner.pop(direction, Action.ATTACK);
                    //System.out.println("Se popea espada");
                }
                //mAnimation.setMustComplete(false);
            }
        });

        InputManager.SubscribePressed(KeyEvent.VK_T, new InputFunction() {
            @Override
            public void Execute() {
                System.out.println(ColliderManager.GetColliderManager().getCollision(hitbox, Interactive.class, true));
            }
        });
    }
    // ------------------------------------------------------------------------

    /* This function set the delay and the frames of a animation to the animation machine
     * 
     */
    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        mCurrentAnimation = i;
        mAnimation.SetFrameTrack(i);
        mAnimation.GetAnimation().SetDelay(delay);
    }
    // ------------------------------------------------------------------------

    /* Animation StateMachine
     * 
     */
    public void Animate() {

        if (mAnimation.MustComplete()){return;} //Early return, if it is a animation thats has to be complete, do not animate
        /*
        ActionObject currentAction = stackActioner.readCurrentAction();
        direction = currentAction.getDirection();
        setMovement(currentAction.getAction());
         */
        
        if (stop){
            setMovement(Action.STOP);
            if (attack){setMovement(Action.ATTACK);}
            else{
                if (bow){setMovement(Action.BOW);}
                else{setMovement(Action.STOP);}
            }
        }
        else{
            if(attack){setMovement(Action.STOP);setMovement(Action.ATTACK);}
            else{
                if(bow){setMovement(Action.STOP);setMovement(Action.BOW);}
                else{   
                    if(falling){setMovement(Action.STOP);setMovement(null);}
                    else{setMovement(Action.RUN);}
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
        //System.out.println(stackActioner);
        playerStateMachine();
        Animate();
        lifeBar.Update();
        pseudoPositionUpdate();
        hitbox.Update();
        terrainColliderUpdate();
        //System.out.println("Player Position: " + this.getPseudoPosition());
        //System.out.println(velocity);
        //System.out.println(GetPosition());
    }
    private void playerStateMachine(){
        if(dash){dashCooldawn = 0;dash();return;}
        else if (dashCooldawn<120){dashCooldawn++;}
        //else {if(!mAnimation.MustComplete()){Move();}}
        if(!mAnimation.MustComplete() && movable){Move();}
    }
    // ------------------------------------------------------------------------
    
    /* Colision
     *      -> True if there is no collision
     */
    private boolean SolveCollisions(Vector2D<Integer> dif) {
        float topLeftX =  dif.x - World.mCurrentLevel.GetBounds().GetPosition().x;
        float topLeftY =  dif.y - World.mCurrentLevel.GetBounds().GetPosition().y;
        CollisionResult res = terrainCollider.GetBounds().collisionTile(topLeftX, topLeftY);
        CollisionResult res_1 = terrainCollider.GetBounds().collisionTile(topLeftX + terrainCollider.GetBounds().GetWidth(), topLeftY);
        CollisionResult res_2 = terrainCollider.GetBounds().collisionTile(topLeftX , topLeftY + terrainCollider.GetBounds().GetHeight());
        CollisionResult res_3 = terrainCollider.GetBounds().collisionTile(topLeftX + terrainCollider.GetBounds().GetWidth(), topLeftY + terrainCollider.GetBounds().GetHeight());
        boolean result = (res == CollisionResult.None) && (res_1 == CollisionResult.None) && (res_2 == CollisionResult.None) && (res_3 == CollisionResult.None);
        falling = (res == CollisionResult.Hole);
        //return (result);
        return true;
    }
    // ------------------------------------------------------------------------
    
    /*! Move
    *
    *   Moves the sprite on a certain direction
    */ //----------------------------------------------------------------------
    private void Move() {
        Vector2D<Float> pos = GetPosition();
        previusPosition = new Vector2D<>(pos.x, pos.y);
        switch (direction){
            case UP:
                if(SolveCollisions(new Vector2D<>(0, -velocity))) pos.y -= velocity;
                break;
            case DOWN:
                if(SolveCollisions(new Vector2D<>(0, +velocity))) pos.y += velocity;
                break;
            case LEFT:
                if(SolveCollisions(new Vector2D<>(-velocity, 0))) pos.x -= velocity;
                break;
            case RIGHT:
                if(SolveCollisions(new Vector2D<>(+velocity, 0))) pos.x += velocity;
                break;
        }
        SetPosition(pos);
    }
    private void terrainColliderUpdate(){
        //terrainCollider.Update();
        terrainCollider.setPosition(new Vector2D<>(getPseudoPosition().x - terrainCollider.GetBounds().GetWidth()/2, getPseudoPosition().y+20));
    }
    // ------------------------------------------------------------------------

    /* Functions to set all animations
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
        Spritesheet Bow = new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/Link/LinkArco.png"), new Vector2D<>(30, 30));
        BufferedImage[][] animation = transposeMatrix(Bow.GetSpriteArray2D());
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 8; j++ ){
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
        return("mCurrentAnimation=" +mCurrentAnimation + ", stop=" + stop + ", attack =" + attack  + ", bow = " + bow + ", falling = " + falling);
    }
    public String mAnimatioT0String(){
        return ("My current animation: " + mCurrentAnimation);
    }
    //------------------------------------------------------------------------

    /* Functions to take damage from the enemies
    */
    public void activateAction(int action){
        if (action < 4){
            switch(action){
                case(0):
                    this.direction = DIRECTION.RIGHT;
                    break;
                case(1):
                    this.direction = DIRECTION.LEFT;
                    break;
                case(2):
                    this.direction = DIRECTION.DOWN;
                    break;
                case(3):
                    this.direction = DIRECTION.UP;
                    break;
            }
        }else if (action >= 5 && action <= 8){
            setVelocity(0);
            setAttack(true);
            return;
        }
        //System.out.println("haha");
        velocity = default_velocity;
        stop = false;
        attack = false;
    }
    //------------------------------------------------------------------------

    /* Getters
     * 
     */
    public int getDelay() {return delay;}
    public boolean isHaveArc() {return haveArc;}
    public boolean isHaveLighter() {return haveLighter;}
    public boolean isHaveBomb() {return HaveBomb;}
    public int getVelocity() {return velocity;}
    public DIRECTION getDirection(){return this.direction;}
    public Animation GetAnimation() {return mAnimation.GetAnimation();}
    public int getHealthPoints(){return this.healthPoints;}
    private Player getPlayer (){return this;}
    public boolean isAble_to_takeDamage() {return able_to_takeDamage;}
    public BoxCollider getHitbox() {return hitbox;}
    public Vector2D<Float> getPreviusPosition(){return this.previusPosition;}
    public BoxCollider getEnviromentCollider() {return this.terrainCollider;}
    //------------------------------------------------------------------------
    /* Setters
     * 
     */
    public void setDamage(int healthPoints) {
        if(able_to_takeDamage){
            this.healthPoints -= healthPoints;
            this.setAble_to_takeDamage(false);
            if(this.healthPoints <= 0){dead();}
            else{
                ThreadInmortal thread = new ThreadInmortal(this);
                thread.start();
                lifeBar.setHealthPoints(this.healthPoints);
                Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/link-hurt.wav"));
                Audio.Instance().Play(sound);
            
                if(this.healthPoints <= 2) {
                    sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/low-hp.wav"));
                    Audio.Instance().Play(sound);
                    Audio.Instance().SetLoopCount(sound, -1);
                }
            }
        }

    }
    public void setVelocity(int velocity) {this.velocity = velocity;}
    public void setAttack(boolean attack) {this.attack = attack;}
    public void setAble_to_takeDamage(boolean able_to_takeDamage) {this.able_to_takeDamage = able_to_takeDamage;}
    private void setMovement(Action type){

        if (type == null){
            mAnimation.setMustComplete(true);
            if(falling && mCurrentAnimation != FALL || mAnimation.GetAnimation().GetDelay() == -1) {
                SetAnimation(FALL, mAnimation.GetSpriteSheet().GetSpriteArray(FALL), delay);
                Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/link-fall.wav"));
                Audio.Instance().Play(sound);
            }//Enviromental special case
            return;
        }
        int i = type.getID();

        if (type == Action.ATTACK || type == Action.BOW){mAnimation.setMustComplete(true);}//Activate must-end sequence

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
    public void setPreviusPosition(Vector2D<Float> previusPosition){this.previusPosition = previusPosition;}
    public void setMovable(Boolean value){this.movable = value;}
    //------------------------------------------------------------------------

    /* Spawn a Arrow object
     *  !this function it is called in Update() not in keylistener
     */
    private void shootArrow(){ //Tiene que dar al enemigo
        nArrows--;
        if(nArrows <= 0){System.out.println("0 Arrows in quiver");}
        else{ObjectManager.GetObjectManager().AddEntity(new Arrow(this));}
    }
    //------------------------------------------------------------------------

    /*  This function is only called if Link has no healthpoints
    *   
    */
    private void dead(){ //falta hacer que link se muera y termine el juego
        Log v = Logger.Instance().GetLog("Gameplay");
        Logger.Instance().Log(v, "I Died", Level.INFO, 1, Color.RED);
        System.out.println("Ha muerto");
    }
    //------------------------------------------------------------------------
    
    /*  These functions are called when link interact with other entities
     * 
     */
    public void Attack(){
        /*  This function takes all Enemys
                
        It will calculate a vector to the player position to the enemy position
        If the DIRECTION of the vector Player-Enemy and The DIRECTION of the player is the same
        It will called a knockBack() function of that enemy
        */
        ArrayList<Actor> enemies = ColliderManager.GetColliderManager().getCollision(mCollider, Enemy.class, true);
        if (!enemies.isEmpty()){
            for(int i = 0; i < enemies.size(); i++){
                Enemy enemy = (Enemy)enemies.get(i);
                if(getPseudoPosition().getTargetDirection(enemy.getPseudoPosition()) == direction){
                    enemy.setHealthPoints(damage);
                    enemy.knockBack();
                    System.out.println("Le da");
                }
            }
        }

        ArrayList<Actor> interactives = ColliderManager.GetColliderManager().getCollision(mCollider, Interactive.class, true);
        if (!interactives.isEmpty()){
            for(int i = 0; i < interactives.size(); i++){
                Interactive interactive = (Interactive)interactives.get(i);
                if(getPseudoPosition().getTargetDirection(interactive.getPseudoPosition()) == direction){
                    Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/bombable-wall.wav"));
                    Audio.Instance().Play(sound);
                }
            }
        }

        {
            switch (direction){
            case UP:
                if(!SolveCollisions(new Vector2D<>(0, -10))) {
                    Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/tink.wav"));
                    Audio.Instance().Play(sound);
                }
                break;
            case DOWN:
                if(!SolveCollisions(new Vector2D<>(0, 10))) {
                    Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/tink.wav"));
                    Audio.Instance().Play(sound);
                }
                break;
            case LEFT:
                if(!SolveCollisions(new Vector2D<>(-10, 0))) {
                    Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/tink.wav"));
                    Audio.Instance().Play(sound);
                }
                break;
            case RIGHT:
                if(!SolveCollisions(new Vector2D<>(10, 0))) {
                    Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/tink.wav"));
                    Audio.Instance().Play(sound);
                }
                break;
        }
        }

    }
    private void interact(){
        if(currentNPCinteraction == null){
            currentNPCinteraction = nearestNPC();
        }
        try {
            currentNPCinteraction.INTERACTION();
        } catch (java.lang.NullPointerException e) {
            System.err.println("No npc found");
            this.currentNPCinteraction = null;
        }
    }
    private Interaction nearestNPC (){
        ArrayList<Actor> allInteraction;
        try{
            allInteraction =ColliderManager.GetColliderManager().getCollision(mCollider, Npc.class, true);
            return (Interaction)allInteraction.get(0);
        }catch(java.lang.IndexOutOfBoundsException e){
            System.err.println("No interaction in hitbox");
            return(null);
        }
    }
    public void removeInteraction(){
        currentNPCinteraction = null;
    }
    public DIRECTION getAttackDirection(Vector2D<Float> vector) { 
        if (Math.abs(vector.x) > Math.abs(vector.y)) {
            if (vector.x > 0) {return DIRECTION.RIGHT;} 
            else {return DIRECTION.LEFT;}
        } else {
            if (vector.y > 0) {return DIRECTION.DOWN;} 
            else {return DIRECTION.UP;}
        }
    }
    //------------------------------------------------------------------------
    
    /* Dash mecanics functions
     * 
     */
    public void dash (){
        /*  This function used to arrows to simulate a classic dash
         *      The first arrow and player have the same instance of the vectorposition it moves modifiying the position of the arrow and the vecto at the same time
         *      The second arrow it a arrow that contais a 1 frame animation and its has a low range to emulate a dash effect
         */
        //Asset dashaAsset = AssetManager.Instance().GetResource("Content/Animations/Link/LinkDashSpriteSheet.png");

        Arrow dash_movement = new Arrow(this, 30, 250, true);
        dash_movement.SetScale(new Vector2D<>(0f, 0f));
        ObjectManager.GetObjectManager().AddEntity(dash_movement);

        movable = false;
        dash = false;
        able_to_takeDamage = true;
    }
    //------------------------------------------------------------------------
    
    /* To pause the gameplay
     * 
     */
    private void Pause(){
        GameLoop.SetPaused(!GameLoop.IsPaused());
    }
    //------------------------------------------------------------------------

    /* This function set the player position to the spawn Point
     * 
     */
    private void setToSpawnPoint(){
        SetPosition(new Vector2D<Float>(1350.f, 800.f));
    }
    //------------------------------------------------------------------------

    /* This function is called when Link fell out or the Border
     * 
     */
    private void linkHasFalled(){
        falling = false;
        stop = true;
        setDamage(2);
        this.direction = DIRECTION.DOWN;
        setToSpawnPoint();
        hitbox.Reset();
        mAnimation.SetFrameTrack(DOWN+Action.STOP.getID());
    }
    public void setBow(boolean bow) {this.bow = bow;}
    //------------------------------------------------------------------------

    @Override 
    public Class GetSuperClass(){return Player.class;}
}