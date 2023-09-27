package Gameplay.Link;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.nio.file.LinkOption;
import java.util.ResourceBundle.Control;
import java.util.concurrent.atomic.AtomicInteger;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.FontComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class Arrow extends Actor{

    BufferedImage[][] allAnimation;
    final private int damage = 2;
    private AnimationMachine animationMachine;
    final private int speed = 1;
    final private int range = 200;
    private DIRECTION direction;

    public Arrow(Player Link){
        super(Link.GetPosition());
        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet("Content/Animations/Arrow.png", 52 , 40))); //52, 40
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();

        System.out.println(allAnimation.length);

        direction = Link.getDirection();
        SetPosition(new Vector2D<Float>(Link.GetPosition().x, Link.GetPosition().y));
        animationMachine.GetAnimation().SetDelay(1);
        Animate();
    }
    public void Move(){
        Vector2D<Float> pos = GetPosition();
        switch (direction){
            case UP:pos.y -= speed;return;
            case DOWN:pos.y += speed;return;
            case LEFT:pos.x -= speed;return;
            case RIGHT:pos.x += speed;return;
        }
        SetPosition(pos);
    }
    @Override
    public void Update() {
        //System.out.println("patata");
        //System.out.println(GetPosition());
        Move();
        Animate();
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
}
