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
    private float speed = 0;
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

    public Arrow (Player Link, Spritesheet spritesheet, float speed, float range, boolean fixed){ //This is actually a dash XD
        super(Link.GetPosition());
        this.speed = speed;
        this.range = range;
        this.damage = 0;
        this.animationMachine = AddComponent(new AnimationMachine(this ,spritesheet)); 
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();
         direction = Link.getDirection();
        if (fixed)
        {
            SetPosition(Link.GetPosition());
            this.fixed = fixed;
        }
        else
        {
            SetPosition(new Vector2D<Float>(Link.GetPosition().x, Link.GetPosition().y));
        }
        SetScale(new Vector2D<Float>(100f,100f));
        animationMachine.GetAnimation().SetDelay(1);
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
        Float currentPosition;
        switch (direction){
            case UP:
                currentPosition = pos.y;
                if(hitbox.GetBounds().collisionTile(0, -speed) == CollisionResult.None){
                    pos.y -= speed;
                    distance += Math.abs(currentPosition - pos.y);
                }else{
                    endArrow = true;
                }
                return;
            case DOWN:
                currentPosition = pos.y;
                if(hitbox.GetBounds().collisionTile(0, +speed) == CollisionResult.None){
                    pos.y += speed;
                    distance += Math.abs(currentPosition - pos.y);
                }else{
                    endArrow = true;
                }
                return;
            case LEFT:
                currentPosition = pos.x;
                if(hitbox.GetBounds().collisionTile(-speed, 0) == CollisionResult.None){
                    pos.x -= speed;
                    distance += Math.abs(currentPosition - pos.x);
                }else{
                    endArrow = true;
                }
                return;
            case RIGHT:
                currentPosition = pos.x;
                if(hitbox.GetBounds().collisionTile(+speed, 0) == CollisionResult.None){
                    pos.x += speed;
                    distance += Math.abs(currentPosition - pos.x);
                }else{
                    endArrow = true;
                }
                return;
        }
        SetPosition(pos);
    }

    public void WallSound() {
        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/arrow-wall.wav"));
        Audio.Instance().Play(sound);
    }

    @Override
    public void Update() {
        super.Update();
        Move();
        if (!(distance >= range)){
            Animate();
        }else{
            despawn();
        }
        if( endArrow ){
            WallSound();
            despawn();
        }
        Attack();
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
            if (damage ==0 ){return;}
            else{despawn();}
        }
        
        Player link = ((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0));
        ArrayList<Actor> rocks;
        if(!(rocks = ColliderManager.GetColliderManager().getCollision(hitbox, Interactive.class, true)).isEmpty()){
            int distance = (int) (speed+link.getVelocity());
            switch(direction){
                case UP:
                    this.SetPosition(new Vector2D<Float>(GetPosition().x, GetPosition().y+distance));
                    despawn();
                    break;
                case DOWN:
                    this.SetPosition(new Vector2D<Float>(GetPosition().x, GetPosition().y-distance));
                    despawn();
                    break;
                case LEFT:
                    this.SetPosition(new Vector2D<Float>(GetPosition().x+distance, GetPosition().y));
                    despawn();
                    break;
                case RIGHT:
                    this.SetPosition(new Vector2D<Float>(GetPosition().x-distance, GetPosition().y));
                    despawn();
                    break;
            }
        }
    }
    @Override
    protected void despawn() {
        if(fixed){((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0)).setDash(false);}
        super.despawn();
    }

    @Override 
    public Class GetSuperClass(){return Arrow.class;}
}
