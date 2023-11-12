package Gameplay.Link;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;

import javax.lang.model.element.ModuleElement.DirectiveKind;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
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
    private int speed = 15;
    private float range = 350;
    private Float distance = 0f;
    private DIRECTION direction;
    private BoxCollider hitbox;
    private boolean endArrow;
    private boolean fixed;

    public Arrow(Player Link){
        super(new Vector2D<Float>(Link.GetPosition().x + 28, Link.GetPosition().y + 45));

        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/Link/Arrow.png"), new Vector2D<>(44, 40)))); //44,40
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
        ArrowSound();
    }

    public Arrow (Player Link, float speed, float range, boolean fixed){ //This is actually a dash XD
        super(Link.GetPosition());
        this.speed = (int)speed;
        this.range = range;
        this.damage = 0;
        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/Link/Arrow.png")))); 
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();
         direction = Link.getDirection();
        if (fixed)
        {
            SetPosition(Link.GetPosition());
            this.fixed = fixed;
        }
        SetScale(new Vector2D<Float>(100f,100f));
        animationMachine.GetAnimation().SetDelay(-1);
        hitbox = Link.getHitbox();
        Animate();

        setPseudoPosition(GetScale().x/2, GetScale().y/2);
        ArrowSound();
    }

    private void ArrowSound() {
        Random random = new Random();
        Sound sound = null;

        if(random.nextBoolean())
            sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/arrow.wav"));
        else
            sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/arrow2.wav"));
        
        Audio.Instance().Play(sound);
    }

    public void Move(){
        Vector2D<Float> pos = GetPosition();
        if(fixed){
            Player link = (Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
            link.setPreviusPosition(new Vector2D<>(pos.x, pos.y));
        }
        switch (direction){
            case UP:
                if(SolveCollisions(new Vector2D<Integer>(0, -speed))) {
                    pos.y -= speed;
                    distance += speed;
                }else{pos.y += speed;}
                break;
            case DOWN:
                if(SolveCollisions(new Vector2D<Integer>(0, +speed))) {
                    pos.y += speed;
                    distance += speed;
                }else{pos.y -= speed;}
                break;
            case LEFT:
                if(SolveCollisions(new Vector2D<Integer>(-speed, 0))) {
                    pos.x -= speed;
                    distance += speed;
                }else{pos.x += speed;}
                break;
            case RIGHT:
                if(SolveCollisions(new Vector2D<Integer>(+speed, 0))){
                    pos.x += speed;
                    distance += speed;
                }else{pos.x -= speed;}
                break;
        }
        //System.out.println(distance);
        //SetPosition(pos);
    }

    public boolean SolveCollisions(Vector2D<Integer> dif) {
        float topLeftX =  dif.x - World.mCurrentLevel.GetBounds().GetPosition().x;
        float topLeftY =  dif.y - World.mCurrentLevel.GetBounds().GetPosition().y;
        CollisionResult res = hitbox.GetBounds().collisionTile(topLeftX, topLeftY);
        CollisionResult res_1 = hitbox.GetBounds().collisionTile(topLeftX + hitbox.GetBounds().GetWidth(), topLeftY);
        CollisionResult res_2 = hitbox.GetBounds().collisionTile(topLeftX , topLeftY + hitbox.GetBounds().GetHeight());
        CollisionResult res_3 = hitbox.GetBounds().collisionTile(topLeftX + hitbox.GetBounds().GetWidth(), topLeftY + hitbox.GetBounds().GetHeight());
        boolean isMovable = (res == CollisionResult.None) && (res_1 == CollisionResult.None) && (res_2 == CollisionResult.None) && (res_3 == CollisionResult.None);
        if(!isMovable){
            endArrow = true;
        }
        return (isMovable);
    }

    public void WallSound() {
        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/arrow-wall.wav"));
        Audio.Instance().Play(sound);
    }
    @Override
    public void Update() {
        super.Update();
        Animate();
        Move();
        Attack();
        if (distance >= range){
            despawn();
            return;
        }
        if( endArrow ){
            WallSound();
            despawn();
            return;
        }
        pseudoPositionUpdate();
        hitbox.Update();
    }
    public void Animate(){
        switch(direction)
        {
            case UP: animationMachine.SetFrameTrack(3); ;return;
            case DOWN: animationMachine.SetFrameTrack(2) ;return;
            case LEFT: animationMachine.SetFrameTrack(1) ;return;
            case RIGHT: animationMachine.SetFrameTrack(0) ;return;
        }
    }
    public int damage(){return damage;}
    public float getVelocity(){return speed;}
    public void Attack(){
        ArrayList<Actor> enemies;
        if(!(enemies = ColliderManager.GetColliderManager().getCollision(hitbox, Enemy.class, true)).isEmpty()){
            Enemy enemy = (Enemy)enemies.get(0);
            enemy.setHealthPoints(damage);
            enemy.knockBack();
            endArrow = true;
            if (damage == 0 ){return;}
            else{endArrow = true;}
        }
        Player link = ((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0));
        ArrayList<Actor> rocks;
        if(!(rocks = ColliderManager.GetColliderManager().getCollision(hitbox, Interactive.class, true)).isEmpty()){
            if(fixed){link.SetPosition(link.getPreviusPosition());}
            endArrow = true;
        }
    }
    @Override
    protected void despawn() {
        if(fixed){((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0)).setMovable(true);}
        super.despawn();
    }
    @Override 
    public Class GetSuperClass(){return Arrow.class;}
}
