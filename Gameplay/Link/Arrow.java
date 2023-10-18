package Gameplay.Link;

import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.lang.model.element.ModuleElement.DirectiveKind;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.CollisionResult;
import Engine.Physics.Components.BoxCollider;
import Engine.Physics.Components.ColliderManager;
import Gameplay.Enemies.Enemy;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Interactive;

public class Arrow extends Actor{

    BufferedImage[][] allAnimation;
    
    private int damage = 2;
    private AnimationMachine animationMachine;
    private float speed = 18;
    private float range = 350;
    private Float distance = 0f;
    private DIRECTION direction;
    private BoxCollider hitbox;
    private boolean endArrow;

    public Arrow(Player Link){
        super(new Vector2D<Float>(Link.GetPosition().x + 28, Link.GetPosition().y + 45));

        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet("Content/Animations/Link/Arrow.png", 44 , 40))); //44,40
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();

        direction = Link.getDirection();
        
        SetScale( new Vector2D<Float>(44f,40f));
        animationMachine.GetAnimation().SetDelay(1);
        Animate();

        setPseudoPosition(GetScale().x/2, GetScale().y/2);
        setPseudoPositionVisible();
        if (direction == DIRECTION.UP || direction == DIRECTION.DOWN){
            hitbox = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<>(20f,40f), true));
        }else{
            hitbox = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<>(40f,20f), true));
        }
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
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this));
        Animate();

        setPseudoPosition(GetScale().x/2, GetScale().y/2);
    }

    public void Move(){
        
        Vector2D<Float> pos = GetPosition();
        Float currentPosition;
        switch (direction){
            case UP:
                currentPosition = pos.y;
                if(hitbox.GetBounds().collisionTile(0, -speed) == CollisionResult.None){
                    pos.y -= speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.y);
                return;
            case DOWN:
                currentPosition = pos.y;
                if(hitbox.GetBounds().collisionTile(0, +speed) == CollisionResult.None){
                    pos.y += speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.y);
                return;
            case LEFT:
                currentPosition = pos.x;
                if(hitbox.GetBounds().collisionTile(-speed, 0) == CollisionResult.None){
                    pos.x -= speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.x);
                return;
            case RIGHT:
                currentPosition = pos.x;
                if(hitbox.GetBounds().collisionTile(+speed, 0) == CollisionResult.None){
                    pos.x += speed;
                }else{endArrow = true;}
                distance += Math.abs(currentPosition - pos.x);
                return;
        }
        SetPosition(pos);
    }
    @Override
    public void Update() {
        super.Update();
        Move();
        if (!(distance >= range)){
            Animate();
        }else{ //Delete arrow
            //System.out.println("Eliminado flecha");
            despawn();
        }
        if( endArrow ){
            //System.out.println("Eliminado flecha");
            despawn();
        }
        Attack();
        pseudoPositionUpdate();
        hitbox.Update();
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
        ArrayList<Actor> enemies;
        if(!(enemies = ColliderManager.GetColliderManager().getCollision(hitbox, Enemy.class, true)).isEmpty()){
            System.out.println("patata");
            Enemy enemy = (Enemy)enemies.get(0);
            enemy.setHealthPoints(damage);
            enemy.knockBack();
            endArrow = true;
            if (damage ==0 ){return;}
            else{despawn();}
        }
    }

    @Override 
    public Class GetSuperClass(){return Arrow.class;}
}
