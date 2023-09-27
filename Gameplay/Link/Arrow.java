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

public class Arrow extends Entity{

    BufferedImage[][] allAnimation;
    final private int damage = 2;
    private AnimationMachine animationMachine;
    final private int speed = 18;
    final private int range = 200;

    private DIRECTION direction;

    public Arrow(Player Link){
        this.animationMachine = new AnimationMachine(Link, new Spritesheet("Content/Animations/Arrow.png"));
        allAnimation = animationMachine.GetSpriteSheet().GetSpriteArray2D();
        direction = Link.getDirection();
        SetPosition(Link.GetPosition());
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
        System.out.println("patata");
    }
    public void Animate(){
        
    }
}
