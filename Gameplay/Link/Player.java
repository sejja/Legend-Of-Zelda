package Gameplay.Link;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import Engine.ECSystem.Level;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.Animation;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Tile.Block;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.CollisionResult;
import Engine.Physics.Components.BoxCollider;
import Engine.Window.GameLoop;
import Engine.Physics.Components.ColliderManager;
import Gameplay.States.PlayState;
import Gameplay.AnimatedObject.Bomb;
import Gameplay.Enemies.Enemy;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Interactive;
import Gameplay.LifeBar.LifeBar;
import Gameplay.NPC.DialogueWindow;
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
    public DIRECTION direction = DIRECTION.RIGHT;
    private boolean attack = false;
    private boolean stop = true;
    private boolean bow = false;
    public boolean dash = false;
    private boolean falling = false;
    //----------------------------------------------------------------------

    /* Animation
     */
    private int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    private int delay = 3; //This is the delay of the all animations
    //----------------------------------------------------------------------

    /*  Enable skills
     */
    private boolean haveArc;
    private boolean haveLighter;
    private boolean HaveBomb;
    protected boolean able_to_takeDamage=true;
    //----------------------------------------------------------------------

    /* CoolDowns
     */
    private static int nArrows = 10;
    private static int nbombs = 10;
    private static int nDash = 3;
    //----------------------------------------------------------------------

    /* Player Stats
     * 
     */
    protected int healthPoints = 10;
    private ZeldaCameraComponent mCamera;
    protected BoxCollider mCollider;
    protected BoxCollider hitbox;
    final private  int damage = 2;
    private int velocity = 0;
    final int default_velocity = 10;
    private LifeBar lifeBar;
    //----------------------------------------------------------------------

    /* NPC
     */
    private Npc currentNPCinteraction;
    //----------------------------------------------------------------------
    protected boolean isTouchingNpc;
    private Vector2D<Float> npcIndex;

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
        implementsActions();
        this.SetName("Player");
        //---------------------------------------------------------------------
        lifeBar = new LifeBar(getPlayer(), getHealthPoints());
        //---------------------------------------------------------------------
        //mCollider = (BoxCollider)AddComponent(new BoxCollider(this));

        setPseudoPosition(50f, 50f);
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(55f, 60f), true));
        ColliderManager.GetColliderManager().addCollider(hitbox, true);
        ObjectManager.GetObjectManager().SetPawn(this);
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
            }
            });
        }
        //RUN______________________________________________________________________________________________
        InputManager.SubscribePressed(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {activateAction(UP);}
        });
        InputManager.SubscribePressed(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {activateAction(DOWN);}
        });
        InputManager.SubscribePressed(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {activateAction(LEFT);}
        });
        InputManager.SubscribePressed(KeyEvent.VK_D, new InputFunction() {
            @Override
            public void Execute() {activateAction(RIGHT);}
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
                //attack = false;
                bow = false;
                dash = false;
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
                }
                dash = false;
            }
        });
        InputManager.SubscribeReleased(KeyEvent.VK_K, new InputFunction() {
            @Override
            public void Execute() {
                setVelocity(0);
                stop = true;
                bow =false;
            }
        });
        //DASH_______________________________________________________________________________________________
        InputManager.SubscribeReleased(KeyEvent.VK_SHIFT, new InputFunction() {
            @Override
            public void Execute() {
                if (nDash > 0){
                    dash = true;
                    nDash--;
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

        mAnimation.AddFinishedListener(new AnimationEvent() {

            @Override
            public void OnTrigger() {
                if (falling) //Finished falling animation
                {
                    linkHasFalled();
                }
                else if (nArrows != 0 && bow) //Spawn Arrow
                {
                    shootArrow();
                    bow = false;
                }
                else if (attack) //Finished Attack (attack && !bow)
                {
                    Attack();
                    bow = false;
                    attack = false;
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
        mAnimation.SetFrames(frames);
        mAnimation.GetAnimation().SetDelay(delay);
    }
    // ------------------------------------------------------------------------

    /* Animation StateMachine
     * 
     */
    public void Animate() {

        if (mAnimation.MustComplete()){return;} //Early return, if it is a animation thats has to be complete, do not animaate
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
        playerStateMachine();
        Animate();
        if(able_to_takeDamage){takeDamage();}
        lifeBar.Update();
        pseudoPositionUpdate();
        hitbox.Update();
    }
    public void playerStateMachine(){
        if(dash){dash();return;}//Early return dash is mostly the dominate action, so if link is dashing he can not do anything else
        else{
            if (!mAnimation.MustComplete()){Move();}
        }
    }
    // ------------------------------------------------------------------------
    
    /* Colision
     *      -> True if there is no collision
     */
    public boolean SolveCollisions(Vector2D<Integer> dif) {
        CollisionResult res = hitbox.GetBounds().collisionTile(
            dif.x - Level.mCurrentLevel.GetBounds().GetPosition().x, 
            dif.y - Level.mCurrentLevel.GetBounds().GetPosition().y);
        
        falling = res == CollisionResult.Hole;
        return (res == CollisionResult.None);
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
        Spritesheet Bow = new Spritesheet("Content/Animations/Link/LinkArco.png", 30, 30);
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
    private void takeDamage(){ //Looking for enemies to take damage
        //System.out.println("Vida = " + healthPoints);
        ArrayList<Entity> allEntities = ObjectManager.GetObjectManager().GetAllObjectsOfType(Enemy.class);

        if(allEntities != null)
            for (int i = 0; i < allEntities.size(); i++){
                Enemy enemy = (Enemy) allEntities.get(i);
                Vector2D<Float> enemyPosition = enemy.GetPosition();
                if (enemyPosition.getModuleDistance(this.GetPosition()) < this.GetScale().y/2){
                    this.setDamage(enemy.getDamage());
                    enemy.KnockBack();
                }
            }
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
    //------------------------------------------------------------------------

    /* Setters
     * 
     */
    public void setDamage(int healthPoints) {
        this.healthPoints -= healthPoints;
        this.setAble_to_takeDamage(false);
        if(this.healthPoints <= 0){dead();}
        else{
            ThreadInmortal thread = new ThreadInmortal(this);
            thread.start();
            lifeBar.setHealthPoints(this.healthPoints);
        }

    }
    public void setVelocity(int velocity) {this.velocity = velocity;}
    public void setAttack(boolean attack) {this.attack = attack;}
    public void setAble_to_takeDamage(boolean able_to_takeDamage) {this.able_to_takeDamage = able_to_takeDamage;}
    private void setMovement(Action type){

        if (type == null){
            mAnimation.setMustComplete(true);
            if(falling && mCurrentAnimation != FALL || mAnimation.GetAnimation().GetDelay() == -1) {SetAnimation(FALL, mAnimation.GetSpriteSheet().GetSpriteArray(FALL), delay);}//Enviromental special case
            return;
        }

        if (type == Action.ATTACK || type == Action.BOW){ //Activate must-end sequence
            mAnimation.setMustComplete(true);
        }

        int i = type.getID();

        if(falling && mCurrentAnimation != FALL+i|| mAnimation.GetAnimation().GetDelay() == -1) {
            SetAnimation(FALL+i, mAnimation.GetSpriteSheet().GetSpriteArray(FALL+i), delay);
            mAnimation.setMustComplete(true);
            return;
        }

        if (type == Action.ATTACK || type == Action.BOW){mAnimation.setMustComplete(true);}//Activate must-end sequence

        i = type.getID();

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
    private void shootArrow(){ //Tiene que dar al enemigo
        nArrows--;
        if(nArrows == 0){System.out.println("0 Arrows in quiver");}
        else{ObjectManager.GetObjectManager().AddEntity(new Arrow(this));}
    }
    //------------------------------------------------------------------------

    /*  This function is only called if Link has no healthpoints
    *   
    */
    private void dead(){ //falta hacer que link se muera y termine el juego
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
        It will called a KnockBack() function of that enemy
        */
        ArrayList<Entity> enemies = ObjectManager.GetObjectManager().GetAllObjectsOfType(Enemy.class);
        if(enemies == null){return;}
        for(Entity entity : enemies){
            Enemy currentEnemy = (Enemy) entity;
            if(currentEnemy.getPseudoPosition().getModuleDistance(getPseudoPosition()) < this.GetScale().getModule()-40){
                System.out.println( "leda");
                currentEnemy.setHealthPoints(damage);
                currentEnemy.KnockBack();
            }
        }
    }

    private void interact(){
        if(currentNPCinteraction == null){
            currentNPCinteraction = nearestNPC();
        }
        try {
            currentNPCinteraction.interaction();
        } catch (java.lang.NullPointerException e) {
            System.err.println("No npc found");
            this.currentNPCinteraction = null;
        }
    }
    private Npc nearestNPC (){
        ArrayList<Entity> NPCs = ObjectManager.GetObjectManager().GetAllObjectsOfType(Npc.class);
        Entity min = NPCs.get(0);

        for(Entity x : NPCs) {
            if (GetPosition().getModuleDistance(x.GetPosition()) < GetPosition().getModuleDistance(min.GetPosition())){
                min = x;
            }
        }
        if (GetPosition().getModuleDistance(min.GetPosition()) > (GetScale().getModule()/2)){ //Magic number to ajust
            return null;
        }
        return (Npc) min;
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
        Arrow dash_movement = new Arrow(this, new Spritesheet("Content/Animations/Link/LinkDashSpriteSheet.png", 90 , 50), 30, 300, true);
        dash_movement.SetScale(new Vector2D<>(0f, 0f));
        Arrow dash_animation = new Arrow(this, new Spritesheet("Content/Animations/Link/LinkDashSpriteSheet.png", 90 , 50), 0.3f , 1, false);

        ObjectManager.GetObjectManager().AddEntity(dash_animation);
        ObjectManager.GetObjectManager().AddEntity(dash_movement);

        dash = false;
        able_to_takeDamage = true;
    }
    
    /* To pause the gameplay
     * 
     */
    private void Pause(){
        GameLoop.SetPaused(!GameLoop.IsPaused());
    }

    /* This function set the player position to the spawn Point
     * 
     */
    private void setToSpawnPoint(){
        SetPosition(new Vector2D<Float>(700.f, 400.f));
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
        mAnimation.SetFrames(mAnimation.GetSpriteSheet().GetSpriteArray2D()[DOWN+Action.STOP.getID()]);
    }
    public void setBow(boolean bow) {this.bow = bow;}
    //------------------------------------------------------------------------

    @Override 
    public Class GetSuperClass(){return Player.class;}
}