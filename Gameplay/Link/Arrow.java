package Gameplay.Link;

import java.awt.image.BufferedImage;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;

public class Arrow extends Actor{

    BufferedImage[][] allAnimation;
    
    final private int damage = 2;
    private AnimationMachine animationMachine;
    final private int speed = 18;
    final private float range = 350;
    private Float distance = 0f;
    private DIRECTION direction;

    public Arrow(Player Link){
        super(Link.GetPosition());
        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet("Content/Animations/Arrow.png", 44 , 40))); //52, 40
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();

        direction = Link.getDirection();

        SetPosition(new Vector2D<Float>(Link.GetPosition().x + 28, Link.GetPosition().y + 45)); //Magic numbers because of the hitbox waiting to be fixed
        SetScale( new Vector2D<Float>(44f,40f));
        animationMachine.GetAnimation().SetDelay(1);
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
            animationMachine.SetFrames(allAnimation[4]);
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
}
