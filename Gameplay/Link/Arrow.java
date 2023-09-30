package Gameplay.Link;

import java.awt.image.BufferedImage;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;

public class Arrow extends Actor{

    BufferedImage[][] allAnimation;
    
    final private int damage = 2;
    private AnimationMachine animationMachine;
    private float speed = 18;
    private float range = 350;
    private Float distance = 0f;
    private DIRECTION direction;
    private BoxCollider boxCollider;
    //private Player link;

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
        //boxCollider = (BoxCollider)AddComponent(new BoxCollider(this));
        Animate();
    }

    public void Move(){
        
        Vector2D<Float> pos = GetPosition();
        Float currentPosition;
        switch (direction){
            case UP:
                currentPosition = pos.y;
                pos.y -= speed;
                distance += Math.abs(currentPosition - pos.y);
                return;
            case DOWN:
                currentPosition = pos.y;
                pos.y += speed;
                distance += Math.abs(currentPosition - pos.y);
                return;
            case LEFT:
                currentPosition = pos.x;
                pos.x -= speed;
                distance += Math.abs(currentPosition - pos.x);
                return;
            case RIGHT:
                currentPosition = pos.x;
                pos.x += speed;
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
            ObjectManager.GetObjectManager().RemoveEntity(this);
        }
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
}
