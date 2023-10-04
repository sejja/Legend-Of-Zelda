package Gameplay.Link;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.CollisionResult;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Enemy;

public class Arrow extends Actor{

    BufferedImage[][] allAnimation;
    
    private int damage = 2;
    private AnimationMachine animationMachine;
    private float speed = 18;
    private float range = 350;
    private Float distance = 0f;
    private DIRECTION direction;
    private BoxCollider boxCollider;
    private boolean endArrow;

    public Arrow(Player Link){
        super(Link.GetPosition());
        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet("Content/Animations/Link/Arrow.png", 44 , 40))); //52, 40
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();

        direction = Link.getDirection();

        SetPosition(new Vector2D<Float>(Link.GetPosition().x + 28, Link.GetPosition().y + 45)); //Magic numbers because of the hitbox waiting to be fixed
        //SetPosition(Link.GetPosition());
        SetScale( new Vector2D<Float>(44f,40f));
        animationMachine.GetAnimation().SetDelay(1);
        boxCollider = (BoxCollider)AddComponent(new BoxCollider(this));
        Animate();
    }

    public Arrow (Player Link, Spritesheet spritesheet, float speed, float range, boolean fixed){ //This is actually a dash XD
        super(Link.GetPosition());
        //link = Link;

        this.speed = speed;
        this.range = range;
        this.damage = 0;
        this.animationMachine = AddComponent(new AnimationMachine(this ,spritesheet)); 

        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();
         direction = Link.getDirection();
        if (fixed)
        {
            SetPosition(Link.GetPosition());
        }
        else
        {
        SetPosition(new Vector2D<Float>(Link.GetPosition().x, Link.GetPosition().y));
        }
        SetScale(new Vector2D<Float>(100f,100f));
        animationMachine.GetAnimation().SetDelay(1);
        boxCollider = (BoxCollider)AddComponent(new BoxCollider(this));
        Animate();
    }

    public void Move(){
        
        Vector2D<Float> pos = GetPosition();
        Float currentPosition;
        switch (direction){
            case UP:
                currentPosition = pos.y;
                if(boxCollider.GetBounds().collisionTile(0, -speed) == CollisionResult.None){
                    pos.y -= speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.y);
                return;
            case DOWN:
                currentPosition = pos.y;
                if(boxCollider.GetBounds().collisionTile(0, +speed) == CollisionResult.None){
                    pos.y += speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.y);
                return;
            case LEFT:
                currentPosition = pos.x;
                if(boxCollider.GetBounds().collisionTile(-speed, 0) == CollisionResult.None){
                    pos.x -= speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.x);
                return;
            case RIGHT:
                currentPosition = pos.x;
                if(boxCollider.GetBounds().collisionTile(+speed, 0) == CollisionResult.None){
                    pos.x += speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.x);
                return;
        }
        SetPosition(pos);
    }
    @Override
    public void Update() {
        Move();
        if (!(distance >= range)){
            Animate();
        }else{ //Delete arrow
            System.out.println("Eliminado flecha");
            animationMachine.SetFrames(allAnimation[4]);
            this.SetScale(new Vector2D<>(0f,0f));
            boxCollider.ShutDown();
            ObjectManager.GetObjectManager().RemoveEntity(this);
        }
        if( endArrow ){
            System.out.println("Eliminado flecha");
            animationMachine.SetFrames(allAnimation[4]);
            this.SetScale(new Vector2D<>(0f,0f));
            boxCollider.ShutDown();
            ObjectManager.GetObjectManager().RemoveEntity(this);
        }
        Attack();
    }
    public void Animate(){
        switch(direction)
        {
            case UP: animationMachine.SetFrames(allAnimation[3]) ;return;
            case DOWN: animationMachine.SetFrames(allAnimation[2]) ;return;
            case LEFT: animationMachine.SetFrames(allAnimation[1]) ;return;
            case RIGHT: animationMachine.SetFrames(allAnimation[0]) ;return;
        }
    }
    public int damage(){return damage;}
    public float getVelocity(){return speed;}
    public void Attack(){
        /*  This function takes all de Entitys and if any of them is a instance of Enemys it has to ve consider hast potencial objetives to hit
         *      It will calculate a vector to the player position to the enemy position
         *             It will called a KnockBack() function of that enemy
         */
        ArrayList<Entity> allEntities = ObjectManager.GetObjectManager().getmAliveEntities();
        for (int i = 0; i < allEntities.size(); i++){
            if (allEntities.get(i) instanceof Enemy){
                Enemy enemy = (Enemy) allEntities.get(i);
                Vector2D<Float> enemyPosition = enemy.GetPosition();
                //System.out.println(enemyPosition.getModuleDistance(this.GetPosition()));
                if (enemyPosition.getModuleDistance(this.GetPosition()) < this.GetScale().getModule()){ //Each enemy thats can be attacked
                    System.out.println("Le da");
                    enemy.setHealthPoints(damage);
                    enemy.KnockBack();
                    endArrow = true;
                    return;
                }
            }
        }
    }
}
